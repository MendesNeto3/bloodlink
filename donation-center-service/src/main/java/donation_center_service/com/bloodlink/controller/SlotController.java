package donation_center_service.com.bloodlink.controller;

import donation_center_service.com.bloodlink.dto.request.CreateSlotRequest;
import donation_center_service.com.bloodlink.dto.response.SlotResponse;
import donation_center_service.com.bloodlink.service.SlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    @PostMapping("/{centerId}/created")
    public ResponseEntity<SlotResponse> createSlot(
            @RequestBody @Valid CreateSlotRequest slot, @PathVariable UUID centerId) {
        SlotResponse slotResponse = slotService.createSlot(slot, centerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(slotResponse);
    }
}
