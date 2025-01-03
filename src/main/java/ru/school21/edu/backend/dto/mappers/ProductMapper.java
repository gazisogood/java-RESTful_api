package ru.school21.edu.backend.dto.mappers;

import org.springframework.stereotype.Service;
import ru.school21.edu.backend.dto.ProductDto;
import ru.school21.edu.backend.entities.Product;

@Service
public class ProductMapper {
    private final SupplierMapper supplierMapper = new SupplierMapper();


    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .availableStock(product.getAvailableStock())
                .lastUpdateDate(product.getLastUpdateDate())
                .supplier(supplierMapper.toDto(product.getSupplier()))
                .build();
    }

    public Product toEntity(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .category(productDto.getCategory())
                .price(productDto.getPrice())
                .availableStock(productDto.getAvailableStock())
                .lastUpdateDate(productDto.getLastUpdateDate())
                .supplier(supplierMapper.toEntity(productDto.getSupplier()))
                .build();
    }

}
