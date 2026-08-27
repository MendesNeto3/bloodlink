package donation_center_service.com.bloodlink.mapper;

import donation_center_service.com.bloodlink.domain.model.DonationCenter;
import donation_center_service.com.bloodlink.dto.response.CenterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CenterDonationMapper {

 CenterResponse toResponse (DonationCenter donationCenter);
}
