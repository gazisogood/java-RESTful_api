package ru.school21.edu.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.school21.edu.backend.dto.AddressDto;
import ru.school21.edu.backend.dto.ClientDto;
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

    @Autowired
    public ClientService(ClientRepository clientRepository, AddressService addressService) {
        this.clientRepository = clientRepository;
        this.addressService = addressService;
    }

    public ResponseEntity<List<Client>> findAll() {
        return new ResponseEntity<>(clientRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<List<Client>> findAll(int limit, int offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        return new ResponseEntity<>(clientRepository.findAll(pageable).getContent(), HttpStatus.OK);
    }

    public ResponseEntity<List<Client>> findByNameAndSurname(String name, String surname) {
        return new ResponseEntity<>(clientRepository.findByNameAndSurname(name, surname), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> save(ClientDto clientDto) {
        if (clientDto.getAddress() == null) {
            return new ResponseEntity<>("Address cannot be null", HttpStatus.BAD_REQUEST);
        }
        Address address = addressService.createNewAddress(clientDto);

        Client client = Client.builder()
                .name(clientDto.getName())
                .surname(clientDto.getSurname())
                .birthday(clientDto.getBirthday())
                .registrationDate(clientDto.getRegistrationDate())
                .address(address)
                .build();
        clientRepository.save(client);
        return new ResponseEntity<>("Client saved", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> update(Long id, AddressDto addressDto) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            return new ResponseEntity<>("Client with id: " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        Client client = optionalClient.get();
        Address existingAddress = client.getAddress();

        if (existingAddress == null) {
            Address newAddress = addressService.createNewAddress(addressDto);
            client.setAddress(newAddress);
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
