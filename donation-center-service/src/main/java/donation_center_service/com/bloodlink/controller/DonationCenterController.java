package donation_center_service.com.bloodlink.controller;

import donation_center_service.com.bloodlink.dto.request.CreateCenterRequest;
import donation_center_service.com.bloodlink.dto.response.CenterResponse;
import donation_center_service.com.bloodlink.service.DonationCenterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
