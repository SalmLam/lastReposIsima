package com.example.NutritionInfo.service;

import com.example.NutritionInfo.dal.NutritionInfoEntity;
import com.example.NutritionInfo.domain.Nutriments;

public interface NutritionService {
    int getNegativeComponent(Nutriments nutriments);
    int getPositiveComponent(Nutriments nutriments);
    int getScore(Nutriments nutriments);
    NutritionInfoEntity getNutritionInfoEntity(int score);
}
