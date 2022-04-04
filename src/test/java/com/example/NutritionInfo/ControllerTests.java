package com.example.NutritionInfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class ControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void getProductTest_ProductExists() throws Exception {

        mvc.perform(get("/product/7622210449283"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barCode", is("7622210449283")))
                .andExpect(jsonPath("$.name", is("Biscuit (cookie), with chocolate, prepacked")))
                .andExpect(jsonPath("$.nutritionScore", is(9)))
                .andExpect(jsonPath("$.color", is("yellow")))
                .andExpect(jsonPath("$.classe", is("Mangeable")));
    }

    @Test
    void getProductTest_ProductDoesNotExist() throws Exception {

        assertThrows(Exception.class, () -> {
            mvc.perform(get("/product/123456789"));
        });
    }

}
