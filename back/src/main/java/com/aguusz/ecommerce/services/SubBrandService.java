package com.aguusz.ecommerce.services;

import com.aguusz.ecommerce.exceptions.InternalErrorException;
import com.aguusz.ecommerce.exceptions.NotFoundException;
import com.aguusz.ecommerce.models.SubBrand;
import com.aguusz.ecommerce.repositories.SubBrandRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SubBrandService {
    @Autowired
    private SubBrandRepository subBrandRepository;

    public List<SubBrand> getAll() throws InternalErrorException {
        try {
            return subBrandRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public SubBrand getById(Long id) throws InternalErrorException, NotFoundException {
        Optional<SubBrand> subBrand;

        try {
            subBrand = subBrandRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }

        if (subBrand.isEmpty()) {
            throw NotFoundException.builder().message("No se ha encontrado la submarca con ID: " + id).build();
        }

        return subBrand.get();
    }

    public SubBrand create(SubBrand subBrand) throws InternalErrorException {
        try {
            return subBrandRepository.save(subBrand);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public SubBrand update(SubBrand subBrand) throws InternalErrorException, NotFoundException {
        SubBrand subBrandFound;

        try {
            subBrandFound = getById(subBrand.getId());
            if (subBrandFound == null) {
                throw NotFoundException.builder().message("Error al actualizar una submarca que no existe.").build();
            }
            return subBrandRepository.save(subBrand);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public void delete(Long id) throws InternalErrorException {
        try {
            subBrandRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }
}
