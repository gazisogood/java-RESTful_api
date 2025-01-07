package ru.school21.edu.backend.repository;

import ru.school21.edu.backend.entities.Address;
import ru.school21.edu.backend.entities.Client;

import java.util.List;

public interface ClientRepository extends Repository<Client, Address, Long> {
    List<Client> findClient(String name, String surname);

    List<Client> findAll(int limit, int page);
}
