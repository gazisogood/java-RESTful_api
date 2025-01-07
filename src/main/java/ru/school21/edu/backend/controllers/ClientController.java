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
import ru.school21.edu.backend.dto.ClientDto;
import ru.school21.edu.backend.services.ClientService;

import java.util.List;

@RestController
@Tag(name = "Client_methods")
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping()
    @Operation(summary = "Add new client",
            description = "Method takes as request body JSON with ClientDto(include AddressDto)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client saved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> createNewClient(@Valid @RequestBody ClientDto clientDto, BindingResult bindingResult) {
        return clientService.save(clientDto, bindingResult);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete client by id",
            description = "Client id require as path variable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client deleted"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        return clientService.delete(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Find clients by name and surname",
            description = "Method takes name and surname as request params. " +
                    "Return list of clients, if not found returns empty list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ClientDto>> findByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        return clientService.findByNameAndSurname(name, surname);
    }

    @GetMapping()
    @Operation(summary = "Find all clients",
            description = "Returns a list of all clients or a paginated list depending on provided parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All clients found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ClientDto>> findAll(@RequestParam(required = false) Integer limit,
                                                   @RequestParam(required = false) Integer page) {
        if (limit != null && page != null)
            return clientService.findAll(limit, page);
        else
            return clientService.findAll();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update client address",
            description = "Take client id as path variable and AddressDto as body. " +
                    "Update client address with current id if exists, or save new address. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> updateClientAddress(@PathVariable("id") Long id, @Valid @RequestBody AddressDto addressDto, BindingResult bindingResult) {
        return clientService.update(id, addressDto, bindingResult);
    }
}
