package com.ssafy.flowstudio.api.service.chatflow.response;

import com.ssafy.flowstudio.api.service.node.response.NodeResponse;
import com.ssafy.flowstudio.api.service.node.response.factory.NodeResponseFactory;
import com.ssafy.flowstudio.api.service.node.response.factory.NodeResponseFactoryProvider;
import com.ssafy.flowstudio.domain.chatflow.entity.ChatFlow;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class ChatFlowResponse {

    private final Long chatFlowId;
    private final String title;
    private final String publishUrl;
    private final String publishedAt;
    private final List<NodeResponse> nodes;
    private final List<EdgeResponse> edges;

    @Builder
    private ChatFlowResponse(Long chatFlowId, String title, List<NodeResponse> nodes, List<EdgeResponse> edges, String publishUrl, LocalDateTime publishedAt) {
        this.chatFlowId = chatFlowId;
        this.title = title;
        this.nodes = nodes;
        this.edges = edges;
        this.publishUrl = publishUrl;
        this.publishedAt = publishedAt != null ? publishedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;

    }

    public static ChatFlowResponse from(ChatFlow chatFlow, List<EdgeResponse> edges) {
        return ChatFlowResponse.builder()
                .chatFlowId(chatFlow.getId())
                .title(chatFlow.getTitle())
                .nodes(chatFlow.getNodes().stream()
                        .map(node -> {
                            NodeResponseFactory factory = NodeResponseFactoryProvider.getFactory(node.getType());

                            return factory.createNodeResponse(node);
                        })
                        .toList())
                .edges(edges)
                .publishUrl(chatFlow.getPublishUrl())
                .publishedAt(chatFlow.getPublishedAt())
                .build();
    }

}
