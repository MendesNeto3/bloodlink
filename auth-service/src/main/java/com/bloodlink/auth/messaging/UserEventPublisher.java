package com.bloodlink.auth.messaging;

import com.bloodlink.auth.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private static final String ROUTING_KEY = "user.registered";

    private final RabbitTemplate rabbitTemplate;

    public void publishUserRegistered(User event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, ROUTING_KEY, event);
    }
}
