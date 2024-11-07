package com.ssafy.flowstudio.api.service.node.response.factory;

import com.ssafy.flowstudio.api.service.node.response.NodeResponse;
import com.ssafy.flowstudio.api.service.node.response.QuestionClassifierResponse;
import com.ssafy.flowstudio.domain.node.entity.Node;
import com.ssafy.flowstudio.domain.node.entity.QuestionClassifier;

public class QuestionClassifierResponseFactory extends NodeResponseFactory {
    @Override
    public NodeResponse createNodeResponse(Node node) {
        return QuestionClassifierResponse.from((QuestionClassifier) node);
    }

    @Override
    public NodeResponse createNodeDetailResponse(Node node) {
        return QuestionClassifierResponse.from((QuestionClassifier) node);
    }
}
