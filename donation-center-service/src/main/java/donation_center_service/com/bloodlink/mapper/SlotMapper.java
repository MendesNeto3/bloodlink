package donation_center_service.com.bloodlink.mapper;

import donation_center_service.com.bloodlink.domain.model.AppointmentSlot;
import donation_center_service.com.bloodlink.dto.response.SlotResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SlotMapper {

    @Mapping(source = "dateHour", target = "date")
    @Mapping(source = "time", target = "hour")
    @Mapping(source = "centerId", target = "centerId", qualifiedByName = "uuidToString")
    @Mapping(source = "capacityTotal", target = "capacityTotal", qualifiedByName = "stringToInteger")
    SlotResponse toResponse(AppointmentSlot slot);

    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString() : null;
    }

    @Named("stringToInteger")
    default Integer stringToInteger(String value) {
        return value != null ? Integer.valueOf(value) : null;
    }
}
