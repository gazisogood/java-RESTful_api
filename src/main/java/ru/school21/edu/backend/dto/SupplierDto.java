package ru.school21.edu.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDto {
    private Long id;

    @NotBlank
    @JsonProperty("name")
    @Size(min = 2, max = 255, message = "Supplier name should be less than 255 characters")
    private String name;

    @Valid
    @NotNull
    @JsonProperty("address_id")
    private AddressDto address;

    @NotBlank
    @JsonProperty("phone_number")
    @Pattern(regexp = "^\\+7\\d{10}$", message = "Phone number must start with +7 and be 11 characters long")
    private String phoneNumber;
}
