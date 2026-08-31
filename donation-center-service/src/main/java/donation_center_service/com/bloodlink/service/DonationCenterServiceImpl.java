package donation_center_service.com.bloodlink.service;

import donation_center_service.com.bloodlink.dto.request.CenterSearchRequest;
import donation_center_service.com.bloodlink.dto.request.CreateCenterRequest;
import donation_center_service.com.bloodlink.dto.response.CenterResponse;
import java.util.List;
import java.util.UUID;


public interface DonationCenterServiceImpl {
    CenterResponse createDonationCenter(CreateCenterRequest request);
    List<CenterResponse> listCenters ();
    CenterResponse findById (UUID id);
    List<CenterResponse> searchCenters ( CenterSearchRequest request);
}
