package ru.school21.edu.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.school21.edu.backend.dto.ProductDto;
import ru.school21.edu.backend.dto.mappers.ProductMapper;
import ru.school21.edu.backend.entities.Product;
import ru.school21.edu.backend.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ResponseEntity<List<ProductDto>> findAll() {
        return new ResponseEntity<>(
                productRepository.findAll().stream()
                        .map(productMapper::toDto)
                        .toList(),
                HttpStatus.OK);
    }

    public ResponseEntity<Object> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(p -> {
                    ProductDto productDto = productMapper.toDto(p);
                    return new ResponseEntity<Object>(productDto, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>("Not found. Product id: " + id, HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ResponseEntity<String> save(ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Product not valid", HttpStatus.BAD_REQUEST);
        }
        productRepository.save(productMapper.toEntity(productDto));
        return new ResponseEntity<>("Product saved", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> update(Long id, Long decreaseAvailableProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>("Not found. Product id: " + id, HttpStatus.NOT_FOUND);
        }
        Product product = optionalProduct.get();
        Long currentAvailableInStock = product.getAvailableStock();
        if (decreaseAvailableProduct > currentAvailableInStock) {
            return new ResponseEntity<>("Decrease product can't be more, than current value.", HttpStatus.BAD_REQUEST);
        }
        productRepository.update(product, Math.abs(decreaseAvailableProduct));
        return new ResponseEntity<>("Available in stock updated. Product id: " + id, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> delete(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>("Not found. Product id: " + id, HttpStatus.NOT_FOUND);
        }
        productRepository.delete(product.get());
        return new ResponseEntity<>("Deleted successful. Product id: " + id, HttpStatus.OK);
    }
}
