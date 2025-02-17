package dev.gunho.chat.controller;

import dev.gunho.chat.dto.ChatDto;
import dev.gunho.chat.dto.ChatReqDto;
import dev.gunho.chat.service.ChatService;
import dev.gunho.global.dto.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/form")
    public ModelAndView chatForm(@AuthenticationPrincipal UserDetail userDetail) {
        return new ModelAndView("/pages/user/chat/form", "userId", userDetail.getUserId());
    }

    @GetMapping("/paging")
    public ModelAndView paging(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "condition", defaultValue = "new") String condition,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        // 페이지 요청 객체를 생성하고, 서비스 호출
        PageRequest pageRequest = PageRequest.of(page, size);
        return chatService.getPagingByCondition(new ModelAndView("/pages/user/chat/list"), pageRequest, userDetail, condition);
    }

    @GetMapping
    public String chat() {
        return "/pages/user/chat/modal/add";
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<?> createChat(@AuthenticationPrincipal UserDetail userDetail, @RequestBody ChatDto chatDto) {
        return chatService.createChat(userDetail, chatDto);
    }

    @GetMapping("/{idx}")
    public ModelAndView chatDetail(@AuthenticationPrincipal UserDetail userDetail, @PathVariable("idx") Long idx) {
        return new ModelAndView("/pages/user/chat/modal/room", "idx", idx)
                .addObject("userId", userDetail.getUserId())
                .addObject("userIdx", userDetail.getId());
    }

    @Description("채팅 요청")
    @MessageMapping("/send")
    @SendTo("/room")
    public @ResponseBody ResponseEntity<?> requestChat(@RequestBody ChatReqDto chatReqDto) {
        return chatService.sendChat(chatReqDto);
    }

    @PostMapping("/recv")
    public @ResponseBody ResponseEntity<?> recvChat(@AuthenticationPrincipal UserDetail userDetail, @RequestBody ChatReqDto chatReqDto) {
        return chatService.recvChat(userDetail, chatReqDto);
    }

}
