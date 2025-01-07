package ru.school21.edu.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.school21.edu.backend.entities.Supplier;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Long> {
}
