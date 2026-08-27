package donation_center_service.com.bloodlink.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "Donation", schema = "public")
public class DonationCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "nome",  nullable = false, length = 50)
    private String name;

    @Column(name = "adress", nullable = false, length = 50)
    private String adress;

    @Column(name = "city",  nullable = false, length = 50)
    private String city;
}
