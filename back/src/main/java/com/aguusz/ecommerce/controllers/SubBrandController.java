package com.aguusz.ecommerce.controllers;

import com.aguusz.ecommerce.models.SubBrand;
import com.aguusz.ecommerce.services.SubBrandService;
import com.aguusz.ecommerce.exceptions.InternalErrorException;
import com.aguusz.ecommerce.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.URL_SUBBRANDS)
public class SubBrandController {

    @Autowired
    private SubBrandService subBrandService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SubBrand>> getAllSubBrands() {
        try {
            List<SubBrand> subBrands = subBrandService.getAll();
            return new ResponseEntity<>(subBrands, HttpStatus.OK);
        } catch (InternalErrorException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubBrand> getSubBrandById(@PathVariable("id") Long id) {
        try {
            SubBrand subBrand = subBrandService.getById(id);
            return new ResponseEntity<>(subBrand, HttpStatus.OK);
        } catch (InternalErrorException | NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubBrand> createSubBrand(@RequestBody SubBrand subBrand) {
        try {
            SubBrand createdSubBrand = subBrandService.create(subBrand);
            return new ResponseEntity<>(createdSubBrand, HttpStatus.CREATED);
        } catch (InternalErrorException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubBrand> updateSubBrand(@RequestBody SubBrand subBrand) {
        try {
            SubBrand updatedSubBrand = subBrandService.update(subBrand);
            return new ResponseEntity<>(updatedSubBrand, HttpStatus.OK);
        } catch (InternalErrorException | NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteSubBrand(@PathVariable("id") Long id) {
        try {
            subBrandService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InternalErrorException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
