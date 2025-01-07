package ru.school21.edu.backend.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.school21.edu.backend.dao.ImagesDao;
import ru.school21.edu.backend.entities.Images;
import ru.school21.edu.backend.repository.ImagesRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ImagesRepositoryImpl implements ImagesRepository {
    private final ImagesDao imagesDao;

    @Autowired
    public ImagesRepositoryImpl(ImagesDao imagesDao) {
        this.imagesDao = imagesDao;
    }

    @Override
    public void save(Images image) {
        imagesDao.save(image);
    }

    @Override
    public void delete(Images image) {
        imagesDao.delete(image);
    }

    @Override
    public Optional<Images> findById(UUID uuid) {
        return imagesDao.findById(uuid);
    }

    @Override
    public List<Images> findAll() {
        return imagesDao.findAll();
    }

    @Override
    public void update(Images image, byte[] newImage) {
        image.setImage(newImage);
        imagesDao.save(image);
    }
}
