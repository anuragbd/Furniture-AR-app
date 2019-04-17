package com.google.ar.sceneform.samples.hellosceneform;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FullImageDisplay extends AppCompatActivity {

    private Intent intent;
    private int pose;
    private int imgsId;
    private Map<Integer, Integer> uriMapping = new HashMap<Integer, Integer>();

    private ArrayList<FurnitureInfoClass> allFurnitureInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_display);
        addFurnitureInformation();
        loadUris();
        Intent i = getIntent();

        //pose = i.getExtras().getInt("pose");
        imgsId = i.getExtras().getInt("id");

        ImageView imageView = (ImageView)findViewById(R.id.full_image_view);
        imageView.setImageResource(imgsId);

        StringBuilder strDisp = new StringBuilder();

        int in;
        int sz = allFurnitureInfo.size();

        for (in = 0; in < sz; in++) {
            if (allFurnitureInfo.get(in).getFurnitureId() == imgsId) {
                strDisp.append( allFurnitureInfo.get(in).getName());
                strDisp.append("\n");
                strDisp.append("Dimensions of the furniture are: \n");
                strDisp.append("Length: ");
                strDisp.append(allFurnitureInfo.get(in).getLength());
                strDisp.append("\n");
                strDisp.append("Width: ");
                strDisp.append(allFurnitureInfo.get(in).getWidth());
                strDisp.append("\n");
                strDisp.append("Height: ");
                strDisp.append(allFurnitureInfo.get(in).getHeight());
                break;
            }
        }
        TextView tv = findViewById(R.id.furniture_text);
        tv.setText(strDisp.toString());
        tv.bringToFront();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_camera);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisplaySingleFurnitureAR.class);

                Bundle bundle = new Bundle();
                bundle.putInt("model", uriMapping.get(imgsId));

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void addFurnitureInformation()
    {

        FurnitureInfoClass furniture = new FurnitureInfoClass("Bed 1", R.drawable.bed, R.raw.bed, 180, 60, 40);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Bed 2", R.drawable.bed2, R.raw.bed2, 150, 70, 40);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Bed 3", R.drawable.bed3, R.raw.bed3, 145, 62, 40);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Bench", R.drawable.bench, R.raw.bench, 200, 40, 40);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("BookShelf 1", R.drawable.bookshelf, R.raw.bookshelf, 100, 50, 180);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 1", R.drawable.chair, R.raw.chair, 45, 30, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 2", R.drawable.chair1, R.raw.chair1, 40, 27, 90);
        allFurnitureInfo.add(furniture);

        //   furniture = new FurnitureInfoClass("Chair 3", R.drawable.chair1, R.raw.chair1, 41, 27, 80);
        // allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 4", R.drawable.chair3, R.raw.chair3, 35, 25, 70);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 5", R.drawable.chair4, R.raw.chair4, 50, 32, 91);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 6", R.drawable.chair_got, R.raw.chair_got, 47, 31, 90);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Desk 1", R.drawable.desk, R.raw.desk, 110, 70, 70);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Desk 2", R.drawable.desk3, R.raw.desk3, 115, 75, 80);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Draft Table 1", R.drawable.tabledraft, R.raw.draft_table, 145, 100, 120);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Lamp 1", R.drawable.lamp, R.raw.lamp, 20, 20, 150);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Lamp 2", R.drawable.lamp2, R.raw.lamp2, 25, 25, 160);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Lamp 3", R.drawable.lamp3, R.raw.lamp3, 17, 17, 140);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Lamp 4", R.drawable.lamp4, R.raw.lamp4, 20, 15, 140);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Piano", R.drawable.piano, R.raw.piano, 195, 45, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Sofa 1", R.drawable.sofa, R.raw.model, 200, 50, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Sofa 2", R.drawable.sofa2, R.raw.sofa2, 190, 45, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Sofa 3", R.drawable.sofa3, R.raw.sofa3, 195, 45, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Speaker 1", R.drawable.speakers, R.raw.speaker, 50, 80, 150);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Speaker 2", R.drawable.speakers2, R.raw.speaker2, 60, 60, 180);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Table 1", R.drawable.table, R.raw.table, 100, 50, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Table 2", R.drawable.table2, R.raw.table2, 120, 60, 100);
        allFurnitureInfo.add(furniture);

    }

    private void loadUris() {
        //Uri address = Uri.parse(R.raw.bookshelf);
        uriMapping.put(R.drawable.bookshelf, R.raw.bookshelf);
        uriMapping.put(R.drawable.table, R.raw.table);
        uriMapping.put(R.drawable.lamp, R.raw.lamp);
        uriMapping.put(R.drawable.tabledraft, R.raw.draft_table);
        uriMapping.put(R.drawable.chair1, R.raw.chair1);
        uriMapping.put(R.drawable.chair, R.raw.chair);
        uriMapping.put(R.drawable.bed, R.raw.bed);
        uriMapping.put(R.drawable.bed2, R.raw.bed2);
        uriMapping.put(R.drawable.bed3, R.raw.bed3);
        uriMapping.put(R.drawable.bench, R.raw.bench);
        uriMapping.put(R.drawable.chair3, R.raw.chair3);
        uriMapping.put(R.drawable.chair4, R.raw.chair4);
        uriMapping.put(R.drawable.chair_got, R.raw.chair_got);
        uriMapping.put(R.drawable.desk, R.raw.desk);
        uriMapping.put(R.drawable.desk3, R.raw.desk3);
        uriMapping.put(R.drawable.lamp2, R.raw.lamp2);
        uriMapping.put(R.drawable.lamp3, R.raw.lamp3);
        uriMapping.put(R.drawable.lamp4, R.raw.lamp4);
        uriMapping.put(R.drawable.piano, R.raw.piano);
        uriMapping.put(R.drawable.speakers, R.raw.speaker);
        uriMapping.put(R.drawable.speakers2,R.raw.speaker2);
        uriMapping.put(R.drawable.table2, R.raw.table2);


//        address = Uri.parse("chair.sfb");
//        uriMapping.put(R.drawable.chair, address.toString());
//
//        address = Uri.parse("chair1.sfb");
//        uriMapping.put(R.drawable.chair1, address.toString());
//
//        address = Uri.parse("draft_table.sfb");
//        uriMapping.put(R.drawable.tabledraft, address.toString());
//
//        address = Uri.parse("lamp.sfb");
//        uriMapping.put(R.drawable.lamp, address.toString());
//
//        address = Uri.parse("table.sfb");
//        uriMapping.put(R.drawable.table, address.toString());
    }
}
