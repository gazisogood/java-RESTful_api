package ru.school21.edu.backend.services;

import org.springframework.stereotype.Service;
import ru.school21.edu.backend.dto.AddressDto;
import ru.school21.edu.backend.dto.ClientDto;
import ru.school21.edu.backend.dto.SupplierDto;
import ru.school21.edu.backend.entities.Address;

@Service

public class AddressService {

    public Address createNewAddress(AddressDto addressDto) {
        return buildAddress(addressDto);
    }

    public Address createNewAddress(ClientDto clientDto) {
        if (clientDto.getAddress() == null) {
            throw new IllegalArgumentException("Address in ClientDto cannot be null");
        }
        return buildAddress(clientDto.getAddress());
    }

    public Address createNewAddress(SupplierDto supplierDto) {
        if (supplierDto.getAddress() == null) {
            throw new IllegalArgumentException("Address in SupplierDto cannot be null");
        }
        return buildAddress(supplierDto.getAddress());
    }

    private Address buildAddress(AddressDto addressDto) {
        return Address.builder()
                .country(addressDto.getCountry())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .build();
    }

    public Address updateExistingAddress(Address address, AddressDto addressDto){
        address.setCountry(addressDto.getCountry());
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        return address;
    }

}