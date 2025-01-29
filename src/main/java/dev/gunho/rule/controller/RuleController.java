package dev.gunho.rule.controller;

import dev.gunho.global.dto.UserDetail;
import dev.gunho.rule.dto.RuleDto;
import dev.gunho.rule.service.RuleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/add")
    public ModelAndView addRuleView(@AuthenticationPrincipal UserDetail userDetail) {
        return ruleService.addView(new ModelAndView("/pages/rule/add"), userDetail.getId());
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addRule(@AuthenticationPrincipal UserDetail userDetail, @RequestBody final RuleDto ruleDto) {
        return ruleService.addRule();
    }
}
