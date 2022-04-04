package com.example.NutritionInfo.domain;

public class Nutriments{

    private int energy;
    private int saturated_fat;
    private int sugars;
    private int salt;
    private int fiber;
    private int proteins;

    public Nutriments(int energy, int saturated_fat, int sugars, int salt, int fiber, int proteins) {
        this.energy = energy;
        this.saturated_fat = saturated_fat;
        this.sugars = sugars;
        this.salt = salt;
        this.fiber = fiber;
        this.proteins = proteins;
    }

    public int getEnergy() {
        return energy;
    }

    public int getSaturated_fat() {
        return saturated_fat;
    }

    public int getSugars() {
        return sugars;
    }

    public int getSalt() {
        return salt;
    }

    public int getFiber() {
        return fiber;
    }

    public int getProteins() {
        return proteins;
    }
}
