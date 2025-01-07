package ru.school21.edu.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.school21.edu.backend.dto.mappers.ImagesMapper;
import ru.school21.edu.backend.entities.Images;
import ru.school21.edu.backend.entities.Product;
import ru.school21.edu.backend.repository.ImagesRepository;
import ru.school21.edu.backend.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ImagesService {
    private final ImagesRepository imagesRepository;
    private final ImagesMapper imagesMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ImagesService(ImagesRepository imagesRepository, ImagesMapper imagesMapper, ProductRepository productRepository) {
        this.imagesRepository = imagesRepository;
        this.imagesMapper = imagesMapper;
        this.productRepository = productRepository;
    }

    @Transactional
    public ResponseEntity<String> save(byte[] imageData, Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>("Not found. Product id: " + productId, HttpStatus.NOT_FOUND);
        }
        if (imageData == null) {
            return new ResponseEntity<>("Image should not be null", HttpStatus.BAD_REQUEST);
        }
        Product product = optionalProduct.get();
        Images image = imagesMapper.toEntity(imageData);
        imagesRepository.save(image);
        product.setImage(image);
        productRepository.save(product);
        return new ResponseEntity<>("Image saved. Product id: " + productId, HttpStatus.OK);
    }

    public ResponseEntity<Object> findById(UUID uuid) {
        Optional<Images> image = imagesRepository.findById(uuid);
        if (image.isEmpty()) {
            return new ResponseEntity<>("Not found. Image uuid: " + uuid, HttpStatus.OK);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(uuid + ".png")
                .build());
        return new ResponseEntity<>(image.get().getImage(), headers, HttpStatus.OK);
    }

    public ResponseEntity<Object> findImageByProductId(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>("Not found. Product id : " + productId, HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("product_" + productId + ".png")
                .build());
        Product product = optionalProduct.get();
        Images image = product.getImage();
        if (image == null) {
            return new ResponseEntity<>(
                    "Image not exist. Product Id: " + product.getId(),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.getImage().getImage(), headers, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> update(UUID uuid, byte[] newImage) {
        Optional<Images> optionalImages = imagesRepository.findById(uuid);
        if (optionalImages.isEmpty()) {
            return new ResponseEntity<>("Not found. Image id: " + uuid, HttpStatus.NOT_FOUND);
        }
        if (newImage == null) {
            return new ResponseEntity<>("Image should not be null", HttpStatus.BAD_REQUEST);
        }
        Images image = optionalImages.get();
        image.setImage(newImage);
        imagesRepository.save(image);
        return new ResponseEntity<>("Updated successful. Image id: " + uuid, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> delete(UUID uuid) {
        Optional<Images> image = imagesRepository.findById(uuid);
        if (image.isEmpty()) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
        Product product = productRepository.findByImage(image.get());
        if (product != null) {
            product.setImage(null);
        }
        imagesRepository.delete(image.get());
        return new ResponseEntity<>("Successful deleted", HttpStatus.OK);
    }
}
