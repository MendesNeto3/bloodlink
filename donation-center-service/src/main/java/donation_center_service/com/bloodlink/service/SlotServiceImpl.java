package donation_center_service.com.bloodlink.service;

import donation_center_service.com.bloodlink.dto.request.CreateSlotRequest;
import donation_center_service.com.bloodlink.dto.response.SlotResponse;

import java.util.UUID;

public interface SlotServiceImpl {
    SlotResponse createSlot(CreateSlotRequest createSlotRequest, UUID centerId);
}
