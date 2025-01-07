package ru.school21.edu.backend.repository.impl;

import org.springframework.stereotype.Repository;
import ru.school21.edu.backend.dao.ProductDao;
import ru.school21.edu.backend.entities.Images;
import ru.school21.edu.backend.entities.Product;
import ru.school21.edu.backend.entities.Supplier;
import ru.school21.edu.backend.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductDao productDao;


    public ProductRepositoryImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product findBySupplier(Supplier supplier) {
        return productDao.findBySupplier(supplier);
    }

    public Product findByImage(Images image) {
        return productDao.findByImage(image);
    }

    public void save(Product product) {
        productDao.save(product);
    }

    public void delete(Product product) {
        productDao.delete(product);
    }

    public Optional<Product> findById(Long id) {
        return productDao.findById(id);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void update(Product product, Long decrease) {
        product.setAvailableStock(product.getAvailableStock() - decrease);
        productDao.save(product);
    }
}
