package ru.school21.edu.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.school21.edu.backend.entities.Images;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @JsonProperty("category")
    private String category;

    @NotBlank
    @JsonProperty("price")
    private Double price;

    @NotBlank
    @JsonProperty("available_stock")
    private Long availableStock;

    @NotBlank
    @JsonProperty("last_update_date")
    private LocalDate lastUpdateDate;

    @NotBlank
    @JsonProperty("supplier_id")
    private SupplierDto supplier;

//    @NotBlank
    @JsonProperty("image_id")
    private Images image;
}
