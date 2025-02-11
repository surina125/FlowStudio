
package com.ssafy.flowstudio.api.service.node.executor;

import com.ssafy.flowstudio.api.controller.sse.SseEmitters;
import com.ssafy.flowstudio.api.service.node.RedisService;
import com.ssafy.flowstudio.domain.chat.entity.Chat;
import com.ssafy.flowstudio.domain.node.entity.Node;
import com.ssafy.flowstudio.domain.node.entity.NodeType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class VariableAssignerExecutor extends NodeExecutor {

    public VariableAssignerExecutor(RedisService redisService, ApplicationEventPublisher eventPublisher, SseEmitters sseEmitters) {
        super(redisService, eventPublisher, sseEmitters);
    }

    @Override
    public void execute(Node node, Chat chat) {
        System.out.println("VariableAssignerExecutor");
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.VARIABLE_ASSIGNER;
    }
}
