package ru.school21.edu.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.school21.edu.backend.dto.AddressDto;
import ru.school21.edu.backend.dto.SupplierDto;
import ru.school21.edu.backend.dto.mappers.SupplierMapper;
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
    private final SupplierMapper supplierMapper;

    @Autowired
    public SupplierService(SupplierRepository supplierRepository,
                           AddressService addressService,
                           SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.addressService = addressService;
        this.supplierMapper = supplierMapper;
    }

    public ResponseEntity<List<SupplierDto>> findAll() {
        return new ResponseEntity<>(
                supplierRepository.findAll()
                        .stream().map(supplierMapper::toDto)
                        .toList(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> findById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.map(s -> new ResponseEntity<Object>(supplierMapper.toDto(s), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Not found. Supplier id: " + id, HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ResponseEntity<String> save(SupplierDto supplierDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Supplier not valid", HttpStatus.BAD_REQUEST);
        }
        if (supplierDto.getAddress() == null) {
            return new ResponseEntity<>("Address cannot be null", HttpStatus.BAD_REQUEST);
        }
        Supplier supplier = supplierMapper.toEntity(supplierDto);
        supplier.setAddress(addressService.createNewAddress(supplierDto));
        supplierRepository.save(supplier);
        return new ResponseEntity<>("Supplier saved", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> update(Long id, AddressDto addressDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Address not valid", HttpStatus.BAD_REQUEST);
        }
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isEmpty()) {
            return new ResponseEntity<>("Not found. Supplier id: " + id, HttpStatus.NOT_FOUND);
        }
        Supplier supplier = optionalSupplier.get();
        Address existingAddress = supplier.getAddress();

        if (existingAddress == null) {
            supplier.setAddress(addressService.createNewAddress(addressDto));
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
