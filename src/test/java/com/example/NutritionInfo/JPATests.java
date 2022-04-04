package com.example.NutritionInfo;

import com.example.NutritionInfo.dal.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class JPATests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private NutritionInfoRepository nutritionInfoRepository;
    @Autowired
    private RuleRepository ruleRepository;

    @Test
    void ProductRepository_findByBarCode_ProductFound() {
        ProductEntity productEntity =
                productRepository
                        .findByBarCode("code1");

        assertEquals("name1", productEntity.getName());
        assertEquals(9, productEntity.getNutritionScore());
        assertEquals("color1", productEntity.getColor());
        assertEquals("classe1", productEntity.getClasse());
    }

    @Test
    void ProductRepository_findByBarCode_ProductNotFound() {
        ProductEntity productEntity =
                productRepository
                        .findByBarCode("code2");

        assertEquals(null, productEntity);
    }

    @Test
    void ProductRepository_existsByBarCode_ProductExist() {
        boolean exist =
                productRepository
                        .existsByBarCode("code1");

        assertEquals(true, exist);
    }

    @Test
    void ProductRepository_existsByBarCode_ProductDoesNotExist() {
        boolean exist =
                productRepository
                        .existsByBarCode("code2");

        assertEquals(false, exist);
    }

    @Test
    void ProductRepository_save_CheckValues() {

        ProductEntity productEntity = new ProductEntity();
        productEntity.setClasse("Mangeable");
        productEntity.setColor("orange");
        productEntity.setNutritionScore(12);

        productRepository.save(productEntity);

        List<ProductEntity> products =  productRepository.findAll();

        // la liste doit contenir 2 elements, le premier est ajoute par data.sql, et le deuxieme par cette methode
        assertEquals(2,products.size());

        assertEquals("orange", products.get(0).getColor());
        assertEquals("Mangeable", products.get(0).getClasse());
        assertEquals(12,  products.get(0).getNutritionScore());
    }

    @Test
    void NutritionInfoRepository_getInfoByScore_ScoreIncluded()
    {
        NutritionInfoEntity nutritionInfoEntity = new NutritionInfoEntity();

        nutritionInfoEntity = nutritionInfoRepository.getInfoByScore(20);

        assertEquals("Degueu", nutritionInfoEntity.getClasse());
        assertEquals("green", nutritionInfoEntity.getColor());
        assertEquals(19, nutritionInfoEntity.getLower_bound());
        assertEquals(40, nutritionInfoEntity.getUpper_bound());
    }

    @Test
    void NutritionInfoRepository_getInfoByScore_ScoreIsNotIncluded()
    {
        NutritionInfoEntity nutritionInfoEntity = new NutritionInfoEntity();

        nutritionInfoEntity = nutritionInfoRepository.getInfoByScore(-11);

        assertEquals(null, nutritionInfoEntity);
    }

    @Test
    void RuleRepository_findPointsByName_NameExist()
    {
        int points = ruleRepository.findPointsByName("energy_100g", 1500);
        assertEquals(4, points);
    }

    @Test
    void RuleRepository_findPointsByName_NameDoesNotExist()
    {
        assertThrows(NullPointerException.class, () -> {
            int points = ruleRepository.findPointsByName("energy", 20);
        });
    }

    @Test
    void RuleRepository_findPointsByName_BoundIsNegative()
    {
        assertThrows(NullPointerException.class, () -> {
            int points = ruleRepository.findPointsByName("energy_100g", -1420);
        });
    }
}

