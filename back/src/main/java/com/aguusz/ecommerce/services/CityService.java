package com.aguusz.ecommerce.services;

import com.aguusz.ecommerce.exceptions.InternalErrorException;
import com.aguusz.ecommerce.exceptions.NotFoundException;
import com.aguusz.ecommerce.models.City;
import com.aguusz.ecommerce.repositories.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> getAll() throws InternalErrorException {
        try {
            return cityRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public City getById(Long id) throws InternalErrorException, NotFoundException {
        Optional<City> city;

        try {
            city = cityRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }

        if (city.isEmpty()) {
            throw NotFoundException.builder().message("No se ha encontrado la ciudad con ID: " + id).build();
        }

        return city.get();
    }

    public City create(City city) throws InternalErrorException {
        try {
            return cityRepository.save(city);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public City update(City city) throws InternalErrorException, NotFoundException {
        City cityFound;

        try {
            cityFound = getById(city.getId());
            if (cityFound == null) {
                throw NotFoundException.builder().message("Error al actualizar una ciudad que no existe.").build();
            }
            return cityRepository.save(city);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public void delete(Long id) throws InternalErrorException {
        try {
            cityRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }
}
