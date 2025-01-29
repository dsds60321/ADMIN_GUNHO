package dev.gunho.rule.service;

import dev.gunho.global.dto.ApiResponse;
import dev.gunho.rule.entity.Rule;
import dev.gunho.rule.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;

    public ModelAndView addView(ModelAndView mv, Long id) {
        List<Rule> ruleList = ruleRepository.findAllByUser_Idx(id);
        mv.addObject("RULE", ruleList);
        return mv;
    }

    public ResponseEntity<?> addRule() {
        return ApiResponse.SERVER_ERROR();
    }
}
