package com.example.NutritionInfo.dal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import javax.persistence.*;

@Entity
@Table(name = "product")
@Builder
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue
    private int id;
    private String barCode;
    private String name;
    private int nutritionScore;
    private String color;
    private String classe;
    private Boolean out;
    private String image_small_url;
    private String brand;
    public ProductEntity() {
    }

    public ProductEntity(String barCode, String name)  {
        this.barCode = barCode;
        this.name = name;
        this.out = true;
    }

    public ProductEntity(String barCode, String name, int nutritionScore, String color, String classe)  {
        this.barCode = barCode;
        this.name = name;
        this.nutritionScore = nutritionScore;
        this.color = color;
        this.classe = classe;
    }

    public void setNutritionScore(int nutritionScore) {
        this.nutritionScore = nutritionScore;
    }

    public int getId() {
        return id;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getName() {
        return name;
    }

    public int getNutritionScore() {
        return nutritionScore;
    }

    public String getColor() {
        return color;
    }

    public String getClasse() {
        return classe;
    }

    public void setColor(String color) {
        if (color == null) {
            this.color = "red";
        }else {
            this.color = color;
        }

    }

    public void setClasse(String classe) {
        if (color == null) {
            this.color = "Degueu";
        }else {
            this.color = classe;
        }
    }

    public Boolean getOut() {
        return out;
    }

    public void setOut(Boolean out) {
        this.out = out;
    }

    public String toString() {
        return "name : " + name;
    }
}
