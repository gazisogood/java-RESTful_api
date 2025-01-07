package ru.school21.edu.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;

    @NotBlank
    @JsonProperty("client_name")
    @Size(min = 2, max = 20, message = "Name should be less than 20 characters")
    private String name;

    @NotBlank
    @JsonProperty("client_surname")
    @Size(min = 2, max = 50, message = "Surname should be less than 50 characters")
    private String surname;

    @NotNull
    @JsonProperty("birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @JsonProperty("registration_date")
    private LocalDate registrationDate;

    @Valid
    @NotNull
    @JsonProperty("address_id")
    private AddressDto address;
}
