package com.example.NutritionInfo.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    ProductEntity findByBarCode (String code);
    boolean existsByBarCode (String code);
    ProductEntity save (ProductEntity product);
    @Modifying
    @Transactional
    @Query(value = "UPDATE product p set out=?1 where p.bar_code = ?2", nativeQuery = true)
    void updateProduct(Boolean out, String code);
    @Query(value = "Select * from product p where p.out = ?1", nativeQuery = true)
    ArrayList<ProductEntity> getCart(Boolean out);
}