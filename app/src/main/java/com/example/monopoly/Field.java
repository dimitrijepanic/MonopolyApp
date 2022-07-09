package com.example.monopoly;

public class Field {
    public enum Type{
        BASIC,
        CHANCE,
        COMMUNITY_CHEST,
        JAIL,
        GO_TO_JAIL,
        PARKING,
        CENTER,
        START,
        TAX
    }

    private int x;
    private int y;
    private String description;
    private int price;
    private Type type;
    private int group;
    private Player owner;
    private int payout;

    public int getPayout() {
        return payout;
    }

    public void setPayout(int payout) {
        this.payout = payout;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Field(String description, int price, Type type, int group) {
        this.description = description;
        this.price = price;
        this.type = type;
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
