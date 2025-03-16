package dev.gunho.stock.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.gunho.global.dto.ApiResponse;
import dev.gunho.global.dto.ApiResponseCode;
import dev.gunho.global.dto.PagingDTO;
import dev.gunho.global.dto.UserDetail;
import dev.gunho.global.entity.Template;
import dev.gunho.global.exception.GlobalException;
import dev.gunho.global.service.KafkaProducerService;
import dev.gunho.global.service.RedisService;
import dev.gunho.rule.dto.RuleDto;
import dev.gunho.rule.entity.Rule;
import dev.gunho.rule.repository.RuleRepository;
import dev.gunho.stock.constant.StockConstants;
import dev.gunho.stock.dto.NotiDTO;
import dev.gunho.stock.dto.StockDTO;
import dev.gunho.stock.dto.StockPagePayload;
import dev.gunho.stock.entity.Stock;
import dev.gunho.stock.entity.StockSymbol;
import dev.gunho.stock.mapper.StockMapper;
import dev.gunho.stock.mapper.StockMapperImpl;
import dev.gunho.stock.repository.StockRepository;
import dev.gunho.stock.repository.StockSymbolRepository;
import dev.gunho.user.dto.EmailPayload;
import dev.gunho.user.entity.QInvite;
import dev.gunho.user.entity.QUser;
import dev.gunho.user.entity.User;
import dev.gunho.user.repository.TemplateRepository;
import dev.gunho.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;


    private final StockSymbolRepository stockSymbolRepository;
    private final StockMapper stockMapper = StockMapper.INSTANCE;
    private final StockRepository stockRepository;
    private final RuleRepository ruleRepository;

    private final JPAQueryFactory queryFactory;
    private final KafkaProducerService kafkaProducerService;
    private final RedisService redisService;

    private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식 정의
    private static final int DASHBOARD_TOTAL_SIZE = 30;

    public ResponseEntity<?> getMyStocks(Long idx) {
        HashMap<String, Object> resMap = new HashMap<>();

        List<Stock> stocks = stockRepository.findAllByUserIdx(idx);

        stocks.forEach((stock) -> {
            // Redis에서 해시 데이터 가져오기
            Map<Object, Object> redisStock = redisService.getHashEntries(
                    String.format(StockConstants.STOCK_DAILY_JSON_SYMBOL, stock.getSymbol())
            );

            // Redis에서 가져온 데이터를 LocalDate 기준으로 정렬 후 10개 선택
            List<Map<Object, Object>> top10Entries = redisStock.entrySet().stream()
                    .sorted((entry1, entry2) -> {
                        // 문자열 키를 LocalDate로 변환
                        LocalDate date1 = LocalDate.parse(entry1.getKey().toString(), DEFAULT_DATE_FORMAT);
                        LocalDate date2 = LocalDate.parse(entry2.getKey().toString(), DEFAULT_DATE_FORMAT);

                        return date2.compareTo(date1); // 최신 날짜 순서로 정렬 (내림차순)
                    })
                    .limit(DASHBOARD_TOTAL_SIZE)
                    .map(entry -> Map.of(entry.getKey(), entry.getValue())) // Map으로 변환
                    .toList(); // Stream 결과를 리스트로 변환

            // 결과를 저장
            resMap.put(stock.getSymbol(), top10Entries);
        });


        return ApiResponse.SUCCESS("", resMap);
    }

    public ResponseEntity<?> getSymbols() {
        Map<Object, Object> symbols = redisService.getHashEntries(StockConstants.STOCK_SYMBOL_HASH);

        if (symbols == null) {
            return ApiResponse.NOT_FOUND("요청하신 데이터를 찾을수 없습니다.");
        }

        return ApiResponse.SUCCESS("", symbols);
    }

    public ResponseEntity<?> stockInfo(String symbol) {
        StockSymbol stockSymbol = stockSymbolRepository.findBySymbol(symbol)
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        return ApiResponse.SUCCESS(stockSymbol);
    }

    public ModelAndView addView(ModelAndView mv, Long id) {
        List<Rule> ruleList = ruleRepository.findAllByUser_Idx(id);
        mv.addObject("RULE", ruleList);
        return mv;
    }

    @Transactional
    public ResponseEntity<?> addStock(Long userIdx, StockDTO stockDTO) {
        User user = userRepository.findById(userIdx)
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        Stock stock = stockMapper.toEntity(stockDTO, user);
        Stock saveStock = stockRepository.save(stock);

        return saveStock.getIdx() > 0 ? ApiResponse.SUCCESS("저장이 완료되었습니다.") : ApiResponse.SERVER_ERROR();
    }

    @Transactional
    public ModelAndView getPagingByCondition(ModelAndView mv, PageRequest pageRequest, long userIdx, String condition) {
        Page<Stock> stockPage = stockRepository.findByUserIdxOrderByRegDate(userIdx, pageRequest);
        PagingDTO<StockPagePayload> pagingDTO = stockMapper.toPagingDTO(stockPage);

        List<StockPagePayload> updatedContent = pagingDTO.getContent().stream().map(stock -> {
            String price = redisService.get(String.format(StockConstants.STOCK_DAILY_PRICE_SYMBOL, stock.symbol()));
            RuleDto ruleDto = stock.rule();
            // 평균 가격 가져오기
            Double averagePrice = stock.averagePrice();

            // 매수/매도 비율 가져오기
            BigDecimal sellPercentage = ruleDto.sellPercentage();
            BigDecimal buyPercentage = ruleDto.buyPercentage();

            // 계산
            // 매도가
            Double sellCalculation = averagePrice + (averagePrice * sellPercentage.doubleValue());
            // 매수가
            Double buyCalculation = averagePrice - (averagePrice * buyPercentage.doubleValue());
            BigDecimal sellRounded = new BigDecimal(sellCalculation).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal buyRounded = new BigDecimal(buyCalculation).setScale(2, BigDecimal.ROUND_HALF_UP);


            return stock.withMarketPrice(buyRounded.doubleValue(), sellRounded.doubleValue(), Double.parseDouble(price));
        }).toList(); // 변경된 데이터를 리스트로 수집

        // 변환한 데이터를 PagingDTO에 반영
        pagingDTO.setContent(updatedContent);

        mv.addObject("PAGE", pagingDTO);
        return mv;
    }

    public ResponseEntity<?> sendNotiWithFriends(UserDetail userDetail, NotiDTO notiDTO) {
        User user = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new GlobalException(ApiResponseCode.NOT_FOUND));

        QInvite invite = QInvite.invite; // QueryDSL 메타 모델 사용
        QUser qUser = QUser.user;        // QueryDSL 메타 모델 사용

        List<User> receivedInvites = queryFactory
                .select(qUser) // User 엔티티에서 정보를 가져옵니다.
                .from(invite)
                .leftJoin(invite.invitee, qUser) // Invitee와 Join
                .where(invite.inviter.idx.eq(user.getIdx())) // inviter_id 조건
                .fetch();// 결과 가져오기

        if (receivedInvites.isEmpty()) {
            return ApiResponse.BAD_REQUEST("초대한 친구가 없습니다.");
        }

        List<String> toUsers = receivedInvites.stream().map(User::getEmail).toList();

        Template template = templateRepository.getById("STOCK_NOTI");
        String contents = template.getContent().replace("{id}", user.getUserId())
                .replace("{symbol}", notiDTO.symbol())
                .replace("{type}", notiDTO.isBuy() ? "매수" : "매도");

        EmailPayload payload = EmailPayload.builder()
                .to(toUsers)
                .subject(template.getSubject())
                .from(template.getFrom())
                .contents(contents)
                .build();

        kafkaProducerService.sendMessage("email-topic", payload);
        return ApiResponse.SUCCESS();
    }

    public ResponseEntity<?> getDailySymbol(String symbol) {
        String price = redisService.get(String.format(StockConstants.STOCK_DAILY_PRICE_SYMBOL, symbol));
        if (price != null) {
            return ApiResponse.SUCCESS(symbol, price);
        }

        return ApiResponse.BAD_REQUEST();
    }
}
