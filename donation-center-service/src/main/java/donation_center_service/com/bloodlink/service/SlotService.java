package donation_center_service.com.bloodlink.service;

import donation_center_service.com.bloodlink.domain.exception.CenterNotFoundException;
import donation_center_service.com.bloodlink.domain.exception.DuplicateSlotException;
import donation_center_service.com.bloodlink.domain.exception.InsufficientCapacityException;
import donation_center_service.com.bloodlink.domain.exception.UnableCreateSlotException;
import donation_center_service.com.bloodlink.domain.model.AppointmentSlot;
import donation_center_service.com.bloodlink.domain.model.DonationCenter;
import donation_center_service.com.bloodlink.dto.request.CreateSlotRequest;
import donation_center_service.com.bloodlink.dto.request.SlotCreatedEvent;
import donation_center_service.com.bloodlink.dto.response.SlotResponse;
import donation_center_service.com.bloodlink.mapper.SlotMapper;
import donation_center_service.com.bloodlink.messaging.publisher.SlotEventpublisher;
import donation_center_service.com.bloodlink.repository.AppointmentSlotRepository;
import donation_center_service.com.bloodlink.repository.DonationCenterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@EnableCaching
@RequiredArgsConstructor
public class SlotService implements SlotServiceImpl{

    private final AppointmentSlotRepository repository;
    private  final DonationCenterRepository donationCenterRepository;
    private final SlotEventpublisher slotEventpublisher;
    private final SlotMapper slotMapper;

    @Override
    @Transactional
    @CacheEvict(value = "centerSlots", key = "#centerId")
    public SlotResponse createSlot(CreateSlotRequest createSlotRequest, UUID centerId) {
        DonationCenter center = donationCenterRepository
                .findById(centerId)
                .orElseThrow(() -> new CenterNotFoundException("Center not found!"));

        if (createSlotRequest.data().isBefore(LocalDate.now())) {
            throw new UnableCreateSlotException
                    ("It was not possible to create the slot because it is in the past.");
        }

        if (createSlotRequest.capacityTotal() <= 0) {
            throw new InsufficientCapacityException
                    ("Capacity must be greater than zero.");
        }

        List<AppointmentSlot> slotsForDate = repository
                .findByCenterIdAndDateHour(center.getId(), createSlotRequest.data());

        boolean alreadyExists = slotsForDate
                .stream()
                .anyMatch(s -> s.getTime().equals(createSlotRequest.time()));

        if (alreadyExists) {
            throw new DuplicateSlotException(
                     "A slot already exists for this center, date and time.");
        }

        AppointmentSlot slot = new AppointmentSlot();
        slot.setDateHour(createSlotRequest.data());
        slot.setTime(createSlotRequest.time());
        slot.setCapacityTotal(String.valueOf(createSlotRequest.capacityTotal()));

        AppointmentSlot slotSaved = repository.save(slot);

        SlotCreatedEvent slotCreatedEvent = new SlotCreatedEvent(
                slotSaved.getId(),
                slotSaved.getCenterId(),
                slotSaved.getCapacityTotal(),
                slotSaved.getDateHour(),
                slotSaved.getTime()
        );

        slotEventpublisher.publishSlotCreated(slotCreatedEvent);

        return slotMapper.toResponse(slotSaved);
    }
}
