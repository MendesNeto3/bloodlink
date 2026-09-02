package donation_center_service.com.bloodlink.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record SlotCreatedEvent(
        UUID slotId,
        UUID centerId,
        String capacityTotal,
        LocalDate date,
        LocalTime time
) {

}
