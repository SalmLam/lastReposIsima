package com.example.NutritionInfo.dal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rule")
public class RuleEntity {

    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "points")
    private int points;

    @Column(name = "min_bound")
    private int min_bound;

    @Column(name = "component")
    private String component;

    public RuleEntity() {

    }

    public RuleEntity(Integer id, String name, int points, int min_bound, String component) {
        this.id = id;
        this.name = name;
        this.points = points;
        this.min_bound = min_bound;
        this.component = component;
    }
}
