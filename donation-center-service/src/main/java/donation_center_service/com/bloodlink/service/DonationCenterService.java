package donation_center_service.com.bloodlink.service;

import donation_center_service.com.bloodlink.domain.exception.CenterNotFoundException;
import donation_center_service.com.bloodlink.domain.model.DonationCenter;
import donation_center_service.com.bloodlink.dto.request.CreateCenterRequest;
import donation_center_service.com.bloodlink.dto.response.CenterResponse;
import donation_center_service.com.bloodlink.mapper.CenterDonationMapper;
import donation_center_service.com.bloodlink.dto.request.DonationCenterCreatedEvent;
import donation_center_service.com.bloodlink.messaging.publisher.CenterDonationPublisher;
import donation_center_service.com.bloodlink.repository.DonationCenterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DonationCenterService implements DonationCenterServiceImpl {

    private final DonationCenterRepository donationCenterRepository;
    private final CenterDonationPublisher publisher;
    private final CenterDonationMapper mapper;

    @Override
    @Transactional
    public CenterResponse createDonationCenter(CreateCenterRequest request) {
        if (request == null ) {
            throw new IllegalArgumentException("request is null");
        }

        DonationCenter donationCenter = new DonationCenter();
        donationCenter.setName(request.name());
        donationCenter.setCity(request.city());
        donationCenter.setAdress(request.address());

        DonationCenter savedDonetion = donationCenterRepository.save(donationCenter);

        DonationCenterCreatedEvent event = new DonationCenterCreatedEvent(
                savedDonetion.getId(),
                savedDonetion.getName(),
                savedDonetion.getCity(),
                savedDonetion.getAdress()
        );

        publisher.publishDonationCreate(event);

        return mapper.toResponse(savedDonetion);
    }

    @Override
    public List<CenterResponse> listCenters() {
      return  donationCenterRepository.findAll()
              .stream()
              .map(mapper::toResponse)
              .toList();
    }

    @Override
    public CenterResponse findById(UUID id) {
        DonationCenter donationCenter = donationCenterRepository.findById(id)
                .orElseThrow(()->
                        new CenterNotFoundException("Center not found."));
        return mapper.toResponse(donationCenter);
    }
}


