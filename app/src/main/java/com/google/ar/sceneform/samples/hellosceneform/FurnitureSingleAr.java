package com.google.ar.sceneform.samples.hellosceneform;

import android.app.Activity;
import android.view.MotionEvent;

import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.TransformableNode;


public class FurnitureSingleAr extends Node implements Node.OnTapListener {

    private final ModelRenderable model;
    //private final TransformableNode node;

    public FurnitureSingleAr(ModelRenderable m) {
        this.model = m;
        setOnTapListener(this);
    }

//    public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
//
//    }

}
