package com.example.NutritionInfo.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public interface ProductService {
    JsonNode getProduct(String id) throws IOException;
    JsonNode searchListProduct(String name) throws  IOException;
    JsonNode getProductDetails(String code) throws  IOException;
    JsonNode addProduct(String code) throws  IOException;
    JsonNode deleteProduct(String code) throws  IOException;
    JsonNode getCart() throws IOException;
}
