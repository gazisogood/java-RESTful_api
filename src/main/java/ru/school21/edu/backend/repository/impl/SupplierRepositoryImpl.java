package ru.school21.edu.backend.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.school21.edu.backend.dao.SupplierDao;
import ru.school21.edu.backend.entities.Address;
import ru.school21.edu.backend.entities.Supplier;
import ru.school21.edu.backend.repository.SupplierRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplierRepositoryImpl implements SupplierRepository {
    private final SupplierDao supplierDao;

    @Autowired
    public SupplierRepositoryImpl(SupplierDao supplierDao) {
        this.supplierDao = supplierDao;
    }

    @Override
    public void save(Supplier supplier) {
        supplierDao.save(supplier);
    }

    @Override
    public void delete(Supplier supplier) {
        supplierDao.delete(supplier);
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return supplierDao.findById(id);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierDao.findAll();
    }

    @Override
    public void update(Supplier supplier, Address data) {
        supplier.setAddress(data);
        supplierDao.save(supplier);
    }
}
