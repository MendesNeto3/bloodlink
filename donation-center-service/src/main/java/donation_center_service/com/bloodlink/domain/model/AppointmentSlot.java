package donation_center_service.com.bloodlink.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "appointment")
@RequiredArgsConstructor
@Data
public class AppointmentSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "center_id")
    private UUID centerId;

    @Column(name = "date_hour", nullable = false)
    private LocalDate dateHour;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "capacidadeTotal", nullable = false)
    private String capacityTotal;
}
