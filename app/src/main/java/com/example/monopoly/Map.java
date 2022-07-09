package com.example.monopoly;

import java.util.HashMap;

public class Map {

    public Field [] fields;
    public static CommunityChestCard[] communityChestCards;
    public static ChanceCard[] chanceCards;

    public static java.util.Map<Integer, Integer> hashMap = new HashMap<>();

    // dodaj i rent ako skupis sve boje

    public void initMap(){
        fields = new Field[40];

        fields[0] = new Field("START", 0, Field.Type.START,-1);
        fields[10] = new Field("JAIL", 0, Field.Type.JAIL,-1);
        fields[20] = new Field("PARKING", 0, Field.Type.PARKING,-1);
        fields[30] = new Field("GO TO JAIL", 0, Field.Type.GO_TO_JAIL,-1);

        hashMap.put(0,2);
        // brown
        fields[1] = new Field("MADRID", 60, Field.Type.BASIC,0);
        fields[1].setPayout(2);
        fields[3] = new Field("GIETHORN", 60, Field.Type.BASIC,0);
        fields[3].setPayout(4);

        // cyan
        fields[6] = new Field("TAI PEI", 100, Field.Type.BASIC,1);
        fields[6].setPayout(6);
        fields[8] = new Field("CAPE TOWN", 100, Field.Type.BASIC,1);
        fields[8].setPayout(6);
        fields[9] = new Field("QUEENS TOWN", 120, Field.Type.BASIC,1);
        fields[9].setPayout(8);
        hashMap.put(1,3);

        // purple
        fields[11] = new Field("SYDNEY", 140, Field.Type.BASIC,2);
        fields[11].setPayout(10);
        fields[13] = new Field("AMSTERDAM", 140, Field.Type.BASIC,2);
        fields[13].setPayout(10);
        fields[14] = new Field("NEW YORK", 160, Field.Type.BASIC,2);
        fields[14].setPayout(12);
        hashMap.put(2,3);

        //orange
        fields[16] = new Field("TOKYO", 180, Field.Type.BASIC,3);
        fields[16].setPayout(14);
        fields[18] = new Field("MOSCOW", 180, Field.Type.BASIC,3);
        fields[18].setPayout(14);
        fields[19] = new Field("LONDON", 200, Field.Type.BASIC,3);
        fields[19].setPayout(16);
        hashMap.put(3,3);

        //red
        fields[24] = new Field("BELFAST", 240, Field.Type.BASIC,4);
        fields[24].setPayout(20);
        fields[23] = new Field("ATHENS", 220, Field.Type.BASIC,4);
        fields[23].setPayout(18);
        fields[21] = new Field("BELGRADE", 220, Field.Type.BASIC,4);
        fields[21].setPayout(18);
        hashMap.put(4,3);

        // yellow
        fields[26] = new Field("SANTIAGO", 260, Field.Type.BASIC,5);
        fields[26].setPayout(22);
        fields[27] = new Field("MEXICO CITY", 260, Field.Type.BASIC,5);
        fields[27].setPayout(22);
        fields[29] = new Field("WARSAW", 280, Field.Type.BASIC,5);
        fields[29].setPayout(24);

        hashMap.put(5,3);
        //green
        fields[31] = new Field("ISTANBUL", 300, Field.Type.BASIC,6);
        fields[31].setPayout(26);
        fields[32] = new Field("LISBON", 300, Field.Type.BASIC,6);
        fields[32].setPayout(26);
        fields[34] = new Field("RIGA", 320, Field.Type.BASIC,6);
        fields[34].setPayout(28);
        hashMap.put(6,3);
        // blue
        fields[37] = new Field("HONG KONG", 350, Field.Type.BASIC,7);
        fields[37].setPayout(35);
        fields[39] = new Field("LIMA", 400, Field.Type.BASIC,7);
        fields[39].setPayout(50);
        hashMap.put(7,3);
        // chance
        fields[2] = new Field("CHANCE", 0, Field.Type.CHANCE,-1);
        fields[12] = new Field("CHANCE", 0, Field.Type.CHANCE,-1);
        fields[22] = new Field("CHANCE", 0, Field.Type.CHANCE,-1);
        fields[33] = new Field("CHANCE", 0, Field.Type.CHANCE,-1);

        // community chest
        fields[7] = new Field("COMMUNITY CHEST", 0, Field.Type.COMMUNITY_CHEST,-1);
        fields[17] = new Field("COMMUNITY CHEST", 0, Field.Type.COMMUNITY_CHEST,-1);
        fields[28] = new Field("COMMUNITY CHEST", 0, Field.Type.COMMUNITY_CHEST,-1);
        fields[38] = new Field("COMMUNITY CHEST", 0, Field.Type.COMMUNITY_CHEST,-1);


        // center
        fields[5] = new Field("UTILITY 1", 200, Field.Type.CENTER,-1);
        fields[5].setPayout(25);
        fields[15] = new Field("UTILITY 2", 200, Field.Type.CENTER,-1);
        fields[15].setPayout(25);
        fields[25] = new Field("UTILITY 3", 200, Field.Type.CENTER,-1);
        fields[25].setPayout(25);
        fields[35] = new Field("UTILITY 4", 200, Field.Type.CENTER,-1);
        fields[35].setPayout(25);

        // tax
        fields[4] = new Field("TAX", 200, Field.Type.TAX,-1);
        fields[36] = new Field("TAX", 100, Field.Type.TAX,-1);

        initXY();
        initCards();
    }

    private void initCards(){
        communityChestCards = new CommunityChestCard[5];
        communityChestCards[0] = new CommunityChestCard("Your house is leaking! Pay 150$", 150,0);
        communityChestCards[1] = new CommunityChestCard("Congratulation your daughter won the pageant! Collect 150$", 150,3);
        communityChestCards[2] = new CommunityChestCard("You got in a car crash.. Pay 500$", 500,0);
        communityChestCards[3] = new CommunityChestCard("Beautiful house! Collect 500$", 500,3);
        communityChestCards[4] = new CommunityChestCard("We don't like you! Pay 300", 300,0);

        chanceCards = new ChanceCard[5];
        chanceCards[0] = new ChanceCard("We caught you! Go to jail!", 0,1);
        chanceCards[1] = new ChanceCard("Go to start and collect 200$", 200,2);
        chanceCards[2] = new ChanceCard("Brm brm! Go to parking", 500,4);
        chanceCards[3] = new ChanceCard("Beautiful house! Collect 500$", 500,3);
        chanceCards[4] = new ChanceCard("We don't like you! Pay 300", 300,0);
    }

    private void initXY(){
        fields[0].setX(370);
        fields[0].setY(370);

        fields[1].setX(330);
        fields[1].setY(370);

        fields[2].setX(295);
        fields[2].setY(370);

        fields[3].setX(265);
        fields[3].setY(370);

        fields[4].setX(230);
        fields[4].setY(370);

        fields[5].setX(200);
        fields[5].setY(370);

        fields[6].setX(170);
        fields[6].setY(370);

        fields[7].setX(135);
        fields[7].setY(370);

        fields[8].setX(105);
        fields[8].setY(370);

        fields[9].setX(75);
        fields[9].setY(370);

        fields[10].setX(30);
        fields[10].setY(370);

        fields[11].setX(30);
        fields[11].setY(330);

        fields[12].setX(30);
        fields[12].setY(295);

        fields[13].setX(30);
        fields[13].setY(265);

        fields[14].setX(30);
        fields[14].setY(235);

        fields[15].setX(30);
        fields[15].setY(200);

        fields[16].setX(30);
        fields[16].setY(170);

        fields[17].setX(30);
        fields[17].setY(140);

        fields[18].setX(30);
        fields[18].setY(105);

        fields[19].setX(30);
        fields[19].setY(70);

        fields[20].setX(30);
        fields[20].setY(30);

        fields[29].setX(330);
        fields[29].setY(30);

        fields[28].setX(295);
        fields[28].setY(30);

        fields[27].setX(265);
        fields[27].setY(30);

        fields[26].setX(230);
        fields[26].setY(30);

        fields[25].setX(200);
        fields[25].setY(30);

        fields[24].setX(170);
        fields[24].setY(30);

        fields[23].setX(135);
        fields[23].setY(30);

        fields[22].setX(105);
        fields[22].setY(30);

        fields[21].setX(75);
        fields[21].setY(30);

        fields[30].setX(370);
        fields[30].setY(30);

        fields[39].setX(370);
        fields[39].setY(330);

        fields[38].setX(370);
        fields[38].setY(295);

        fields[37].setX(370);
        fields[37].setY(265);

        fields[36].setX(370);
        fields[36].setY(235);

        fields[35].setX(370);
        fields[35].setY(200);

        fields[34].setX(370);
        fields[34].setY(170);

        fields[33].setX(370);
        fields[33].setY(140);

        fields[32].setX(370);
        fields[32].setY(105);

        fields[31].setX(370);
        fields[31].setY(70);
    }
}
