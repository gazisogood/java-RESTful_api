package ru.school21.edu.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 2, max = 255, message = "Product name should be less than 100 characters")
    private String name;

    @NotBlank
    @JsonProperty("category")
    @Size(min = 2, max = 255, message = "Product category should be less than 100 characters")
    private String category;

    @NotBlank
    @JsonProperty("price")
    @Min(value = 0, message = "Price should not be negative")
    private Double price;

    @NotBlank
    @JsonProperty("available_stock")
    @Min(value = 0, message = "Available should not be negative")
    private Long availableStock;

    @NotBlank
    @JsonProperty("last_update_date")
    private LocalDate lastUpdateDate;

    @NotBlank
    @JsonProperty("supplier_id")
    private SupplierDto supplier;

    @JsonProperty("image_id")
    private Images image;
}
