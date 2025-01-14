package com.ssafy.flowstudio.domain.node.entity;

import com.ssafy.flowstudio.domain.chat.entity.Chat;
import com.ssafy.flowstudio.domain.chatflow.entity.ChatFlow;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Answer extends Node {

    @Lob
    private String outputMessage;

    @Builder
    private Answer(Long id, ChatFlow chatFlow, String name, NodeType type, Coordinate coordinate, String outputMessage) {
        super(id, chatFlow, name, type, coordinate);
        this.outputMessage = outputMessage;
    }

    public static Answer create(ChatFlow chatFlow, Coordinate coordinate) {
        return Answer.builder()
            .chatFlow(chatFlow)
            .name("답변")
            .type(NodeType.ANSWER)
            .coordinate(coordinate)
            .build();
    }

    public static Answer create(ChatFlow chatFlow, String name, Coordinate coordinate) {
        return Answer.builder()
            .chatFlow(chatFlow)
            .name(name)
            .type(NodeType.ANSWER)
            .coordinate(coordinate)
            .build();
    }

    @Override
    public void accept(NodeVisitor visitor, Chat chat) {
        visitor.visit(this, chat);
    }

    public void update(String name, Coordinate coordinate, String outputMessage) {
        this.name = name;
        this.coordinate = coordinate;
        this.outputMessage = outputMessage;
    }

    public void updateOutputMessage(String message) {
        this.outputMessage = message;
    }
}
