package ru.school21.edu.backend.dto.mappers;

import org.springframework.stereotype.Service;
import ru.school21.edu.backend.dto.AddressDto;
import ru.school21.edu.backend.entities.Address;

@Service
public class AddressMapper {

    public AddressDto toDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .country(address.getCountry())
                .city(address.getCity())
                .street(address.getStreet())
                .build();
    }

    public Address toEntity(AddressDto addressDto) {
        return Address.builder()
                .country(addressDto.getCountry())
                .city(addressDto.getCity())
                .street(addressDto.getStreet())
                .build();
    }
}
