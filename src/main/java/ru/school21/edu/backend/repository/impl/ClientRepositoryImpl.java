package ru.school21.edu.backend.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.school21.edu.backend.dao.ClientDao;
import ru.school21.edu.backend.entities.Address;
import ru.school21.edu.backend.entities.Client;
import ru.school21.edu.backend.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private final ClientDao clientDao;

    @Autowired
    public ClientRepositoryImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public void save(Client client) {
        clientDao.save(client);
    }

    @Override
    public void delete(Client client) {
        clientDao.delete(client);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientDao.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    public void update(Client entity, Address data) {
        entity.setAddress(data);
        clientDao.save(entity);
    }

    @Override
    public List<Client> findAll(int limit, int page) {
        Pageable pageable = PageRequest.of(page, limit);
        return clientDao.findAll(pageable).getContent();
    }

    @Override
    public List<Client> findClient(String name, String surname) {
        return clientDao.findByNameAndSurname(name, surname);
    }
}
