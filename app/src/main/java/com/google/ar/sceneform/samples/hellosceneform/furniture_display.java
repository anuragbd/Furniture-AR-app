package com.google.ar.sceneform.samples.hellosceneform;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class furniture_display extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Intent intent;
    private int pose;
    private Bundle bundle;
    private int[] furnitureId;
    private Map<Integer, List<Integer>> imgMapping = new HashMap<Integer, List<Integer>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_display);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        intent = getIntent();
        bundle = intent.getExtras();
        //pose = bundle.getInt("pose");
        //furnitureId = bundle.getIntArray("id");
        loadMap();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_furniture_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view. DELETED
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
           // List<Integer> images = imgMapping.get(furnitureId[pose]);
            switch (position) {
                case 0:
                    furniture_display_tab1 tab1 = new furniture_display_tab1();
                    return tab1;
                case 1:
                    furniture_display_tab2 tab2 = new furniture_display_tab2();
                    return tab2;
                case 2:
                    furniture_display_tab3 tab3 = new furniture_display_tab3();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    private void loadMap() {
        List<Integer> images = new ArrayList<Integer>();

        //chair
        images.add(R.drawable.chair);
        images.add(R.drawable.chair1);
        images.add(R.drawable.bookshelf);

        imgMapping.put(R.drawable.chair, images);

        //chair 2
        images.add(R.drawable.chair1);
        images.add(R.drawable.bookshelf);
        images.add(R.drawable.sofa);

        imgMapping.put(R.drawable.chair1, images);

        //bookshelf
        images.add(R.drawable.chair1);
        images.add(R.drawable.bookshelf);
        images.add(R.drawable.sofa);

        imgMapping.put(R.drawable.bookshelf, images);

        //sofa
        images.add(R.drawable.sofa);
        images.add(R.drawable.bookshelf);
        images.add(R.drawable.sofa);

        imgMapping.put(R.drawable.sofa, images);

        //lamp
        images.add(R.drawable.lamp);
        images.add(R.drawable.bookshelf);
        images.add(R.drawable.sofa);

        imgMapping.put(R.drawable.lamp, images);

        //table
        images.add(R.drawable.table);
        images.add(R.drawable.bookshelf);
        images.add(R.drawable.sofa);

        imgMapping.put(R.drawable.bookshelf, images);

        //tabledraft
        images.add(R.drawable.tabledraft);
        images.add(R.drawable.bookshelf);
        images.add(R.drawable.sofa);

        imgMapping.put(R.drawable.tabledraft, images);


    }
}
