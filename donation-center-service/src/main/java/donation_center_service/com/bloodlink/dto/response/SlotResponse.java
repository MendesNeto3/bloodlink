package donation_center_service.com.bloodlink.dto.response;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record SlotResponse(
        UUID id,
        String centerId,
        LocalDate date,
        LocalTime hour,
        String capacityTotal
) {
}
