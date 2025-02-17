package dev.gunho.user.controller;

import dev.gunho.global.dto.UserDetail;
import dev.gunho.user.dto.InviteDto;
import dev.gunho.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @ResponseBody
    public ResponseEntity<?> getAuthenticatedUser(@AuthenticationPrincipal UserDetail userDetail) {
        return userService.getUser(userDetail.getId());
    }

    @GetMapping("/friends")
    @ResponseBody
    public ResponseEntity<?> getFriends(@AuthenticationPrincipal UserDetail userDetail) {
        return userService.getFriends(userDetail);
    }

    @GetMapping("/friends/form")
    public String form() {
        return "/pages/user/friends/form";
    }

    @GetMapping("/friends/paging")
    public ModelAndView paging(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "condition", defaultValue = "new") String condition,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        // 페이지 요청 객체를 생성하고, 서비스 호출
        PageRequest pageRequest = PageRequest.of(page, size);
        return userService.getPagingByCondition(new ModelAndView("/pages/user/friends/list"), pageRequest, userDetail.getId(), condition);
    }

    @GetMapping("/friends/invite")
    public String inviteForm() {
        return "/pages/user/friends/modal/invite";
    }

    @PostMapping("/friends/invite")
    public @ResponseBody ResponseEntity<?> inviteFriend(@AuthenticationPrincipal UserDetail userDetail, @RequestBody final InviteDto inviteDto) {
        return userService.invite(userDetail, inviteDto);
    }
}
