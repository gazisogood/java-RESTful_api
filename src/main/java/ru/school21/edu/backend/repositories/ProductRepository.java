package ru.school21.edu.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.school21.edu.backend.entities.Images;
import ru.school21.edu.backend.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductByImage(Images images);
}
