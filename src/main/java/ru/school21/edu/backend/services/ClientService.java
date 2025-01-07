package ru.school21.edu.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.school21.edu.backend.dto.AddressDto;
import ru.school21.edu.backend.dto.ClientDto;
import ru.school21.edu.backend.dto.mappers.ClientMapper;
import ru.school21.edu.backend.entities.Address;
import ru.school21.edu.backend.entities.Client;
import ru.school21.edu.backend.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientService {
    private final ClientRepository clientRepository;
    private final AddressService addressService;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, AddressService addressService, ClientMapper clientMapping) {
        this.clientRepository = clientRepository;
        this.addressService = addressService;
        this.clientMapper = clientMapping;
    }

    public ResponseEntity<List<ClientDto>> findAll() {
        return new ResponseEntity<>(
                clientRepository.findAll().stream()
                        .map(clientMapper::toDto)
                        .toList(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<List<ClientDto>> findAll(int limit, int page) {
        Pageable pageable = PageRequest.of(page / limit, limit);
        return new ResponseEntity<>(clientRepository.findAll(pageable).getContent().stream()
                .map(clientMapper::toDto)
                .toList(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<List<ClientDto>> findByNameAndSurname(String name, String surname) {
        return new ResponseEntity<>(clientRepository.findByNameAndSurname(name, surname).stream()
                .map(clientMapper::toDto)
                .toList(),
                HttpStatus.OK
        );
    }

    @Transactional
    public ResponseEntity<String> save(ClientDto clientDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Client not valid", HttpStatus.BAD_REQUEST);
        }
        Client client = clientMapper.toEntity(clientDto);
        clientRepository.save(client);
        return new ResponseEntity<>("Client saved", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> update(Long id, AddressDto addressDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Address not valid", HttpStatus.BAD_REQUEST);
        }
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>("Client with id: " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        Client client = optionalClient.get();
        Address existingAddress = client.getAddress();
        if (existingAddress == null) {
            client.setAddress(addressService.createNewAddress(addressDto));
        } else {
            client.setAddress(addressService.updateExistingAddress(existingAddress, addressDto));
        }
        clientRepository.save(client);
        return new ResponseEntity<>("Address updated. Client id: " + id, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> delete(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            return new ResponseEntity<>("Not found. Client id: " + id, HttpStatus.NOT_FOUND);
        }
        clientRepository.deleteById(id);
        return new ResponseEntity<>("Client id: " + id + " deleted", HttpStatus.OK);
    }
}
