package donation_center_service.com.bloodlink.dto.request;

import java.util.UUID;

public record DonationCenterCreatedEvent(
        UUID centerId,
        String name,
        String adress,
        String city
) {
}
