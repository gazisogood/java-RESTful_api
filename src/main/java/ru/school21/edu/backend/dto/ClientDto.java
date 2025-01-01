package ru.school21.edu.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    @NotBlank
    @JsonProperty("client_name")
    private String name;

    @NotBlank
    @JsonProperty("client_surname")
    private String surname;

    @NotNull
    @JsonProperty("birthday")
    private LocalDate birthday;

    @NotNull
    @JsonProperty("registration_date")
    private LocalDate registrationDate;

    @NotNull
    @Valid
    @JsonProperty("address_id")
    private AddressDto address;
}
