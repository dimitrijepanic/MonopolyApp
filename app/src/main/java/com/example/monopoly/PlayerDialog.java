package com.example.monopoly;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayerDialog extends Dialog {

    Player player;
    GameBoardActivity activity;
    public PlayerDialog(@NonNull Context context, Player player, GameBoardActivity activity){
        super(context);
        this.player = player;
        this.activity = (GameBoardActivity) activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_player);
        Button b1 = (Button) findViewById(R.id.cancel);
        TextView name = (TextView) findViewById(R.id.playerName);
        TextView money = (TextView) findViewById(R.id.playerMoney);
        ListView owns = (ListView) findViewById(R.id.list_view);

        name.setText(player.getName());
        money.setText(player.getMoney() + "$");
        List<String> list = new ArrayList<>();

        for(int i = 0; i < player.getOwns().size(); i++){
            list.add(player.getOwns().get(i).getDescription());
        }

        ArrayAdapter<String> arr = new ArrayAdapter<String>(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                list
        );
        owns.setAdapter(arr);

        b1.setOnClickListener(v->{
            dismiss();
        });
    }
}
