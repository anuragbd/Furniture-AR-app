package com.google.ar.sceneform.samples.hellosceneform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<FurnitureInfoClass> allFurnitureInfo = new ArrayList<>();

    public ImageAdapter(Context c, ArrayList<FurnitureInfoClass> afi) {
        this.mContext = c;
        this.allFurnitureInfo = afi;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return allFurnitureInfo.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null) {
            gridView = new View(mContext);

            gridView = inflater.inflate(R.layout.grid_helper, null);

            TextView tv = (TextView) gridView.findViewById(R.id.grid_item_image_text);
            tv.setText(allFurnitureInfo.get(position).getName());

            ImageView iv = (ImageView) gridView.findViewById(R.id.grid_item_image);
            iv.setImageResource(allFurnitureInfo.get(position).getFurnitureId());

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }


}
