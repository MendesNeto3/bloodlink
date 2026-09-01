package donation_center_service.com.bloodlink.service;

import donation_center_service.com.bloodlink.domain.exception.CenterNotFoundException;
import donation_center_service.com.bloodlink.domain.model.DonationCenter;
import donation_center_service.com.bloodlink.dto.request.CenterSearchRequest;
import donation_center_service.com.bloodlink.dto.request.CreateCenterRequest;
import donation_center_service.com.bloodlink.dto.response.CenterResponse;
import donation_center_service.com.bloodlink.mapper.CenterDonationMapper;
import donation_center_service.com.bloodlink.dto.request.DonationCenterCreatedEvent;
import donation_center_service.com.bloodlink.messaging.publisher.CenterDonationPublisher;
import donation_center_service.com.bloodlink.repository.DonationCenterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@EnableCaching
@RequiredArgsConstructor
public class DonationCenterService implements DonationCenterServiceImpl {

    private final DonationCenterRepository donationCenterRepository;
    private final CenterDonationPublisher publisher;
    private final CenterDonationMapper mapper;

    @Override
    @CacheEvict(value = "centers", allEntries = true)
    @Transactional
    public CenterResponse createDonationCenter(CreateCenterRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }

        DonationCenter donationCenter = new DonationCenter();
        donationCenter.setName(request.name());
        donationCenter.setCity(request.city());
        donationCenter.setAdress(request.address());

        DonationCenter savedDonation = donationCenterRepository.save(donationCenter);

        DonationCenterCreatedEvent event = new DonationCenterCreatedEvent(
                savedDonation.getId(),
                savedDonation.getName(),
                savedDonation.getCity(),
                savedDonation.getAdress()
        );

        publisher.publishDonationCreate(event);

        return mapper.toResponse(savedDonation);
    }

    @Override
    @Cacheable(value = "centers")
    public List<CenterResponse> listCenters() {
      return  donationCenterRepository
              .findAll()
              .stream()
              .map(mapper::toResponse)
              .toList();
    }

    @Override
    @Cacheable(value = "center", key = "#id")
    public CenterResponse findById(UUID id) {
        DonationCenter donationCenter = donationCenterRepository.findById(id)
                .orElseThrow(()-> new CenterNotFoundException("Center not found."));
        return mapper.toResponse(donationCenter);
    }

    @Override
    public List<CenterResponse> searchCenters(CenterSearchRequest request) {
        return donationCenterRepository
                .search(request.name(), request.city())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}


