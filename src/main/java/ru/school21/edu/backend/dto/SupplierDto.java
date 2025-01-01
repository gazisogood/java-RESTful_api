package ru.school21.edu.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.school21.edu.backend.entities.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {
    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotNull
    @Valid
    @JsonProperty("address_id")
    private AddressDto address;

    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;
}
