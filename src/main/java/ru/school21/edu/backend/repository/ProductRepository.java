package ru.school21.edu.backend.repository;

import ru.school21.edu.backend.entities.Images;
import ru.school21.edu.backend.entities.Product;
import ru.school21.edu.backend.entities.Supplier;

public interface ProductRepository extends Repository<Product, Long, Long> {
    Product findBySupplier(Supplier supplier);

    Product findByImage(Images image);
}
