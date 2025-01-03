package ru.school21.edu.backend.dto.mappers;

import org.springframework.stereotype.Service;
import ru.school21.edu.backend.dto.SupplierDto;
import ru.school21.edu.backend.entities.Supplier;

@Service
public class SupplierMapper {
    private final AddressMapper addressMapper = new AddressMapper();

    public SupplierDto toDto(Supplier supplier) {
        return SupplierDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .address(addressMapper.toDto(supplier.getAddress()))
                .phoneNumber(supplier.getPhoneNumber())
                .build();
    }

    public Supplier toEntity(SupplierDto supplierDto) {
        return Supplier.builder()
                .name(supplierDto.getName())
                .phoneNumber(supplierDto.getPhoneNumber())
                .address(addressMapper.toEntity(supplierDto.getAddress()))
                .build();
    }
}
