package dev.gunho.rule.controller;

import dev.gunho.global.dto.UserDetail;
import dev.gunho.rule.dto.RuleDto;
import dev.gunho.rule.service.RuleService;
import dev.gunho.stock.dto.StockDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/rule")
public class RuleController {

    private final RuleService ruleService;

    @GetMapping("/form")
    public String form() {
        return "/pages/rule/form";
    }

    @GetMapping("/paging")
    public ModelAndView paging(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "condition", defaultValue = "new") String condition
    ) {
        // 페이지 요청 객체를 생성하고, 서비스 호출
        PageRequest pageRequest = PageRequest.of(page, size);
        return ruleService.getPagingByCondition(new ModelAndView("/pages/rule/list"), pageRequest, condition);
    }

    @GetMapping("/add")
    public ModelAndView addView(@AuthenticationPrincipal UserDetail userDetail) {
        return ruleService.addView(new ModelAndView("/pages/rule/add"), userDetail.getId());
    }

    /**
     * 주식 등록
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addRule(@AuthenticationPrincipal UserDetail userDetail, @RequestBody final RuleDto ruleDto) {
        return ruleService.add(userDetail.getId(), ruleDto);
    }
}
