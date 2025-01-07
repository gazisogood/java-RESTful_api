package ru.school21.edu.backend.dto.mappers;

import org.springframework.stereotype.Service;
import ru.school21.edu.backend.dto.ImagesDto;
import ru.school21.edu.backend.entities.Images;

@Service
public class ImagesMapper {
    public ImagesDto toDto(Images images) {
        return ImagesDto.builder()
                .id(images.getId())
                .image(images.getImage())
                .build();
    }

    public Images toEntity(byte[] imageDto) {
        return Images.builder()
                .image(imageDto)
                .build();
    }
}
