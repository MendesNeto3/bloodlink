package donation_center_service.com.bloodlink.messaging.publisher;

import donation_center_service.com.bloodlink.dto.request.SlotCreatedEvent;
import donation_center_service.com.bloodlink.messaging.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SlotEventpublisher {

    private static final String ROUTING_KEY = "slot.created";
    private final RabbitTemplate rabbitTemplate;

    public SlotEventpublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishSlotCreated(SlotCreatedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, ROUTING_KEY, event);
    }
}
