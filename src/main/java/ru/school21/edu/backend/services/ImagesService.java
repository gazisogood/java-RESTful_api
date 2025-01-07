package ru.school21.edu.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.school21.edu.backend.entities.Images;
import ru.school21.edu.backend.entities.Product;
import ru.school21.edu.backend.repositories.ImagesRepository;
import ru.school21.edu.backend.repositories.ProductRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ImagesService {
    private final ImagesRepository imagesRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ImagesService(ImagesRepository imagesRepository, ProductRepository productRepository) {
        this.imagesRepository = imagesRepository;
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
        Images image = Images.builder()
                .image(imageData)
                .build();
        imagesRepository.save(image);
        product.setImage(image);
        productRepository.save(product);
        return new ResponseEntity<>("Image saved. Product id: ", HttpStatus.OK);
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

    public ResponseEntity<Object> findImageByProductId(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return new ResponseEntity<>("Not found. Product id : " + id, HttpStatus.OK);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("product_" + id + ".png")
                .build());
        return new ResponseEntity<>(product.get().getImage().getImage(), headers, HttpStatus.OK);
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
        Product product = productRepository.findProductByImage(image.get());
        product.setImage(null);
        imagesRepository.deleteById(uuid);
        return new ResponseEntity<>("Successful deleted", HttpStatus.OK);
    }
}
