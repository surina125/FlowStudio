package com.ssafy.flowstudio.api.controller.user;

import com.ssafy.flowstudio.api.controller.user.request.ApiKeyRequest;
import com.ssafy.flowstudio.api.service.user.request.ApiKeyServiceRequest;
import com.ssafy.flowstudio.api.service.user.response.ApiKeyResponse;
import com.ssafy.flowstudio.support.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiKeyControllerTest extends ControllerTestSupport {

    @DisplayName("사용자의 Api Key를 수정한다.")
    @WithMockUser
    @Test
    void updateApiKey() throws Exception {
        // given
        ApiKeyRequest apiKeyRequest = ApiKeyRequest.builder()
                .openAiKey("openai_or_null")
                .claudeKey("claude_or_null")
                .geminiKey("gemini_or_null")
                .clovaKey("clova_or_null")
                .build();

        // when
        ResultActions perform = mockMvc.perform(
                put("/api/v1/users/keys")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(apiKeyRequest))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        verify(apiKeyService, times(1)).updateApiKey(any(), any(ApiKeyServiceRequest.class));

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @DisplayName("사용자의 Api Key를 조회한다.")
    @WithMockUser
    @Test
    void getApiKey() throws Exception {
        // given
        given(apiKeyService.getApiKey(any()))
                .willReturn(ApiKeyResponse.from(
                        "decryptedOpenAiKey",
                        "decryptedClaudeKey",
                        "decryptedGeminiKey",
                        "decryptedClovaKey"
                ));

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/users/keys"));

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.openAiKey").value("decryptedOpenAiKey"))
                .andExpect(jsonPath("$.data.claudeKey").value("decryptedClaudeKey"))
                .andExpect(jsonPath("$.data.geminiKey").value("decryptedGeminiKey"))
                .andExpect(jsonPath("$.data.clovaKey").value("decryptedClovaKey"));
    }

    @DisplayName("사용자의 빈 Api Key를 조회한다.")
    @WithMockUser
    @Test
    void getEmptyApiKey() throws Exception {
        // given
        given(apiKeyService.getApiKey(any()))
                .willReturn(ApiKeyResponse.from(null, null, null, null));

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/users/keys"));

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.openAiKey").value(org.hamcrest.Matchers.nullValue()))
                .andExpect(jsonPath("$.data.claudeKey").value(org.hamcrest.Matchers.nullValue()))
                .andExpect(jsonPath("$.data.geminiKey").value(org.hamcrest.Matchers.nullValue()))
                .andExpect(jsonPath("$.data.clovaKey").value(org.hamcrest.Matchers.nullValue()));
    }
}