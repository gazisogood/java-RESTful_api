package ru.school21.edu.backend.dto.mappers;

import org.springframework.stereotype.Service;
import ru.school21.edu.backend.dto.ClientDto;
import ru.school21.edu.backend.entities.Client;

import java.time.LocalDate;

@Service
public class ClientMapper {
    private final AddressMapper addressMapper = new AddressMapper();

    public ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .surname(client.getSurname())
                .birthday(client.getBirthday())
                .registrationDate(client.getRegistrationDate())
                .address(addressMapper.toDto(client.getAddress()))
                .build();
    }

    public Client toEntity(ClientDto clientDto) {
        return Client.builder()
                .name(clientDto.getName())
                .surname(clientDto.getSurname())
                .birthday(clientDto.getBirthday())
                .registrationDate(LocalDate.now())
                .address(addressMapper.toEntity(clientDto.getAddress()))
                .build();
    }
}
