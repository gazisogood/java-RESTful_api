package ru.school21.edu.backend.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.school21.edu.backend.dto.ImagesDto;
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
    public ResponseEntity<String> save(ImagesDto imagesDto, Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>("Not found. Product id: " + productId, HttpStatus.NOT_FOUND);
        }
        if (imagesDto.getImage() == null || imagesDto.getImage().length == 0) {
            return new ResponseEntity<>("Image data cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        Product product = optionalProduct.get();
        Images image = Images.builder()
                .image(imagesDto.getImage())
                .build();

        imagesRepository.save(image);
        product.setImage(image);
        productRepository.save(product);
        return new ResponseEntity<>("Image saved. Product id: " + product, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> findById(UUID uuid) {
        Optional<Images> image = imagesRepository.findById(uuid);
        if (image.isEmpty()) {
            throw new EntityNotFoundException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(uuid + ".png")
                .build());

        return new ResponseEntity<>(image.get().getImage(), headers, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> findImageByProductId(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("product_" + id + ".png")
                .build());

        return new ResponseEntity<>(product.get().getImage().getImage(), headers, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> update(UUID uuid, ImagesDto imagesDto) {
        Optional<Images> optionalImages = imagesRepository.findById(uuid);
        if (optionalImages.isEmpty()) {
            return new ResponseEntity<>("Not found. Image id: " + uuid, HttpStatus.NOT_FOUND);

        }
        if (imagesDto.getImage() == null || imagesDto.getImage().length == 0) {
            return new ResponseEntity<>("Image data cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        Images newImage = Images.builder()
                .image(imagesDto.getImage())
                .build();
        Images image = optionalImages.get();
        image.setImage(newImage.getImage());
        imagesRepository.save(image);
        return new ResponseEntity<>("Updated successful. Image id: " + uuid, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> delete(UUID uuid) {
        Optional<Images> image = imagesRepository.findById(uuid);
        if (image.isEmpty()) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
        imagesRepository.deleteById(uuid);
        return new ResponseEntity<>("Successful deleted", HttpStatus.OK);
    }
}
