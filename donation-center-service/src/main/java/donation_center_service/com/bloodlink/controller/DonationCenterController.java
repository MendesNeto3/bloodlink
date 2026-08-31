package donation_center_service.com.bloodlink.controller;

import donation_center_service.com.bloodlink.dto.request.CenterSearchRequest;
import donation_center_service.com.bloodlink.dto.request.CreateCenterRequest;
import donation_center_service.com.bloodlink.dto.response.CenterResponse;
import donation_center_service.com.bloodlink.service.DonationCenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/centers")
@RequiredArgsConstructor
public class DonationCenterController {
    private final DonationCenterService service;

    @PostMapping("/created")
    public ResponseEntity<CenterResponse> createDonationCenter(@RequestBody @Valid CreateCenterRequest request){
        CenterResponse donationCenter = service.createDonationCenter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(donationCenter);
    }

    @GetMapping
    public ResponseEntity<List<CenterResponse>> listCenters() {
        return ResponseEntity.ok(service.listCenters());
    }

    @GetMapping()
    public ResponseEntity<CenterResponse> getDonationCenter(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CenterResponse>> searchDonationCenter(@ModelAttribute CenterSearchRequest filters){
        return ResponseEntity.ok(service.searchCenters(filters));
    }
 }
