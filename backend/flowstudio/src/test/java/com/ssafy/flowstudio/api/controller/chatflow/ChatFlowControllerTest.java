package com.ssafy.flowstudio.api.controller.chatflow;

import com.ssafy.flowstudio.api.controller.chatflow.request.ChatFlowCreateRequest;
import com.ssafy.flowstudio.api.service.chatflow.request.ChatFlowCreateServiceRequest;
import com.ssafy.flowstudio.api.service.chatflow.response.CategoryResponse;
import com.ssafy.flowstudio.api.service.chatflow.response.ChatFlowListResponse;
import com.ssafy.flowstudio.api.service.chatflow.response.ChatFlowResponse;
import com.ssafy.flowstudio.api.service.chatflow.response.ChatFlowUpdateResponse;
import com.ssafy.flowstudio.api.service.node.response.NodeResponse;
import com.ssafy.flowstudio.api.service.user.response.UserResponse;
import com.ssafy.flowstudio.domain.user.entity.User;
import com.ssafy.flowstudio.support.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatFlowControllerTest extends ControllerTestSupport {

    @DisplayName("내 챗플로우 목록을 조회한다.")
    @WithMockUser
    @Test
    void getChatFlows() throws Exception {
        // given
        UserResponse author = UserResponse.builder()
                .id(1L)
                .username("username")
                .nickname("nickname")
                .profileImage("profileImage")
                .build();

        CategoryResponse category1 = CategoryResponse.builder()
                .categoryId(1L)
                .name("카테고리1")
                .build();

        CategoryResponse category2 = CategoryResponse.builder()
                .categoryId(2L)
                .name("카테고리2")
                .build();

        ChatFlowListResponse response = ChatFlowListResponse.builder()
                .chatFlowId(1L)
                .title("title")
                .description("description")
                .author(author)
                .thumbnail("1")
                .categories(List.of(category1, category2))
                .isPublic(false)
                .build();

        given(chatFlowService.getChatFlows(any(User.class)))
                .willReturn(List.of(response));

        // when
        ResultActions perform = mockMvc.perform(
                get("/api/v1/chat-flows")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("챗플로우를 상세조회한다.")
    @WithMockUser
    @Test
    void getChatFlow() throws Exception {
        // given


        ChatFlowResponse response = ChatFlowResponse.builder()
                .chatFlowId(1L)
                .title("title")
                .build();

        given(chatFlowService.getChatFlow(any(User.class), any(Long.class)))
                .willReturn(response);
        // when
        ResultActions perform = mockMvc.perform(
                get("/api/v1/chat-flows/{chatFlowId}")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON));

        // then
    }

    @DisplayName("챗플로우를 생성한다.")
    @WithMockUser
    @Test
    void createChatFlows() throws Exception {
        // given
        ChatFlowCreateRequest request = ChatFlowCreateRequest.builder()
                .title("title")
                .thumbnail("thumbnail")
                .description("description")
                .categoryIds(List.of(1L, 2L))
                .build();

        // when
        ResultActions perform = mockMvc.perform(
                post("/api/v1/chat-flows")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("챗플로우 정보를 수정한다.")
    @WithMockUser
    @Test
    void updateChatFlows() throws Exception {
        // given
        ChatFlowCreateRequest request = ChatFlowCreateRequest.builder()
                .title("title")
                .thumbnail("thumbnail")
                .description("description")
                .categoryIds(List.of(1L, 2L))
                .build();

        ChatFlowUpdateResponse response = ChatFlowUpdateResponse.builder()
                .chatFlowId(1L)
                .title("title")
                .description("description")
                .thumbnail("thumbnail")
                .build();

        given(chatFlowService.updateChatFlow(any(User.class), any(Long.class), any(ChatFlowCreateServiceRequest.class)))
                .willReturn(response);

        // when
        ResultActions perform = mockMvc.perform(
                patch("/api/v1/chat-flows/{chatFlowId}", 1L)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("챗플로우를 삭제한다.")
    @WithMockUser
    @Test
    void deleteChatFlows() throws Exception {
        // given

        // when
        ResultActions perform = mockMvc.perform(
                delete("/api/v1/chat-flows/{chatFlowId}", 1L)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}