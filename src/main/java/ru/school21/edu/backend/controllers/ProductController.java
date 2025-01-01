package ru.school21.edu.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.school21.edu.backend.dto.ProductDto;
import ru.school21.edu.backend.entities.Product;
import ru.school21.edu.backend.services.ProductService;

import java.util.List;

@RestController
@Tag(name = "Product_methods")
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    @Operation(summary = "Create new product",
            description = "Method takes as request body JSON with ProductDto(include SupplierDto, ImagesDto)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> createNewProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.save(productDto);
    }

    @PatchMapping("{id}")
    @Operation(summary = "Decrease available in stock",
            description = "Method takes product id and decrease value")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product edited"),
            @ApiResponse(responseCode = "400", description = "Decrease value incorrect"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> editProductById(@PathVariable Long id, Long decreaseAvailableProduct) {
        return productService.update(id, decreaseAvailableProduct);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find product by id",
            description = "Method returns entity, or not found response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product find successful"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping()
    @Operation(summary = "Find all products",
            description = "Method returns list of all products, if not found empty list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Product>> findAllProducts() {
        return productService.findAll();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by id",
            description = "Method delete entity by id. If not exists, returns not found response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product find successful"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        return productService.delete(id);
    }
}
