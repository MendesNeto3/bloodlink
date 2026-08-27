package donation_center_service.com.bloodlink.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCenterRequest(
        @NotBlank(message = "O nome não pode ser nulo ou vazio")
        String name,

        @NotBlank(message = "O endereço não pode ser nulo ou vazio")
        String address,

        @NotBlank(message = "A cidade não pode ser nula ou vazia")
        String city
) {}
