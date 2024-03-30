package com.aguusz.ecommerce.services;

import com.aguusz.ecommerce.exceptions.InternalErrorException;
import com.aguusz.ecommerce.exceptions.NotFoundException;
import com.aguusz.ecommerce.models.Brand;
import com.aguusz.ecommerce.repositories.BrandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAll() throws InternalErrorException {
        try {
            return brandRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public Brand getById(Long id) throws InternalErrorException, NotFoundException {
        Optional<Brand> brand;
        try {
            brand = brandRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }

        if (brand.isEmpty()) {
            throw NotFoundException.builder().message("No se ha encontrado la marca de ID: " + id).build();
        }

        return brand.get();
    }

    public Brand create(Brand brand) throws InternalErrorException {
        try {
            return brandRepository.save(brand);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public Brand update(Brand brand) throws InternalErrorException, NotFoundException {
        Brand brandFound = getById(brand.getId());
        try {
            if (brandFound == null) {
                throw NotFoundException.builder().message("Error al actualizar una direccion que no existe.").build();
            }
            return brandRepository.save(brand);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public void delete(Long id) throws InternalErrorException, NotFoundException {
        getById(id);
        try {
            brandRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }


}
