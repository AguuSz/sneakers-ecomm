package com.aguusz.ecommerce.repositories;

import com.aguusz.ecommerce.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByUserId(Long userId);

    List<Address> findAllByZipcode(String zipcode);

    List<Address> findAllByCity_Id(Long cityId);
}
