/*
 * Copyright 2018 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.ar.sceneform.samples.hellosceneform;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.Sun;
import com.google.ar.sceneform.collision.Box;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This is an example activity that uses the Sceneform UX package to make common AR tasks easier.
 */
public class HelloSceneformActivity extends AppCompatActivity {
    private static final String TAG = HelloSceneformActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;

    private ModelRenderable bedRenderable;
    private ModelRenderable bedRenderable2;
    private ModelRenderable bedRenderable3;
    private ModelRenderable benchRenderable;
    private ModelRenderable bookshelfRenderable;
    private ModelRenderable chairRenderable;
    private ModelRenderable chairRenderable1;
    private ModelRenderable chairRenderable2;
    private ModelRenderable chairRenderable3;
    private ModelRenderable chairRenderable4;
    private ModelRenderable chair_gotRenderable;
    private ModelRenderable deskRenderable;
    private ModelRenderable deskRenderable3;
    private ModelRenderable tableDraftRenderable;
    private ModelRenderable lampRenderable;
    private ModelRenderable lampRenderable2;
    private ModelRenderable lampRenderable3;
    private ModelRenderable lampRenderable4;
    private ModelRenderable pianoRenderable;
    private ModelRenderable sofaRenderable;
    private ModelRenderable sofaRenderable2;
    private ModelRenderable sofaRenderable3;
    private ModelRenderable speakerRenderable;
    private ModelRenderable speakerRenderable2;
    private ModelRenderable tableRenderable;
    private ModelRenderable tableRenderable2;

    private boolean flag = false;
    private Plane plane;
    private float xlen;
    private float zlen;
    private Session session;
    private ArSceneView arSceneView;
    private Button button;
    private TransformableNode removeNode = null;
   // List<ImageView> imageViews;
    private int index;
    private ModelRenderable dispModel;

//    private int[] furnitureId = new int[] {
//            R.drawable.bookshelf,
//            R.drawable.chair,
//            R.drawable.chair1,
//            R.drawable.lamp,
//            R.drawable.sofa,
//            R.drawable.table,
//            R.drawable.tabledraft
//    };
//


    private Map<Integer, ModelRenderable> idModelMap = new HashMap<>();

    private LinearLayout linearLayout;

    ArrayList<Float> arrayList1 = new ArrayList<>();
    ArrayList<Float> arrayList2 = new ArrayList<>();
    private AnchorNode lastAnchorNode;
    ModelRenderable cubeRenderable, heightRenderable;
    Vector3 point1, point2;
    Button btnLen;
    private boolean isBtnLenClicked = false;
    private TextView txtDistance;
    private int counter = 0;


    private ArrayList<FurnitureInfoClass> allFurnitureInfo = new ArrayList<>();
   // private ArrayList<FurnitureInfoClass> shortlistedFurniture = new ArrayList<>();

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    // CompletableFuture requires api level 24
    // FutureReturnValueIgnored is not valid

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }



        setContentView(R.layout.activity_ux);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        arSceneView = (ArSceneView) arFragment.getArSceneView();

        button = (Button) findViewById(R.id.close_main);

        linearLayout = (LinearLayout) findViewById(R.id.hr_scr_view_ll);

        btnLen = (Button) findViewById(R.id.disp_all);
        btnLen.setOnClickListener(v -> {
//            isBtnLenClicked = true;
//            onClear();
            updateScrView(allFurnitureInfo);
        });

        // When you build a Renderable, Sceneform loads its resources in the background while returning
        // a CompletableFuture. Call thenAccept(), handle(), or check isDone() before calling get().
        initModels();

        //Load information about all the furniture
        addFurnitureInformation();

        //Mapping between id and renderable
        updateMap();

        //add images to scrollView
        updateScrView(allFurnitureInfo);

        suggestFurniture();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

//        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
//            @Override
//            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
//                if (flag) {
//                    return;
//                }
//                System.out.println("tapLis 1");
//                if (startAnchor == null) {
//                    session = arFragment.getArSceneView().getSession();
//                    startAnchor = session.createAnchor(hitResult.getHitPose());
//                    return;
//                }
//                System.out.println("tapLis 2");
//                Pose stPose = startAnchor.getPose();
//                Pose endPose = hitResult.getHitPose();
//
//                startAnchor = null;
//
//                float dx = stPose.tx() - endPose.tx();
//                float dy = stPose.ty() - endPose.ty();
//                float dz = stPose.tz() - endPose.tz();
//
//                float dista = (float)Math.sqrt(dx*dx + dy*dy + dz*dx);
//                System.out.println("distance :" + dista);
//
////                Anchor anchor = hitResult.createAnchor();
////                AnchorNode anchorNode = new AnchorNode(anchor);
////                anchorNode.setParent(arFragment.getArSceneView().getScene());
////
////                TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
////                andy.setParent(anchorNode);
////                andy.setRenderable(dispModel);
////                andy.select();
////
////                flag = true;
//            }
//        });

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if (dispModel == null || flag) {
                   // Toast.makeText(getApplicationContext(), "x & y " + plane.getExtentX() + " " + plane.getExtentZ(), Toast.LENGTH_LONG);
                    ArrayList<FurnitureInfoClass> shortlistedFurniture = updateArray(plane.getExtentX()*100, plane.getExtentZ()*100);
                    updateScrView(shortlistedFurniture);
                    return;
                }

                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
                andy.setParent(anchorNode);
                andy.setRenderable(dispModel);
                andy.select();
                button.setEnabled(false);

                andy.setOnTapListener(new Node.OnTapListener() {
                    @Override
                    public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                        if (button.isEnabled() == false) {
                            button.setEnabled(true);
                            removeNode = andy;
                        }

                        Box box = (Box) andy.getRenderable().getCollisionShape();
                        Vector3 renderableSize = box.getSize();

                        Vector3 transformableNodeScale = andy.getWorldScale();
                        Vector3 finalSize =
                                new Vector3(
                                        renderableSize.x * transformableNodeScale.x,
                                        renderableSize.y * transformableNodeScale.y,
                                        renderableSize.z * transformableNodeScale.z);
                        Toast.makeText(getApplicationContext(), "length: " + finalSize.x + " width: " + finalSize.z + " height: " + finalSize.y, Toast.LENGTH_LONG).show();
                    }
                });

                flag = true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.isEnabled() == true) {
                    removeNode.setEnabled(false);
                    removeNode = null;
                    button.setEnabled(false);
                }
            }
        });
    }

    private void updateMap() {

        idModelMap.clear();
        int i = 0;
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), bedRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), bedRenderable2);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), bedRenderable3);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), benchRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), bookshelfRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), chairRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), chairRenderable1);
//        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), chairRenderable2);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), chairRenderable3);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), chairRenderable4);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), chair_gotRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), deskRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), deskRenderable3);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), tableDraftRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), lampRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), lampRenderable2);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), lampRenderable3);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), lampRenderable4);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), pianoRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), sofaRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), sofaRenderable2);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(),sofaRenderable3 );
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), speakerRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), speakerRenderable2);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), tableRenderable);
        idModelMap.put(allFurnitureInfo.get(i++).getFurnitureId(), tableRenderable2);

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

    private void updateScrView(ArrayList<FurnitureInfoClass> displayFurniture)
    {

        linearLayout.removeAllViews();
        ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
        int i;
        int len = displayFurniture.size();
        for(i = 0; i < len; i++) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = layoutInflater.inflate(R.layout.image_hr, null, false);

            ImageView imageView = rowView.findViewById(R.id.img_hr_view);
            imageView.setImageResource(displayFurniture.get(i).getFurnitureId());
            imageView.setId(displayFurniture.get(i).getFurnitureId());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateMap();
                    flag = false;
                    int u = v.getId();
                    dispModel = idModelMap.get(u);
                }
            });

            linearLayout.addView(rowView);
        }
    }




    private void onClear() {
        List<Node> children = new ArrayList<>(arFragment.getArSceneView().getScene().getChildren());
        for (Node node : children) {
            if (node instanceof AnchorNode) {
                if (((AnchorNode) node).getAnchor() != null) {
                    ((AnchorNode) node).getAnchor().detach();
                }
            }
            if (!(node instanceof Camera) && !(node instanceof Sun)) {
                node.setParent(null);
            }
        }
        arrayList1.clear();
        arrayList2.clear();
        lastAnchorNode = null;
        point1 = null;
        point2 = null;
        txtDistance.setText("");
    }

    private void suggestFurniture()
    {
        Scene scene = arFragment.getArSceneView().getScene();
        scene.addOnUpdateListener(new Scene.OnUpdateListener() {
            @Override
            public void onUpdate(FrameTime frameTime) {
                float maxX = Float.MIN_VALUE;
                float maxY = Float.MIN_VALUE;
                System.out.println("Inside 1");
                Frame frame = arFragment.getArSceneView().getArFrame();
                if (Objects.nonNull(frame)) {
                    System.out.println("Inside 2");
                    if (Objects.nonNull(frame.getUpdatedTrackables(Plane.class))) {
                        System.out.println("Inside 3");
                        for (Plane plane : frame.getUpdatedTrackables(Plane.class)) {
                            //maxX = Math.max(maxX, plane.getExtentX());
                            //maxY = Math.max(maxY, plane.getExtentZ());
                            System.out.println(plane.getExtentX() + " " + plane.getExtentX());
                        }

                        //maxX = maxY*100;
                        //maxY = maxY*100;
                        //updateArray(maxX,maxY);
                        //updateScrView(shortlistedFurniture);
                    }
                }
            }
        });
    }

    private ArrayList<FurnitureInfoClass> updateArray(float X, float Y)
    {
        int i;
        int len = allFurnitureInfo.size();

        ArrayList<FurnitureInfoClass> shortlistedFurniture = new ArrayList<>();

        for(i = 0; i < len; i++) {
            if (allFurnitureInfo.get(i).getLength() <= X && allFurnitureInfo.get(i).getWidth() <= Y) {
                shortlistedFurniture.add(allFurnitureInfo.get(i));
            }
        }

        return shortlistedFurniture;
    }

    private void initModels()
    {
        ModelRenderable.builder()
                .setSource(this, R.raw.bed)
                .build()
                .thenAccept(renderable -> bedRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.bed2)
                .build()
                .thenAccept(renderable -> bedRenderable2 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.bed3)
                .build()
                .thenAccept(renderable -> bedRenderable3 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.bench)
                .build()
                .thenAccept(renderable -> benchRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.bookshelf)
                .build()
                .thenAccept(renderable -> bookshelfRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.chair)
                .build()
                .thenAccept(renderable -> chairRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.chair1)
                .build()
                .thenAccept(renderable -> chairRenderable1 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.chair2)
                .build()
                .thenAccept(renderable -> chairRenderable2 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.chair3)
                .build()
                .thenAccept(renderable -> chairRenderable3 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.chair4)
                .build()
                .thenAccept(renderable -> chairRenderable4 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.chair_got)
                .build()
                .thenAccept(renderable -> chair_gotRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.desk)
                .build()
                .thenAccept(renderable -> deskRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.desk3)
                .build()
                .thenAccept(renderable -> deskRenderable3 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.draft_table)
                .build()
                .thenAccept(renderable -> tableDraftRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.lamp)
                .build()
                .thenAccept(renderable -> lampRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.lamp2)
                .build()
                .thenAccept(renderable -> lampRenderable2 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.lamp3)
                .build()
                .thenAccept(renderable -> lampRenderable3 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.lamp4)
                .build()
                .thenAccept(renderable -> lampRenderable4 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.piano)
                .build()
                .thenAccept(renderable -> pianoRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.model)
                .build()
                .thenAccept(renderable -> sofaRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.sofa2)
                .build()
                .thenAccept(renderable -> sofaRenderable2 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.sofa3)
                .build()
                .thenAccept(renderable -> sofaRenderable3 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.speaker)
                .build()
                .thenAccept(renderable -> speakerRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.speaker2)
                .build()
                .thenAccept(renderable -> speakerRenderable2 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.table)
                .build()
                .thenAccept(renderable -> tableRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        ModelRenderable.builder()
                .setSource(this, R.raw.table2)
                .build()
                .thenAccept(renderable -> tableRenderable2 = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });
    }



    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     * <p>
     * <p>Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     * <p>
     * <p>Finishes the activity if Sceneform can not run
     */
    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

    private String generateFilename() {
        String date =
                new SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.getDefault()).format(new Date());
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + File.separator + "Sceneform/" + date + "_screenshot.jpg";
    }

    private void saveBitmapToDisk(Bitmap bitmap, String filename) throws IOException {

        File out = new File(filename);
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        try (FileOutputStream outputStream = new FileOutputStream(filename);
             ByteArrayOutputStream outputData = new ByteArrayOutputStream()) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputData);
            outputData.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            throw new IOException("Failed to save bitmap to disk", ex);
        }
    }

    private void takePhoto() {
        final String filename = generateFilename();
        ArSceneView view = arFragment.getArSceneView();

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);

        // Create a handler thread to offload the processing of the image.
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap, filename);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(HelloSceneformActivity.this, e.toString(),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        "Photo saved", Snackbar.LENGTH_LONG);
                snackbar.setAction("Open in Photos", v -> {
                    File photoFile = new File(filename);

                    Uri photoURI = FileProvider.getUriForFile(HelloSceneformActivity.this,
                            HelloSceneformActivity.this.getPackageName(),
                            photoFile);
                    Intent intent = new Intent(Intent.ACTION_VIEW, photoURI);
                    intent.setDataAndType(photoURI, "image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                });
                snackbar.show();
            } else {
                Toast toast = Toast.makeText(HelloSceneformActivity.this,
                        "Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }
}
