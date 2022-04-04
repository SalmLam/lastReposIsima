package com.example.NutritionInfo.mapper;

import com.example.NutritionInfo.domain.Nutriments;
import com.fasterxml.jackson.databind.JsonNode;

public interface NutrimentsMapper {
    public  Nutriments getNutriments(JsonNode node);
}
