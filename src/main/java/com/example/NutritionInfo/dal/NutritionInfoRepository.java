package com.example.NutritionInfo.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NutritionInfoRepository extends JpaRepository<NutritionInfoEntity, Integer> {

    @Query(value = "select n.* from nutrition_info n where n.lower_bound <= ?1 and n.upper_bound > ?1", nativeQuery = true)
    NutritionInfoEntity  getInfoByScore(int score);
}
