package ru.school21.edu.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.school21.edu.backend.dto.AddressDto;
import ru.school21.edu.backend.dto.SupplierDto;
import ru.school21.edu.backend.entities.Address;
import ru.school21.edu.backend.entities.Supplier;
import ru.school21.edu.backend.repositories.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final AddressService addressService;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository, AddressService addressService) {
        this.supplierRepository = supplierRepository;
        this.addressService = addressService;
    }

    public ResponseEntity<List<Supplier>> findAll() {
        return new ResponseEntity<>(supplierRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Supplier> findById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ResponseEntity<String> save(SupplierDto supplierDto) {
        if (supplierDto.getAddress() == null) {
            return new ResponseEntity<>("Address cannot be null", HttpStatus.BAD_REQUEST);
        }

        Address address = addressService.createNewAddress(supplierDto);
        Supplier supplier = Supplier.builder()
                .name(supplierDto.getName())
                .address(address)
                .phoneNumber(supplierDto.getPhoneNumber())
                .build();

        supplierRepository.save(supplier);
        return new ResponseEntity<>("Supplier saved", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> update(Long id, AddressDto addressDto) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isEmpty()) {
            return new ResponseEntity<>("Not found. Supplier id: " + id, HttpStatus.NOT_FOUND);
        }
        Supplier supplier = optionalSupplier.get();
        Address existingAddress = supplier.getAddress();

        if (existingAddress == null) {
            Address newAddress = addressService.createNewAddress(addressDto);
            supplier.setAddress(newAddress);
        } else {
            supplier.setAddress(addressService.updateExistingAddress(existingAddress, addressDto));
        }
        supplierRepository.save(supplier);
        return new ResponseEntity<>("Address updated successful. Supplier id: " + id, HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<String> delete(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isEmpty()) {
            return new ResponseEntity<>("Supplier not found. Id: " + id, HttpStatus.NOT_FOUND);
        }
        supplierRepository.deleteById(id);
        return new ResponseEntity<>("Supplier id: " + id + ". Successful deleted.", HttpStatus.OK);
    }
}
