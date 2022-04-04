package com.example.NutritionInfo;

import com.example.NutritionInfo.dal.*;
import com.example.NutritionInfo.domain.Nutriments;
import com.example.NutritionInfo.service.implementation.OFFDataProvider;
import com.example.NutritionInfo.service.implementation.OFFNutritionService;
import com.example.NutritionInfo.service.implementation.OFFProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RuleRepository ruleRepository;

    @Mock
    private NutritionInfoRepository nutritionInfoRepository;

    @InjectMocks
    private OFFDataProvider dataProvider;

    @InjectMocks
    private OFFProductService productService;

    @InjectMocks
    private OFFNutritionService nutritionService;


    @Test
    void OFFProductService_getProduct_ProductExists() throws IOException {

        ReflectionTestUtils.setField(dataProvider, "objectMapper", new ObjectMapper());
        ObjectMapper objectMapper = new ObjectMapper();

        ProductEntity productEntity = ProductEntity.builder()
                .barCode("7622210449283")
                .nutritionScore(5)
                .build();

        JsonNode productNode = objectMapper.createObjectNode();
        ObjectNode productJsonObject = (ObjectNode) productNode;
        productJsonObject.put("barCode", productEntity.getBarCode());
        productJsonObject.put("nutritionScore", productEntity.getNutritionScore());


        when(productRepository.existsByBarCode(anyString()))
                .thenReturn(true);

        when(productRepository.findByBarCode(anyString()))
                .thenReturn(productEntity);

        ReflectionTestUtils.setField(productService, "provider", new OFFDataProvider(objectMapper));

        assertEquals(5, productService.getProduct("7622210449283").get("nutritionScore").asInt());
    }

    @Test
    void OFFProductService_getProduct_ProductDoesNotExist() throws IOException {

        assertThrows(NullPointerException.class, () -> {
            int score = productService.getProduct("123456789").get("nutritionScore").asInt();
        });
    }



    @Test
    void OFFDataProvider_getProductNode_ProductExist() throws IOException {

        ReflectionTestUtils.setField(dataProvider, "objectMapper", new ObjectMapper());

        JsonNode node = dataProvider.getProductNode("7622210449283");

        assertEquals("7622210449283",node.get("code").asText());
        assertEquals("Biscuit (cookie), with chocolate, prepacked",node.get("product").get("ecoscore_data").get("agribalyse").get("name_en").asText());
    }

    @Test
    void OFFDataProvider_getProductNode_ProductDoesNotExist() throws IOException {

        ReflectionTestUtils.setField(dataProvider, "objectMapper", new ObjectMapper());

        JsonNode node = dataProvider.getProductNode("123");

        assertEquals("no code or invalid code",node.get("status_verbose").asText());
    }

    @Test
    void OFFDataProvider_getProductJson_ValidJsonNode() throws JsonProcessingException {

        ProductEntity productEntity = ProductEntity.builder()
                .id(2)
                .barCode("1234")
                .name("Biscuits")
                .nutritionScore(8)
                .classe("Mangeable")
                .color("green")
                .build();


        ReflectionTestUtils.setField(dataProvider, "objectMapper", new ObjectMapper());

        JsonNode jsonNode = dataProvider.getProductJson(productEntity);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode expectedNode = (ObjectNode) objectMapper.createObjectNode();
        expectedNode.put("id", 2);
        expectedNode.put("barCode", "1234");
        expectedNode.put("name", "Biscuits");
        expectedNode.put("nutritionScore", 8);
        expectedNode.put("classe", "Mangeable");
        expectedNode.put("color", "green");

        assertEquals(expectedNode, jsonNode);
    }

    @Test
    void OFFDataProvider_getProductJson_UncompletedEntity() throws JsonProcessingException {

        ProductEntity productEntity = ProductEntity.builder()
                .id(2)
                .barCode("1234")
                .name("Biscuits")
                .nutritionScore(8)
                .build();


        ReflectionTestUtils.setField(dataProvider, "objectMapper", new ObjectMapper());

        JsonNode jsonNode = dataProvider.getProductJson(productEntity);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode expectedNode = (ObjectNode) objectMapper.createObjectNode();
        expectedNode.put("id", 2);
        expectedNode.put("barCode", "1234");
        expectedNode.put("name", "Biscuits");
        expectedNode.put("nutritionScore", 8);
        expectedNode.put("classe", (String)null);
        expectedNode.put("color", (String)null);

        assertEquals(expectedNode, jsonNode);
    }


    @Test
    void OFFNutritionService_getNegativeComponent()
    {
        Nutriments nutriments = new Nutriments(200, 350, 1400, 2980, 1786, 1002);

        when(ruleRepository.findPointsByName("energy_100g", nutriments.getEnergy()))
                .thenReturn(3);
        when(ruleRepository.findPointsByName("saturated-fat_100g", nutriments.getSaturated_fat()))
                .thenReturn(4);
        when(ruleRepository.findPointsByName("sugars_100g", nutriments.getSugars()))
                .thenReturn(10);
        when(ruleRepository.findPointsByName("salt_100g", nutriments.getSalt()))
                .thenReturn(8);

        assertEquals(25, nutritionService.getNegativeComponent(nutriments));
    }

    @Test
    void OFFNutritionService_getPositiveComponent()
    {
        Nutriments nutriments = new Nutriments(200, 350, 1400, 2980, 1786, 1002);

        when(ruleRepository.findPointsByName("fiber_100g", nutriments.getFiber()))
                .thenReturn(20);
        when(ruleRepository.findPointsByName("proteins_100g", nutriments.getProteins()))
                .thenReturn(-15);

        assertEquals(5, nutritionService.getPositiveComponent(nutriments));
    }

    @Test
    void OFFNutritionService_getScore()
    {
        Nutriments nutriments = new Nutriments(200, 350, 1400, 2980, 1786, 1002);

        when(nutritionService.getNegativeComponent(nutriments))
                .thenReturn(25);
        when(nutritionService.getPositiveComponent(nutriments))
                .thenReturn(5);


        assertEquals(20, nutritionService.getScore(nutriments));
    }

    @Test
    void OFFNutritionService_getNutritionInfoEntity_ScoreInRanges()
    {
        NutritionInfoEntity expectedNutritionInfo = new NutritionInfoEntity();
        expectedNutritionInfo.setClasse("Mouai");
        expectedNutritionInfo.setColor("orange");
        expectedNutritionInfo.setLower_bound(11);
        expectedNutritionInfo.setUpper_bound(18);

        when(nutritionInfoRepository.getInfoByScore(16))
                .thenReturn(expectedNutritionInfo);

        NutritionInfoEntity nutritionInfoEntity =  nutritionService.getNutritionInfoEntity(16);

        assertEquals("Mouai", nutritionInfoEntity.getClasse());
        assertEquals("orange", nutritionInfoEntity.getColor());
        assertEquals(11, nutritionInfoEntity.getLower_bound());
        assertEquals(18, nutritionInfoEntity.getUpper_bound());
    }

    @Test
    void OFFNutritionService_getNutritionInfoEntity_ScoreOutRanges()
    {
        NutritionInfoEntity expectedNutritionInfo = new NutritionInfoEntity();
        expectedNutritionInfo.setClasse(null);
        expectedNutritionInfo.setColor(null);
        expectedNutritionInfo.setLower_bound(0);
        expectedNutritionInfo.setUpper_bound(0);

        when(nutritionInfoRepository.getInfoByScore(-14))
                .thenReturn(expectedNutritionInfo);
        NutritionInfoEntity nutritionInfoEntity =  nutritionInfoRepository.getInfoByScore(-14);

        assertEquals(null, nutritionInfoEntity.getClasse());
        assertEquals(null, nutritionInfoEntity.getColor());
        assertEquals(0, nutritionInfoEntity.getLower_bound());
        assertEquals(0, nutritionInfoEntity.getUpper_bound());

    }
}