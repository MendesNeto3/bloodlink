package donation_center_service.com.bloodlink.dto.response;

import java.util.UUID;

public record CenterResponse (
        UUID id,
        String name,
        String adress,
        String city
) {
}
