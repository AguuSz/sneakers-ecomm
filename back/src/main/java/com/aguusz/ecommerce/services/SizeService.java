package com.aguusz.ecommerce.services;

import com.aguusz.ecommerce.exceptions.InternalErrorException;
import com.aguusz.ecommerce.exceptions.InvalidRequestException;
import com.aguusz.ecommerce.exceptions.NotFoundException;
import com.aguusz.ecommerce.models.Size;
import com.aguusz.ecommerce.repositories.SizeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    public List<Size> getAll() throws InternalErrorException {
        try {
            return sizeRepository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public Size getById(Long id) throws InternalErrorException, NotFoundException {
        Optional<Size> size;

        try {
            size = sizeRepository.findById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }

        if (size.isEmpty()) {
            throw NotFoundException.builder().message("No se ha encontrado el tamaño con ID: " + id).build();
        }

        return size.get();
    }

    public Size create(Size size) throws InternalErrorException, InvalidRequestException {
        size.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        if (size.getId() != null) {
            size.setId(null);
        }

        validateRequest(size);

        try {
            return sizeRepository.save(size);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public Size update(Size size) throws InternalErrorException, NotFoundException, InvalidRequestException {
        Size sizeFound;

        validateRequest(size);

        try {
            sizeFound = getById(size.getId());
            if (sizeFound == null) {
                throw NotFoundException.builder().message("Error al actualizar un tamaño que no existe.").build();
            }
            sizeFound.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return sizeRepository.save(size);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    public void delete(Long id) throws InternalErrorException {
        try {
            sizeRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw InternalErrorException.builder().ex(e).build();
        }
    }

    private void validateRequest(Size size) throws InvalidRequestException {
        if (size.getProduct() == null) {
            throw InvalidRequestException.builder().message("No se puede crear/actualizar un tamaño sin un producto asociado.").build();
        }

        if (size.getSize() < 0) {
            throw InvalidRequestException.builder().message("El tamaño no puede ser negativo.").build();
        }

        if (size.getStock() < 0) {
            throw InvalidRequestException.builder().message("El stock no puede ser negativo.").build();
        }

        if (size.getPrice() < 0) {
            throw InvalidRequestException.builder().message("El precio no puede ser negativo.").build();
        }

        if (size.getUpdatedAt() != null) {
            size.setUpdatedAt(null);
        }
    }
}
