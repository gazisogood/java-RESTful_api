package ru.school21.edu.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.school21.edu.backend.entities.Images;

import java.util.UUID;

@Repository
public interface ImagesRepository extends JpaRepository<Images, UUID> {
}
