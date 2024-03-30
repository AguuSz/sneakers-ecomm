package com.aguusz.ecommerce.services;

import com.aguusz.ecommerce.exceptions.InternalErrorException;
import com.aguusz.ecommerce.exceptions.NotFoundException;
import com.aguusz.ecommerce.models.Country;
import com.aguusz.ecommerce.repositories.CountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAll() throws InternalErrorException {
        try {
            return countryRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public Country getById(Long id) throws InternalErrorException, NotFoundException {
        Optional<Country> country;

        try {
            country = countryRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }

        if (country.isEmpty()) {
            throw NotFoundException.builder().message("No se ha encontrado el país con ID: " + id).build();
        }

        return country.get();
    }

    public Country create(Country country) throws InternalErrorException {
        try {
            return countryRepository.save(country);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public Country update(Country country) throws InternalErrorException, NotFoundException {
        Country countryFound;

        try {
            countryFound = getById(country.getId());
            if (countryFound == null) {
                throw NotFoundException.builder().message("Error al actualizar un país que no existe.").build();
            }
            return countryRepository.save(country);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public void delete(Long id) throws InternalErrorException {
        try {
            countryRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }
}