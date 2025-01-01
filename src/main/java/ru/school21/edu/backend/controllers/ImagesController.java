package ru.school21.edu.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.school21.edu.backend.dto.ImagesDto;
import ru.school21.edu.backend.services.ImagesService;

import java.util.UUID;

@RestController
@Tag(name = "Images_methods")
@RequestMapping("/images")
public class ImagesController {
    private final ImagesService imagesService;

    @Autowired
    public ImagesController(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @PostMapping("/products/{id}")
    @Operation(summary = "Add new image in product",
            description = "Method takes as request bode ImageDto and product id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image saved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> saveImage(@RequestBody ImagesDto imagesDto, @PathVariable Long id) {
        return imagesService.save(imagesDto, id);
    }

    @PatchMapping("/{uuid}")
    @Operation(summary = "Update image by uuid",
            description = "Method takes as request body ImagesDto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image updated"),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> updateImageById(@PathVariable UUID uuid, @Valid @RequestBody ImagesDto imagesDto) {
        return imagesService.update(uuid, imagesDto);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Find image by uuid",
            description = "Method takes as path variable image uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image found"),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<byte[]> findImageByUuid(@PathVariable UUID uuid) {
        return imagesService.findById(uuid);
    }

    @GetMapping("/products/{id}")
    @Operation(summary = "Find image by product id",
            description = "Method takes as path variable product id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image found"),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<byte[]> findImageByProductId(@PathVariable Long id) {
        return imagesService.findImageByProductId(id);
    }

    @DeleteMapping("/id")
    @Operation(summary = "Delete image by uuid",
            description = "Method takes as path variable image uuid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image deleted"),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        return imagesService.delete(id);
    }
}
