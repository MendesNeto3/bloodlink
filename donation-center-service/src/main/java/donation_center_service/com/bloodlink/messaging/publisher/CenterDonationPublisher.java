package donation_center_service.com.bloodlink.messaging.publisher;

import donation_center_service.com.bloodlink.dto.request.DonationCenterCreatedEvent;
import donation_center_service.com.bloodlink.messaging.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CenterDonationPublisher {
    private static final String ROUTING_KEY = "donation.created";
    private final RabbitTemplate rabbitTemplate;

    public CenterDonationPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishDonationCreate(DonationCenterCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                ROUTING_KEY,
                event);
    }
}
