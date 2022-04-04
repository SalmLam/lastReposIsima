package com.example.NutritionInfo.dal;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "nutrition_info")
public class NutritionInfoEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "classe")
    private String classe;

    @Column(name = "lower_bound")
    private int lower_bound;

    @Column(name = "upper_bound")
    private int upper_bound;

    @Column(name = "color")
    private String color;

}
