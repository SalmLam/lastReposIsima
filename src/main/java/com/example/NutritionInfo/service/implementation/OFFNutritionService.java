package com.example.NutritionInfo.service.implementation;


import com.example.NutritionInfo.dal.NutritionInfoEntity;
import com.example.NutritionInfo.dal.NutritionInfoRepository;
import com.example.NutritionInfo.dal.RuleRepository;
import com.example.NutritionInfo.domain.Nutriments;
import com.example.NutritionInfo.service.NutritionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OFFNutritionService implements NutritionService {

    private final RuleRepository ruleRepository;
    private final NutritionInfoRepository nutritionInfoRepository;

    @Override
    public int getNegativeComponent(Nutriments nutriments) {
        return ruleRepository.findPointsByName("energy_100g", nutriments.getEnergy())
                + ruleRepository.findPointsByName("saturated-fat_100g", nutriments.getSaturated_fat())
                + ruleRepository.findPointsByName("sugars_100g", nutriments.getSugars())
                + ruleRepository.findPointsByName("salt_100g", nutriments.getSalt());
    }

    @Override
    public int getPositiveComponent(Nutriments nutriments) {
        return ruleRepository.findPointsByName("fiber_100g", nutriments.getFiber())
                + ruleRepository.findPointsByName("proteins_100g", nutriments.getProteins());
    }

    @Override
    public int getScore(Nutriments nutriments) {
        return getNegativeComponent(nutriments) - getPositiveComponent(nutriments);
    }

    @Override
    public NutritionInfoEntity getNutritionInfoEntity(int score) {
        NutritionInfoEntity nut = nutritionInfoRepository.getInfoByScore(score);
        return nut;
    }

}
