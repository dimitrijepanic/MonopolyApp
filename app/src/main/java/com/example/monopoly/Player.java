package com.example.monopoly;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {

    private int inJail = -1;
    private int field;
    private int money;
    private String name;
    private int color;
    private int backgroundColor;
    private List<Field> owns = new ArrayList<>();
    private int numOfUtilities = 0;
    public java.util.Map<Field, Integer> houses = new HashMap<>();
    private boolean hasLost = false;


    public boolean isHasLost() {
        return hasLost;
    }

    public void setHasLost(boolean hasLost) {
        for(Field field : this.getOwns()){
            field.setOwner(null);
        }
        this.hasLost = hasLost;
    }

    public Map<Field, Integer> getHouses() {
        return houses;
    }

    public void setHouses(Map<Field, Integer> houses) {
        this.houses = houses;
    }

    public int getNumOfUtilities() {
        return numOfUtilities;
    }

    public void setNumOfUtilities(int numOfUtilities) {
        this.numOfUtilities = numOfUtilities;
    }

    public void addUtility(){
        numOfUtilities++;
    }

    public int getInJail() {
        return inJail;
    }

    public void setInJail(int inJail) {
        this.inJail = inJail;
    }

    public List<Field> getOwns() {
        return owns;
    }

    public void setOwns(List<Field> owns) {
        this.owns = owns;
    }

    public void add(Field f){
        owns.add(f);
    }

    public List<Field> getList(){
        return owns;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void addMoney(int money){
        this.money += money;
    }
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Player(){

    }
    public Player(int field, int money, String name) {
        this.field = field;
        this.money = money;
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
