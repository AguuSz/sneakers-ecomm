package com.aguusz.ecommerce.controllers;

import com.aguusz.ecommerce.exceptions.InvalidRequestException;
import com.aguusz.ecommerce.models.Size;
import com.aguusz.ecommerce.services.SizeService;
import com.aguusz.ecommerce.exceptions.InternalErrorException;
import com.aguusz.ecommerce.exceptions.NotFoundException;
import com.aguusz.ecommerce.utils.IStandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.URL_SIZES)
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @Autowired
    private IStandardResponse response;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllSizes() {
        try {
            List<Size> sizes = sizeService.getAll();
            return new ResponseEntity<>(sizes, HttpStatus.OK);
        } catch (InternalErrorException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSizeById(@PathVariable("id") Long id) {
        try {
            Size size = sizeService.getById(id);
            return new ResponseEntity<>(size, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InternalErrorException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSize(@RequestBody Size size) {
        try {
            Size createdSize = sizeService.create(size);
            return new ResponseEntity<>(createdSize, HttpStatus.CREATED);
        } catch(InvalidRequestException e) {
            return new ResponseEntity<>(response.build(HttpStatus.BAD_REQUEST, e, e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (InternalErrorException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSize(@RequestBody Size size) {
        try {
            Size updatedSize = sizeService.update(size);
            return new ResponseEntity<>(updatedSize, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(response.build(HttpStatus.NOT_FOUND, e, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InvalidRequestException e) {
            return new ResponseEntity<>(response.build(HttpStatus.BAD_REQUEST, e, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (InternalErrorException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteSize(@PathVariable("id") Long id) {
        try {
            sizeService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalErrorException e) {
            return new ResponseEntity<>(response.build(HttpStatus.INTERNAL_SERVER_ERROR, e, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
