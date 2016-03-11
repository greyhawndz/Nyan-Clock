package com.example.hannah.nyanclock;

/**
 * Created by WilliamPC on 3/9/2016.
 */
public class Cat {
    public static final String TABLE_NAME = "Cat";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_HUNGER = "Hunger";
    public static final String COLUMN_HAPPINESS = "Happiness";

    private int id;
    private String name = "Nyanta";
    private int happiness;
    private int hunger;

    public Cat(){

    }
    public Cat(int happiness, int hunger){
        this.happiness = happiness;
        this.hunger = hunger;
    }
    public Cat(int id, int happiness, int hunger){
        this.id = id;
        this.happiness = happiness;
        this.hunger = hunger;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getHappiness(){
        return happiness;
    }

    public void setHappiness(int happiness){
        this.happiness = happiness;
    }

    public int getHunger(){
        return hunger;
    }

    public void setHunger(int hunger){
        this.hunger = hunger;
    }
}
