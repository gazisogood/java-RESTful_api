package ru.school21.edu.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.school21.edu.backend.entities.Client;

import java.util.List;

@Repository
public interface ClientDao extends JpaRepository<Client, Long> {
    List<Client> findByNameAndSurname(String name, String surname);
}
