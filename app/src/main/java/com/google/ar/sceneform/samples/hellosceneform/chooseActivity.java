package com.google.ar.sceneform.samples.hellosceneform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class chooseActivity extends AppCompatActivity {

    private ArrayList<FurnitureInfoClass> allFurnitureInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        findViewById(R.id.designRoom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HelloSceneformActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), shoppingMenu.class);
                startActivity(intent);
            }
        });
    }


}
