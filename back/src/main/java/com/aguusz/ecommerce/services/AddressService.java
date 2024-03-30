package com.aguusz.ecommerce.services;

import com.aguusz.ecommerce.exceptions.InternalErrorException;
import com.aguusz.ecommerce.exceptions.InvalidRequestException;
import com.aguusz.ecommerce.exceptions.NotFoundException;
import com.aguusz.ecommerce.models.Address;
import com.aguusz.ecommerce.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAll() throws InternalErrorException {
        try {
            return addressRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public Address getById(Long id) throws InternalErrorException, NotFoundException {
        Optional<Address> address;

        try {
            address = addressRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }

        if (address.isEmpty()) {
            throw NotFoundException.builder().message("No se ha encontrado la dirección de ID: " + id).build();
        }

        return address.get();
    }

    public List<Address> findByUserId(Long userId) throws InternalErrorException {
        try {
            return addressRepository.findAllByUserId(userId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public List<Address> findByZipcode(String zipcode) throws InternalErrorException {
        try {
            return addressRepository.findAllByZipcode(zipcode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public List<Address> findByCityId(Long cityId) throws InternalErrorException {
        try {
            return addressRepository.findAllByCity_Id(cityId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public Address create(Address address) throws InternalErrorException, InvalidRequestException {
        // Le agrego la fecha actual como la fecha de creacion
        address.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        if (address.getAddress() == null || address.getAddress().isEmpty()) {
            throw InvalidRequestException.builder().message("La dirección no puede estar vacía.").build();
        }

        try {
            return addressRepository.save(address);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public Address update(Address address) throws InternalErrorException, NotFoundException {
        Address addressFound;

        try {
            addressFound = getById(address.getId());
            if (addressFound == null) {
                throw NotFoundException.builder().message("Error al actualizar una direccion que no existe.").build();
            }
            return addressRepository.save(address);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public void delete(Long id) throws InternalErrorException {
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }
}
