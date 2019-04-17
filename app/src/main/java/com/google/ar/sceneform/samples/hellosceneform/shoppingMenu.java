package com.google.ar.sceneform.samples.hellosceneform;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class shoppingMenu extends AppCompatActivity {

    private String[] furnitureName = new String[]{
            "Bookshelf 1", "Chair 1", "Chair 2", "Lamp 1", "Sofa 1", "Table 1", "TableDraft 1"
    };

    private int[] furnitureId = new int[]{
            R.drawable.bookshelf,
            R.drawable.chair,
            R.drawable.chair1,
            R.drawable.lamp,
            R.drawable.sofa,
            R.drawable.table,
            R.drawable.tabledraft
    };

    private ArrayList<FurnitureInfoClass> allFurnitureInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addFurnitureInformation();

        int size = (int) Math.ceil(allFurnitureInfo.size() / 2);

        TableLayout tableLayout = findViewById(R.id.table_view);

        View.OnClickListener lis = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int u = v.getId();
                Intent intent = new Intent(getApplicationContext(), FullImageDisplay.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id",u);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        int counter = 0;
        for (int i = 0; i < size; i++) {
            TableRow tb = new TableRow(shoppingMenu.this);
            //tb.setMinimumHeight(400);
            // if(i%2==0)tb.setBackgroundColor(Color.BLACK);
//            tb.addView(new Button(shoppingMenu.this));
//            tb.addView(new Button(shoppingMenu.this));
            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = layoutInflater.inflate(R.layout.grid_helper, null, false);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.grid_item_image);
            TextView tv = (TextView) rowView.findViewById(R.id.grid_item_image_text);

            imageView.setImageResource(allFurnitureInfo.get(counter).getFurnitureId());
            tv.setText(allFurnitureInfo.get(counter).getName());
            imageView.setId(allFurnitureInfo.get(counter).getFurnitureId());
            imageView.setOnClickListener(lis);

            counter++;
            tb.addView(rowView);

            rowView = layoutInflater.inflate(R.layout.grid_helper, null, false);
            imageView = (ImageView) rowView.findViewById(R.id.grid_item_image);
            tv = (TextView) rowView.findViewById(R.id.grid_item_image_text);

            imageView.setImageResource(allFurnitureInfo.get(counter).getFurnitureId());
            tv.setText(allFurnitureInfo.get(counter).getName());
            imageView.setId(allFurnitureInfo.get(counter).getFurnitureId());
            imageView.setOnClickListener(lis);
            counter++;
            tb.addView(rowView);

            tableLayout.addView(tb);
        }

//        GridView gridView = (GridView) findViewById(R.id.gridview);
//        gridView.setAdapter(new ImageAdapter(this, allFurnitureInfo));

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getApplicationContext(), FullImageDisplay.class);
//                Bundle extra = new Bundle();
//                //extra.putInt("pose", position);
//                extra.putInt("id", allFurnitureInfo.get(position).getFurnitureId());
//                i.putExtras(extra);
//                startActivity(i);
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_shopping, menu);

        MenuItem searchViewItem = menu.findItem(R.id.menuSearchBtn);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final SearchView searchView = (SearchView) searchViewItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    private void addFurnitureInformation() {

        FurnitureInfoClass furniture = new FurnitureInfoClass("Bed 1", R.drawable.bed, R.raw.bed, 45, 30, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Bed 2", R.drawable.bed2, R.raw.bed2, 45, 30, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Bed 3", R.drawable.bed3, R.raw.bed3, 45, 30, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Bench", R.drawable.bench, R.raw.bench, 45, 30, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("BookShelf 1", R.drawable.bookshelf, R.raw.bookshelf, 100, 50, 180);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 1", R.drawable.chair, R.raw.chair, 45, 30, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 2", R.drawable.chair1, R.raw.chair1, 30, 20, 70);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 3", R.drawable.chair1, R.raw.chair1, 30, 20, 70);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 4", R.drawable.chair3, R.raw.chair3, 30, 20, 70);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 5", R.drawable.chair4, R.raw.chair4, 30, 20, 70);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Chair 6", R.drawable.chair_got, R.raw.chair_got, 30, 20, 70);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Desk 1", R.drawable.desk, R.raw.desk, 30, 20, 70);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Desk 2", R.drawable.desk3, R.raw.desk3, 30, 20, 70);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Draft Table 1", R.drawable.tabledraft, R.raw.draft_table, 145, 100, 120);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Lamp 1", R.drawable.lamp, R.raw.lamp, 20, 20, 150);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Lamp 2", R.drawable.lamp2, R.raw.lamp2, 20, 20, 150);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Lamp 3", R.drawable.lamp3, R.raw.lamp3, 20, 20, 150);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Lamp 4", R.drawable.lamp4, R.raw.lamp4, 20, 20, 150);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Sofa 1", R.drawable.sofa, R.raw.model, 200, 50, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Sofa 2", R.drawable.sofa2, R.raw.sofa2, 200, 50, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Sofa 3", R.drawable.sofa3, R.raw.sofa3, 200, 50, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Speaker 1", R.drawable.speakers, R.raw.speaker, 100, 50, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Speaker 2", R.drawable.speakers2, R.raw.speaker2, 100, 50, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Table 1", R.drawable.table, R.raw.table, 100, 50, 100);
        allFurnitureInfo.add(furniture);

        furniture = new FurnitureInfoClass("Table 2", R.drawable.table2, R.raw.table2, 100, 50, 100);
        allFurnitureInfo.add(furniture);

    }
}
