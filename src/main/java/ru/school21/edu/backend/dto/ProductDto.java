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

    @NotNull
    @JsonProperty("price")
    @Min(value = 0, message = "Price should not be negative")
    private Double price;

    @NotNull
    @JsonProperty("available_stock")
    @Min(value = 0, message = "Available should not be negative")
    @Max(value = Long.MAX_VALUE)
    private Long availableStock;

    @JsonProperty("last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastUpdateDate;

    @Valid
    @NotNull
    @JsonProperty("supplier_id")
    private SupplierDto supplier;

    @Valid
    @JsonProperty("image_id")
    private ImagesDto image;
}
