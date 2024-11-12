package com.ssafy.flowstudio.api.controller.chat;

import com.ssafy.flowstudio.api.controller.chat.request.ChatCreateRequest;
import com.ssafy.flowstudio.api.controller.chat.request.ChatMessageRequest;
import com.ssafy.flowstudio.api.service.chat.ChatService;
import com.ssafy.flowstudio.api.service.chat.response.ChatCreateResponse;
import com.ssafy.flowstudio.api.service.chat.response.ChatListResponse;
import com.ssafy.flowstudio.common.annotation.CurrentUser;
import com.ssafy.flowstudio.common.payload.ApiResponse;
import com.ssafy.flowstudio.domain.user.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    /**
     * 채팅 목록 조회
     * @param user
     * @param chatFlowId
     * @return
     */
    @GetMapping(value = "/api/v1/chat-flows/{chatFlowId}/chats")
    public ApiResponse<ChatListResponse> getChats(
            @CurrentUser User user,
            @PathVariable Long chatFlowId
    ) {
        return ApiResponse.ok(chatService.getChats(user, chatFlowId));
    }


    /**
     * 채팅 생성
     * @param user
     * @return
     */
    @PostMapping(value = "/api/v1/chat-flows/{chatFlowId}/chats")
    public ApiResponse<ChatCreateResponse> createChat(
            @CurrentUser User user,
            @PathVariable Long chatFlowId,
            @Valid @RequestBody ChatCreateRequest request,
            HttpServletResponse servletResponse
    ) {
        return ApiResponse.ok(chatService.createChat(user, chatFlowId, request.toServiceRequest(), servletResponse));
    }

    /**
     * 채팅 메시지 생성
     * @param chatId
     * @param request
     * @return
     */
    @PostMapping(value = "/api/v1/chats/{chatId}")
    public ApiResponse<Void> sendChatMessage(
            @PathVariable Long chatId,
            @Valid @RequestBody ChatMessageRequest request
    ) {
        chatService.sendMessage(chatId, request.toServiceRequest());
        return ApiResponse.ok();
    }

}
