package com.aguusz.ecommerce.services;

import com.aguusz.ecommerce.models.Address;
import com.aguusz.ecommerce.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address getById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }
}
