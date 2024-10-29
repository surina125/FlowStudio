package com.ssafy.flowstudio.domain.node.factory;

import com.ssafy.flowstudio.domain.node.entity.*;

public class QuestionClassifierFactory extends NodeFactory {
    @Override
    public Node createNode(Coordinate coordinate) {
        return QuestionClassifier.create(coordinate);
    }
}