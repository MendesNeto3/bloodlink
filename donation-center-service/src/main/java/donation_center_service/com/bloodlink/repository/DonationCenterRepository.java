package donation_center_service.com.bloodlink.repository;

import donation_center_service.com.bloodlink.domain.model.DonationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DonationCenterRepository extends JpaRepository<DonationCenter, UUID> {
}
