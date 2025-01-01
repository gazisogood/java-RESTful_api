package ru.school21.edu.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.school21.edu.backend.dto.ProductDto;
import ru.school21.edu.backend.entities.Address;
import ru.school21.edu.backend.entities.Images;
import ru.school21.edu.backend.entities.Product;
import ru.school21.edu.backend.entities.Supplier;
import ru.school21.edu.backend.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<List<Product>> findAll() {
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Product> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ResponseEntity<String> save(ProductDto productDto) {
        Address address = Address.builder()
                .country(productDto.getSupplier().getAddress().getCountry())
                .city(productDto.getSupplier().getAddress().getCity())
                .street(productDto.getSupplier().getAddress().getStreet())
                .build();

        Supplier supplier = Supplier.builder()
                .name(productDto.getSupplier().getName())
                .address(address)
                .build();

        Images image = Images.builder()
                .image(productDto.getImage().getImage())
                .build();

        Product product = Product.builder()
                .name(productDto.getName())
                .category(productDto.getCategory())
                .price(productDto.getPrice())
                .availableStock(productDto.getAvailableStock())
                .lastUpdateDate(productDto.getLastUpdateDate())
                .supplier(supplier)
                .image(image)
                .build();

        productRepository.save(product);
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
        product.setAvailableStock(product.getAvailableStock() - decreaseAvailableProduct);
        productRepository.save(product);
        return new ResponseEntity<>("Available in stock updated. Product id: " + id, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> delete(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>("Not found. Product id: " + id, HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
        return new ResponseEntity<>("Deleted successful. Product id: " + id, HttpStatus.OK);

    }
}
