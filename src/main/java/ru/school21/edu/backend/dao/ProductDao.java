package ru.school21.edu.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.school21.edu.backend.entities.Images;
import ru.school21.edu.backend.entities.Product;
import ru.school21.edu.backend.entities.Supplier;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    Product findBySupplier(Supplier supplier);

    Product findByImage(Images images);
}
