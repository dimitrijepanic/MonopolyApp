package com.example.monopoly;

public class CommunityChestCard {

    private String description;
    private int money;
    private int type; // tip - 0 plati, tip 1 - go to jail, tip 2 - back to start , tip 3 - collect

    public CommunityChestCard(String description, int money, int type) {
        this.description = description;
        this.money = money;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
