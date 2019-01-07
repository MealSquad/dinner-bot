package com.github.mealsquad.model;

public class User {

    private String name;
    private int kills;
    private int damage;
    private double kilometers;

    public User(String name, int kills, int damage, double kilometers) {
        this.name = name;
        this.kills = kills;
        this.damage = damage;
        this.kilometers = kilometers;
    }

    public String getName() {
        return name;
    }

    public int getKills() {
        return kills;
    }

    public int getDamage() {
        return damage;
    }

    public double getKilometers() {
        return kilometers;
    }
}
