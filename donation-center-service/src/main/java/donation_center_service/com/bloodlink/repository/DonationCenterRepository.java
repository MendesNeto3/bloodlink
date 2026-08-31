package donation_center_service.com.bloodlink.repository;

import donation_center_service.com.bloodlink.domain.model.DonationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DonationCenterRepository extends JpaRepository<DonationCenter, UUID> {
    List<DonationCenter> findByIdAndDate(UUID centerId, LocalDate date);

    @Query("""
        SELECT c FROM DonationCenter c
        WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:city IS NULL OR LOWER(c.city) = LOWER(:city))
        """)
    List<DonationCenter> search(@Param("name") String name, @Param("city") String city);
}
