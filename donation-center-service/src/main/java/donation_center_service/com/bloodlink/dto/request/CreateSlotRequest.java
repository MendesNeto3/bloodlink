package donation_center_service.com.bloodlink.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateSlotRequest  (
        @NotNull(message = "Date is required")
        @FutureOrPresent(message = "Date cannot be in the past")
        LocalDate data,

        @NotNull(message = "Time is required")
        LocalTime time,

        @NotNull(message = "Capacity is required")
        @Positive(message = "Capacity must be greater than zero")
        Integer capacityTotal
) {
}