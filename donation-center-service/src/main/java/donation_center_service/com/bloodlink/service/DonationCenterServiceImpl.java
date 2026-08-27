package donation_center_service.com.bloodlink.service;

import donation_center_service.com.bloodlink.dto.request.CreateCenterRequest;
import donation_center_service.com.bloodlink.dto.response.CenterResponse;

public interface DonationCenterServiceImpl {
    CenterResponse createDonationCenter(CreateCenterRequest request);
}
