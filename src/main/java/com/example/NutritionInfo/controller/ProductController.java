package com.example.NutritionInfo.controller;

import com.example.NutritionInfo.service.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/product/{code}")
    JsonNode getProduct(@PathVariable String code) throws IOException {

        return service.getProduct(code);
    }

    @GetMapping("/search/{name}")
    JsonNode serachByName(@PathVariable String name) throws IOException {

        return service.searchListProduct(name);
    }

    @GetMapping("/details/{code}")
    JsonNode detailsByCode(@PathVariable String code) throws IOException {

        return service.getProductDetails(code);
    }

    @GetMapping("/add/{code}")
    JsonNode addByCode(@PathVariable String code) throws IOException {
        return service.addProduct(code);
    }

    @GetMapping("/delete/{code}")
    JsonNode deleteByCode(@PathVariable String code) throws IOException {
        return service.deleteProduct(code);
    }

    @GetMapping("/cart/")
    JsonNode getCart() throws IOException {
        return service.getCart();
    }
}

