package com.example.NutritionInfo.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RuleRepository extends JpaRepository<RuleEntity, Integer> {
    @Query(value = "select max(r.points) from Rule r where r.name = ?1 and r.min_bound <= ?2", nativeQuery = true)
    Integer findPointsByName(String name, int bound);
}
