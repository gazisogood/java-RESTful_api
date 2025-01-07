package ru.school21.edu.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;

    @NotBlank
    @JsonProperty("country")
    @Size(min = 2, max = 100, message = "Country should be less than 100 characters")
    private String country;

    @NotBlank
    @JsonProperty("city")
    @Size(min = 2, max = 100, message = "City should be less than 100 characters")
    private String city;

    @NotBlank
    @JsonProperty("street")
    @Size(min = 2, max = 100, message = "Street should be less than 255 characters")
    private String street;
}
