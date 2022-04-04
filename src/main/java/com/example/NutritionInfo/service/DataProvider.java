package com.example.NutritionInfo.service;

import com.example.NutritionInfo.dal.ProductEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;

public interface DataProvider {

    JsonNode getProductNode (String id) throws IOException;
    JsonNode getProductListNode (String name) throws IOException;
    JsonNode getProductJson (ProductEntity productEntity) throws JsonProcessingException;
    public ArrayNode getProductListJson(ArrayList<ProductEntity> productEntities) throws JsonProcessingException;
}
