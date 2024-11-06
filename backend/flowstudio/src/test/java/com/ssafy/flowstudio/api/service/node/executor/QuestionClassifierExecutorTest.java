package com.ssafy.flowstudio.api.service.node.executor;

import com.ssafy.flowstudio.api.service.node.RedisService;
import com.ssafy.flowstudio.common.constant.ChatEnvVariable;
import com.ssafy.flowstudio.domain.chat.entity.Chat;
import com.ssafy.flowstudio.domain.chatflow.entity.ChatFlow;
import com.ssafy.flowstudio.domain.chatflow.repository.ChatFlowRepository;
import com.ssafy.flowstudio.domain.edge.entity.Edge;
import com.ssafy.flowstudio.domain.node.entity.Answer;
import com.ssafy.flowstudio.domain.node.entity.Coordinate;
import com.ssafy.flowstudio.domain.node.entity.QuestionClass;
import com.ssafy.flowstudio.domain.node.entity.QuestionClassifier;
import com.ssafy.flowstudio.domain.user.entity.User;
import com.ssafy.flowstudio.domain.user.repository.UserRepository;
import com.ssafy.flowstudio.support.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
class QuestionClassifierExecutorTest extends IntegrationTestSupport {

    @Mock
    private ApplicationEventPublisher publisher;

    @Autowired
    private QuestionClassifierExecutor questionClassifierExecutor;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatFlowRepository chatFlowRepository;

    @Autowired
    private RedisService redisService;

    @DisplayName("질문 분류기 노드를 실행한다.")
    @Test
    void execute() {
        // given
        User user = User.builder()
                .username("test")
                .build();

        ChatFlow chatFlow = ChatFlow.builder()
                .owner(user)
                .author(user)
                .title("title")
                .build();

        Coordinate coordinate = Coordinate.builder()
                .x(1)
                .y(1)
                .build();

        QuestionClassifier questionClassifier = QuestionClassifier.create(chatFlow, coordinate);

        QuestionClass questionClass1 = QuestionClass.create(questionClassifier, "한국");
        QuestionClass questionClass2 = QuestionClass.create(questionClassifier, "중국");
        QuestionClass questionClass3 = QuestionClass.create(questionClassifier, "일본");

        questionClassifier.addQuestionClass(questionClass1);
        questionClassifier.addQuestionClass(questionClass2);
        questionClassifier.addQuestionClass(questionClass3);

        Answer answer1 = Answer.create(chatFlow, coordinate);
        Answer answer2 = Answer.create(chatFlow, coordinate);
        Answer answer3 = Answer.create(chatFlow, coordinate);

        Edge edge1 = Edge.create(questionClassifier, answer1);
        Edge edge2 = Edge.create(questionClassifier, answer2);
        Edge edge3 = Edge.create(questionClassifier, answer3);

        questionClass1.updateEdge(edge1);
        questionClass2.updateEdge(edge2);
        questionClass3.updateEdge(edge3);

        chatFlow.addNode(questionClassifier);
        chatFlow.addNode(answer1);
        chatFlow.addNode(answer2);
        chatFlow.addNode(answer3);

        userRepository.save(user);
        chatFlowRepository.save(chatFlow);

        Chat chat = Chat.create(user, chatFlow);

        redisService.save(chat.getId(), ChatEnvVariable.INPUT_MESSAGE, "스시에 대해 알려줘");

        // when
        questionClassifierExecutor.execute(questionClassifier, chat);
    }
}