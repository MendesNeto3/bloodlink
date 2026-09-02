package donation_center_service.com.bloodlink.repository;

import donation_center_service.com.bloodlink.domain.model.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentSlotRepository extends JpaRepository<AppointmentSlot, UUID> {
    List<AppointmentSlot> findByCenterIdAndDateHour(UUID centerId, LocalDate data);
}
