package com.aguusz.ecommerce.repositories;

import com.aguusz.ecommerce.models.SubBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubBrandRepository extends JpaRepository<SubBrand, Long> {
}
