package donation_center_service.com.bloodlink.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateSlotRequest  (
        LocalDate data,
        LocalTime time,
        String capacityTotal
){
}
