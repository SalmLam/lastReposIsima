package com.example.NutritionInfo.mapper.implementation;

import com.example.NutritionInfo.dal.ProductEntity;
import com.example.NutritionInfo.domain.Nutriments;
import com.example.NutritionInfo.mapper.NutrimentsMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JsonToNutriments implements NutrimentsMapper {

    public  int getNutrimentScore(String nutriment_name, JsonNode nutriments, int min) {
        if (nutriments.get(nutriment_name) != null) return nutriments.get(nutriment_name).asInt();
        else return min;
    }

    @Override
    public Nutriments getNutriments(JsonNode product) {

        //JsonNode product = node.get("product");
        JsonNode nutriments = product.get("nutriments");
        return new Nutriments(
                this.getNutrimentScore("energy_100g", nutriments, 335),
                this.getNutrimentScore("saturated-fat_100g", nutriments, 1),
                this.getNutrimentScore("sugars_100g", nutriments, 5),
                this.getNutrimentScore("salt_100g-fat_100g", nutriments, 90),
                this.getNutrimentScore("fiber_100g", nutriments, 1),
                this.getNutrimentScore("proteins_100g-fat_100g", nutriments, 2));

    }

}
