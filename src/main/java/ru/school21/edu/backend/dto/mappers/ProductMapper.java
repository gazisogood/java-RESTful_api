package ru.school21.edu.backend.dto.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.school21.edu.backend.dto.ProductDto;
import ru.school21.edu.backend.entities.Product;

@Service
public class ProductMapper {
    private final SupplierMapper supplierMapper;
    private final ImagesMapper imagesMapper;

    @Autowired
    public ProductMapper(SupplierMapper supplierMapper, ImagesMapper imagesMapper) {
        this.supplierMapper = supplierMapper;
        this.imagesMapper = imagesMapper;
    }

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .availableStock(product.getAvailableStock())
                .lastUpdateDate(product.getLastUpdateDate())
                .supplier(product.getSupplier() != null && product.getSupplier().getId() != null
                        ? supplierMapper.toDto(product.getSupplier())
                        : null)
                .image(product.getImage() != null && product.getImage().getId() != null
                        ? imagesMapper.toDto(product.getImage())
                        : null)
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
