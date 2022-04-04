package com.example.NutritionInfo.mapper;

import com.example.NutritionInfo.dal.ProductEntity;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;

public interface ProductMapper {

    public  ProductEntity getProductEntity(JsonNode node);
    public ArrayList<ProductEntity> getProductEntityList(JsonNode node);

    ArrayList<JsonNode> getProductNodesArray (JsonNode node) throws IOException;
}
