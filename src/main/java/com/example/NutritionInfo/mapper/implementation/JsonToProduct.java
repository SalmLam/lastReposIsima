package com.example.NutritionInfo.mapper.implementation;

import com.example.NutritionInfo.dal.ProductEntity;
import com.example.NutritionInfo.mapper.ProductMapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@AllArgsConstructor
@Component
public class JsonToProduct implements ProductMapper {

    @Override
    public ProductEntity getProductEntity(JsonNode node){

        return new ProductEntity(
                node.get("code").asText(),
                node.get("product_name").asText());
                //node.get("ecoscore_data").get("agribalyse").get("name_en").asText());
    }

    @Override
    public ArrayList<ProductEntity> getProductEntityList(JsonNode node) {
        int count = node.get("page_count").asInt();
        int size = 10;
        if (count < size ) size = count;
        ArrayList<ProductEntity> products = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            products.add(this.getProductEntity(node.get("products").get(i)));
        }
        System.out.println(products.get(0).getName());

        return products;
    }

    @Override
    public ArrayList<JsonNode> getProductNodesArray(JsonNode node) throws IOException {
        int count = node.get("page_count").asInt();
        System.out.println("size : " + count);
        int size = 10;
        if (count < size ) size = count;
        System.out.println("size : " + size);
        ArrayList<JsonNode> nodesList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            nodesList.add(node.get("products").get(i));
        }

        return nodesList;
    }

}
