package com.example.monopoly;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class FieldDialog extends Dialog{

    GameBoardActivity activity;
    private Field field;
    private Player player;

    public FieldDialog(@NonNull Context context,Field field, Player player, AppCompatActivity activity){
        super(context);
        this.activity = (GameBoardActivity) activity;
        this.field = field;
        this.player = player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_field);
        Button b1 = (Button) findViewById(R.id.b1);
        Button b2 = (Button) findViewById(R.id.b2);
        canDismiss = false;
        b2.setOnClickListener(v -> {
            if(canDismiss)
                dismiss();
        });

        switch(field.getType()){
            case BASIC: basicField(); break;
            case CHANCE: chance(); break;
            case COMMUNITY_CHEST: communityChest(); break;
            case TAX : tax(); break;
            case CENTER : center(); break;
        }
    }

    private void center(){
        TextView txt = (TextView) findViewById(R.id.field_name);
        txt.setText("Welcome to " + field.getDescription() + "!");
        TextView txt2 = (TextView) findViewById(R.id.price);
        txt2.setText("Price is " + field.getPrice() + "$");
        TextView txt3 = (TextView) findViewById(R.id.owner);
        Button b1 = null;
        boolean buyIt = false;
        if(field.getOwner() != null && field.getOwner().getName().equals(player.getName())){
            txt3.setText("Welcome Home! You are the owner!");
            b1 = (Button) findViewById(R.id.b1);
            b1.setText("BUY");
            b1.setEnabled(false);
            canDismiss = true;
        } else
        if(field.getOwner() == null){
            txt3.setText("On the market! You can buy it!");
            b1 = (Button) findViewById(R.id.b1);
            b1.setText("BUY");
            buyIt = true;
            canDismiss = true;
        } else {
            int price = field.getPayout();
            txt3.setText("The owner is " + field.getOwner().getName() + ", pay him " + price * field.getOwner().getNumOfUtilities());
            b1 = (Button) findViewById(R.id.b1);
            b1.setText("PAY");
            canDismiss = false;
        }


        Button finalB = b1;
        boolean finalBuyIt = buyIt;

        b1.setOnClickListener(v ->{
            if(finalBuyIt)
            {
                int money = player.getMoney();
                int price = field.getPrice();

                if(money < price){
                    Toast.makeText(activity, "U dont have enough money..", Toast.LENGTH_SHORT).show();
                    return;
                }

                field.setOwner(player);
                player.add(field);
                player.addMoney(-price);
                Toast.makeText(activity, "Congratulation u bought it!", Toast.LENGTH_SHORT).show();
                player.addUtility();
                activity.changeMoney();
                finalB.setEnabled(false);
                canDismiss = true;
            } else {
                int moneyFrom = player.getMoney();
                int moneyTo = field.getOwner().getMoney();
                int price = field.getPayout() * field.getOwner().getNumOfUtilities();


                if(moneyFrom < price){
                    Toast.makeText(activity, "U dont have enough money losing the game..", Toast.LENGTH_SHORT).show();
                    return;
                }

                player.addMoney(-price);
                field.getOwner().addMoney(price);
                Toast.makeText(activity, "Payed him!", Toast.LENGTH_SHORT).show();
                activity.changeMoney();
                finalB.setEnabled(false);
                canDismiss = true;
            }

        });
    }


    private void communityChest(){
        TextView txt = (TextView) findViewById(R.id.field_name);
        txt.setText("Welcome to community chest!");
        TextView txt2 = (TextView) findViewById(R.id.price);
        txt2.setText("");
        TextView txt3 = (TextView) findViewById(R.id.owner);
        txt3.setText("");
        Button b1 = (Button) findViewById(R.id.b1);;
        b1.setText("DRAW CARD");
        b1.setOnClickListener(v ->{

            int random =  (int) ((Math.random() * ( Map.communityChestCards.length - 0)) + 0);
            CommunityChestCard card = Map.communityChestCards[random];
            txt3.setText(card.getDescription());


            switch(card.getType()){
                case 0:
                    int price = card.getMoney();
                    
                    if(player.getMoney() < price){
                        Toast.makeText(activity, "U dont have enough money..", Toast.LENGTH_SHORT).show();
                        player.setMoney(0);
                        canDismiss = true;
                        b1.setEnabled(false);
                        player.setColor(0xD3D3D3);
                        player.setBackgroundColor(0xD3D3D3);
                        player.setHasLost(true);
                        activity.setBackground();
                        return;
                    }

                    player.addMoney(-price);
                    GameBoardActivity.centerMoney += price;
                    break;
                case 1:
                    player.setField(10);
                    player.setInJail(0);
                    activity.paintPlayers(4);
                    break;
                case 2:
                    player.setField(0);
                    player.addMoney(200);
                    activity.paintPlayers(4);
                    break;
                case 3:
                    player.addMoney(card.getMoney());
                    break;
            }

            activity.changeMoney();
            activity.paintPlayers(4);
            b1.setEnabled(false);
            canDismiss = true;
        });
    }
    private void tax(){
        TextView txt = (TextView) findViewById(R.id.field_name);
        txt.setText("Pay tax!");
        TextView txt2 = (TextView) findViewById(R.id.price);
        txt2.setText("It's time for taxes! " + field.getPrice() + "$");
        TextView txt3 = (TextView) findViewById(R.id.owner);
        txt3.setText("");
        Button b1 = (Button) findViewById(R.id.b1);;
        b1.setText("PAY");
        b1.setOnClickListener(v ->{
            int money = player.getMoney();
            int price = field.getPrice();

            if(money < price){
                Toast.makeText(activity, "Can't pay taxes.. u lose", Toast.LENGTH_SHORT).show();
                player.setMoney(0);
                canDismiss = true;
                b1.setEnabled(false);
                player.setColor(0xD3D3D3);
                player.setBackgroundColor(0xD3D3D3);
                player.setHasLost(true);
                activity.setBackground();
                return;
            }
            player.addMoney(-price);
            GameBoardActivity.centerMoney += price;
            b1.setEnabled(false);
            activity.changeMoney();
            activity.paintPlayers(4);
            canDismiss = true;
        });
    }
    private void chance(){
        TextView txt = (TextView) findViewById(R.id.field_name);
        txt.setText("Welcome to chance!");
        TextView txt2 = (TextView) findViewById(R.id.price);
        txt2.setText("");
        TextView txt3 = (TextView) findViewById(R.id.owner);
        txt3.setText("");
        Button b1 = (Button) findViewById(R.id.b1);;
        b1.setText("DRAW CARD");
        b1.setOnClickListener(v ->{
            int random =  (int) ((Math.random() * ( Map.chanceCards.length - 0)) + 0);
            ChanceCard card = Map.chanceCards[random];
            txt3.setText(card.getDescription());

            switch(card.getType()){
                case 0:
                    int price = card.getMoney();
                    if(player.getMoney() < price){
                        Toast.makeText(activity, "U dont have enough money..", Toast.LENGTH_SHORT).show();
                        player.setMoney(0);
                        canDismiss = true;
                        b1.setEnabled(false);
                        player.setColor(0xD3D3D3);
                        player.setBackgroundColor(0xD3D3D3);
                        player.setHasLost(true);
                        activity.setBackground();
                        return;
                    }
                    player.addMoney(-price);
                    GameBoardActivity.centerMoney += price;
                    break;
                case 1:
                    player.setField(10);
                    player.setInJail(0);
                    activity.paintPlayers(4);
                    break;
                case 2:
                    player.setField(0);
                    player.addMoney(200);
                    activity.paintPlayers(4);
                    break;
                case 3:
                    player.addMoney(card.getMoney());
                    break;
                case 4:
                    // collect money
                    player.setField(20);
                    player.addMoney(GameBoardActivity.centerMoney);

                    GameBoardActivity.centerMoney = 0;
                    activity.paintPlayers(4);
            }
            activity.changeMoney();
            activity.paintPlayers(4);
            b1.setEnabled(false);
            canDismiss = true;
        });
    }


    private boolean canDismiss;
    private void basicField(){

        TextView txt = (TextView) findViewById(R.id.field_name);
        txt.setText("Welcome to " + field.getDescription() + "!");
        TextView txt2 = (TextView) findViewById(R.id.price);
        txt2.setText("Price is " + field.getPrice() + "$");
        TextView txt3 = (TextView) findViewById(R.id.owner);
        Button b1 = null;
        boolean buyIt = false;
        int mulPrice = 1;
        boolean canBuyHouse = false;

        if(field.getOwner() != null && field.getOwner().getName().equals(player.getName())){
            txt3.setText("Welcome Home! You are the owner!");
            List<Field> owned = field.getOwner().getOwns();
            int num =  Map.hashMap.get(field.getGroup());
            b1 = (Button) findViewById(R.id.b1);
            int numOfOwned = 0;
            for(Field f:owned){
                if(f.getGroup() == field.getGroup()) numOfOwned ++;
            }
            if(numOfOwned == num){
                txt3.setText("Welcome Home! Do you wanna buy a house?");
            } else {
                b1.setEnabled(false);
            }
            b1.setText("BUY");
            canDismiss = true;
        } else
        if(field.getOwner() == null){
            txt3.setText("On the market! You can buy it!");
            b1 = (Button) findViewById(R.id.b1);
            b1.setText("BUY");
            canDismiss = true;
            buyIt = true;
        } else {
            int price = field.getPayout();
            List<Field> owned = field.getOwner().getOwns();
            int num =  Map.hashMap.get(field.getGroup());
            int numOfOwned = 0;
            for(Field f:owned){
                if(f.getGroup() == field.getGroup()) numOfOwned ++;
            }

            if(numOfOwned == num) {
                price *= 2;
                mulPrice = 2;
            }

            txt3.setText("The owner is " + field.getOwner().getName()+ ", pay him " + field.getPayout());
            b1 = (Button) findViewById(R.id.b1);
            b1.setText("PAY");
            canDismiss = false;
        }


        Button finalB = b1;
        boolean finalBuyIt = buyIt;
        int finalMulPrice = mulPrice;
        b1.setOnClickListener(v ->{
            if(finalBuyIt)
            {
                int money = player.getMoney();
                int price = field.getPrice() * finalMulPrice;

                if(money < price){
                    Toast.makeText(activity, "U dont have enough money..", Toast.LENGTH_SHORT).show();
                    player.setMoney(0);
                    canDismiss = true;
                    finalB.setEnabled(false);
                    player.setColor(0xD3D3D3);
                    player.setBackgroundColor(0xD3D3D3);
                    player.setHasLost(true);
                    activity.setBackground();
                    return;
                }

                field.setOwner(player);
                player.add(field);
                player.addMoney(-price);
                Toast.makeText(activity, "Congratulations u bought it!", Toast.LENGTH_SHORT).show();
                activity.changeMoney();
                finalB.setEnabled(false);
                canDismiss = true;
            } else {
                int moneyFrom = player.getMoney();
                int moneyTo = field.getOwner().getMoney();
                int price = field.getPayout();

                if(moneyFrom < price){
                    Toast.makeText(activity, "U dont have enough money losing the game..", Toast.LENGTH_SHORT).show();
                    return;
                }

                player.addMoney(-price);
                field.getOwner().addMoney(price);
                Toast.makeText(activity, "Payed him!", Toast.LENGTH_SHORT).show();
                activity.changeMoney();
                finalB.setEnabled(false);
                canDismiss = true;
            }

        });
    }

}
