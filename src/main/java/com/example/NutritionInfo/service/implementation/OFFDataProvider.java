package com.example.NutritionInfo.service.implementation;

import com.example.NutritionInfo.dal.ProductEntity;
import com.example.NutritionInfo.service.DataProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

@AllArgsConstructor
@Service
public class OFFDataProvider implements DataProvider {

    private final ObjectMapper objectMapper;

    @Override
    public JsonNode getProductNode(String code) throws IOException {
        String api_url = "https://fr.openfoodfacts.org/api/v0/produit/";
        JsonNode node =  objectMapper.readTree(new URL(api_url + code + ".json"));
        return node.get("product");
    }

    @Override
    public JsonNode getProductListNode(String name) throws IOException {
        String api_url = "https://world.openfoodfacts.org/cgi/search.pl?search_terms=";
        return objectMapper.readTree(new URL(api_url + name + "&json=1"));
    }



    @Override
    public JsonNode getProductJson(ProductEntity productEntity) {

        JsonNode productNode = objectMapper.createObjectNode();
        ObjectNode productJsonObject = (ObjectNode) productNode;
        productJsonObject.put("id", productEntity.getId());
        productJsonObject.put("barCode", productEntity.getBarCode());
        productJsonObject.put("name", productEntity.getName());
        productJsonObject.put("nutritionScore", productEntity.getNutritionScore());
        productJsonObject.put("classe", productEntity.getClasse());
        productJsonObject.put("color", productEntity.getColor());
        
        return productJsonObject;
    }

    @Override
    public ArrayNode getProductListJson(ArrayList<ProductEntity> productEntities) throws JsonProcessingException {
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for (ProductEntity productEntity : productEntities) {
            arrayNode.add(this.getProductJson(productEntity));
        }
        return arrayNode;
    }

}
