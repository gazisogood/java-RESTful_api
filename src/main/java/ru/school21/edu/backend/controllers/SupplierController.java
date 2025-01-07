package ru.school21.edu.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.school21.edu.backend.dto.AddressDto;
import ru.school21.edu.backend.dto.SupplierDto;
import ru.school21.edu.backend.service.SupplierService;

import java.util.List;

@RestController
@Tag(name = "Supplier_methods")
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping()
    @Operation(summary = "Create new supplier",
            description = "Method takes as request body JSON with SupplierDto(include SupplierDto, AddressDto)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> createNewSupplier(@Valid @RequestBody SupplierDto supplierDto, BindingResult bindingResult) {
        return supplierService.save(supplierDto, bindingResult);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update supplier address",
            description = "Take supplier id as path variable and AddressDTO as body. " +
                    "Update supplier address with current id if exists, or save new address. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier updated"),
            @ApiResponse(responseCode = "404", description = "Supplier not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> updateSupplierAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressDto addressDto,
            BindingResult bindingResult) {
        return supplierService.update(id, addressDto, bindingResult);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find supplier by id",
            description = "Take supplier id as path variable, return supplier entity. " +
                    "If supplier not exist, return not found response.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find successful"),
            @ApiResponse(responseCode = "404", description = "Supplier not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> findSupplierById(@PathVariable Long id) {
        return supplierService.findById(id);
    }

    @GetMapping()
    @Operation(summary = "Find all suppliers",
            description = "Return list all suppliers. If not exists, return empty list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find successful"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<SupplierDto>> findAll() {
        return supplierService.findAll();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete supplier by id",
            description = "Supplier id require as path variable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete successful"),
            @ApiResponse(responseCode = "404", description = "Supplier not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return supplierService.delete(id);
    }
}
