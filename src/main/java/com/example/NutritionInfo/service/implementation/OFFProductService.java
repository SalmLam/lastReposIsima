package com.example.NutritionInfo.service.implementation;

import com.example.NutritionInfo.dal.NutritionInfoEntity;
import com.example.NutritionInfo.dal.ProductEntity;
import com.example.NutritionInfo.dal.ProductRepository;
import com.example.NutritionInfo.domain.Nutriments;
import com.example.NutritionInfo.mapper.implementation.JsonToNutriments;
import com.example.NutritionInfo.mapper.implementation.JsonToProduct;
import com.example.NutritionInfo.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class OFFProductService implements ProductService{

    private final ProductRepository productRepository;
    private final OFFDataProvider provider;
    private final OFFNutritionService nutritionService;
    private final JsonToProduct jsonToProduct;
    private final JsonToNutriments jsonToNutriments;

    @Override
    public JsonNode getProduct(String id) throws IOException {
        ProductEntity productEntity;
        if (productRepository.existsByBarCode(id)) {
            productEntity = productRepository.findByBarCode(id);
        }
        else {
            JsonNode node = provider.getProductNode(id);
            productEntity = jsonToProduct.getProductEntity(node);
            Nutriments nutriments = jsonToNutriments.getNutriments(node);
            int score = nutritionService.getScore(nutriments);
            productEntity.setNutritionScore(score);
            NutritionInfoEntity nutritionInfoEntity = nutritionService.getNutritionInfoEntity(score);
            productEntity.setClasse(nutritionInfoEntity.getClasse());
            productEntity.setColor(nutritionInfoEntity.getColor());
            productRepository.save(productEntity);
        }
        return provider.getProductJson(productEntity);
    }

    @Override
    public JsonNode searchListProduct(String name) throws IOException {
        JsonNode listNode = provider.getProductListNode(name);
        ArrayList<ProductEntity>  productEntities = jsonToProduct.getProductEntityList(listNode);
        ArrayList<JsonNode> jsonNodes = jsonToProduct.getProductNodesArray(listNode);

        for (int i = 0; i < jsonNodes.size(); i++) {
            String code = productEntities.get(i).getBarCode();
            if (productRepository.existsByBarCode(code)){
                productEntities.set(i, productRepository.findByBarCode(code));
            }else {
                Nutriments nutriments = jsonToNutriments.getNutriments(jsonNodes.get(i));
                int score = nutritionService.getScore(nutriments);
                productEntities.get(i).setNutritionScore(score);
                NutritionInfoEntity nutritionInfoEntity = nutritionService.getNutritionInfoEntity(score);
                productEntities.get(i).setColor("yellow");
                productRepository.save(productEntities.get(i));
            }

        }

        return provider.getProductListJson(productEntities);
    }

    @Override
    public JsonNode getProductDetails(String code) throws IOException {
        ProductEntity productEntity = null;
        if (productRepository.existsByBarCode(code)) {
            productEntity = productRepository.findByBarCode(code);
        }
        return provider.getProductJson(productEntity);
    }

    @Override
    public JsonNode addProduct(String code) throws IOException {
        ProductEntity productEntity = null;
        if (productRepository.existsByBarCode(code)) {
            productRepository.updateProduct(false, code);
            productEntity = productRepository.findByBarCode(code);
        }
        return provider.getProductJson(productEntity);
    }

    @Override
    public JsonNode deleteProduct(String code) throws IOException {
        ProductEntity productEntity = null;
        if (productRepository.existsByBarCode(code)) {
            productRepository.updateProduct(true, code);
            productEntity = productRepository.findByBarCode(code);
        }
        return provider.getProductJson(productEntity);
    }

    @Override
    public JsonNode getCart() throws IOException {
        ArrayList<ProductEntity> productEntities = new ArrayList<>();
        productEntities = productRepository.getCart(false);
        return provider.getProductListJson(productEntities);
    }

}
