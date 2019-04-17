package com.google.ar.sceneform.samples.hellosceneform;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.HitTestResult;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.Sun;
import com.google.ar.sceneform.collision.Box;
import com.google.ar.sceneform.common.TransformProvider;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.Material;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.GesturePointersUtility;
import com.google.ar.sceneform.ux.PinchGesture;
import com.google.ar.sceneform.ux.ScaleController;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.ar.sceneform.samples.hellosceneform.HelloSceneformActivity.checkIsSupportedDeviceOrFinish;
import static com.google.ar.schemas.lull.VertexAttributeUsage.Color;

public class DisplaySingleFurnitureAR extends AppCompatActivity {

    private Intent intent;
    private ArFragment arFragment;
    private ModelRenderable model;
    private Map<Pose, Boolean> anchorHitPose = new HashMap<Pose, Boolean>();
    private boolean isPlaced = false;
    //private TransformableNode removeNode;
    private Node removeNode;
    private Button button;
    private GestureDetector gestureDetector;
    private PinchGesture pinchGesture;
    private List<HitResult> hitResults = new ArrayList<>();

    ArrayList<Float> arrayList1 = new ArrayList<>();
    ArrayList<Float> arrayList2 = new ArrayList<>();
    private AnchorNode lastAnchorNode;
    ModelRenderable cubeRenderable, heightRenderable;
    Vector3 point1, point2;
    FloatingActionButton btnLen;
    private boolean isBtnLenClicked = false;
    private TextView txtDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setContentView(R.layout.activity_display_single_furniture_ar);
        button = (Button) findViewById(R.id.close);
        intent = getIntent();

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment_disp_single_pic);

//        btnLen = (FloatingActionButton) findViewById(R.id.fablen);
//        btnLen.setOnClickListener(v -> {
//            isBtnLenClicked = true;
//            onClear();
//        });
//        txtDistance = findViewById(R.id.txtDistance);

        ModelRenderable.builder()
                .setSource(this, intent.getExtras().getInt("model"))
                .build()
                .thenAccept(renderable -> model = renderable)
                .exceptionally(
                        throwable -> {
                            Toast toast =
                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            return null;
                        });

        MaterialFactory.makeTransparentWithColor(this, new Color(0F, 0F, 244F))
                .thenAccept(
                        material -> {
                            Vector3 vector3 = new Vector3(0.01f, 0.01f, 0.01f);
                            cubeRenderable = ShapeFactory.makeCube(vector3, Vector3.zero(), material);
                            cubeRenderable.setShadowCaster(false);
                            cubeRenderable.setShadowReceiver(false);
                        });

        //gestureDetector = new GestureDetector(this, new Ges)

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                if (isPlaced == true) {
                    return;
                }

                if (cubeRenderable == null) {
                    return;
                }

                if (isBtnLenClicked) {
                    if (lastAnchorNode == null) {
                        Anchor anchor = hitResult.createAnchor();
                        AnchorNode anchorNode = new AnchorNode(anchor);
                        anchorNode.setParent(arFragment.getArSceneView().getScene());

                        Pose pose = anchor.getPose();
                        if (arrayList1.isEmpty()) {
                            arrayList1.add(pose.tx());
                            arrayList1.add(pose.ty());
                            arrayList1.add(pose.tz());
                        }
                        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                        transformableNode.setParent(anchorNode);
                        transformableNode.setRenderable(cubeRenderable);
                        transformableNode.select();
                        lastAnchorNode = anchorNode;
                    } else {
                        int val = motionEvent.getActionMasked();
                        float axisVal = motionEvent.getAxisValue(MotionEvent.AXIS_X, motionEvent.getPointerId(motionEvent.getPointerCount() - 1));
                        Log.e("Values:", String.valueOf(val) + String.valueOf(axisVal));
                        Anchor anchor = hitResult.createAnchor();
                        AnchorNode anchorNode = new AnchorNode(anchor);
                        anchorNode.setParent(arFragment.getArSceneView().getScene());

                        Pose pose = anchor.getPose();


                        if (arrayList2.isEmpty()) {
                            arrayList2.add(pose.tx());
                            arrayList2.add(pose.ty());
                            arrayList2.add(pose.tz());
                            float d = getDistanceMeters(arrayList1, arrayList2);
                            txtDistance.setText("Distance: " + String.valueOf(d));
                        } else {
                            arrayList1.clear();
                            arrayList1.addAll(arrayList2);
                            arrayList2.clear();
                            arrayList2.add(pose.tx());
                            arrayList2.add(pose.ty());
                            arrayList2.add(pose.tz());
                            float d = getDistanceMeters(arrayList1, arrayList2);
                            txtDistance.setText("Distance: " + String.valueOf(d));
                        }

                        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
                        transformableNode.setParent(anchorNode);
                        transformableNode.setRenderable(cubeRenderable);
                        transformableNode.select();

                        Vector3 point1, point2;
                        point1 = lastAnchorNode.getWorldPosition();
                        point2 = anchorNode.getWorldPosition();

                        final Vector3 difference = Vector3.subtract(point1, point2);
                        final Vector3 directionFromTopToBottom = difference.normalized();
                        final Quaternion rotationFromAToB =
                                Quaternion.lookRotation(directionFromTopToBottom, Vector3.up());
                        MaterialFactory.makeOpaqueWithColor(getApplicationContext(), new Color(0, 255, 244))
                                .thenAccept(
                                        material -> {
                                            ModelRenderable model = ShapeFactory.makeCube(
                                                    new Vector3(.01f, .01f, difference.length()),
                                                    Vector3.zero(), material);
                                            Node node = new Node();
                                            node.setParent(anchorNode);
                                            node.setRenderable(model);
                                            node.setWorldPosition(Vector3.add(point1, point2).scaled(.5f));
                                            node.setWorldRotation(rotationFromAToB);
                                        }
                                );
                        lastAnchorNode = anchorNode;
                    }
                } else {

                    Anchor anchor1 = hitResult.createAnchor();
                    AnchorNode anchorNode1 = new AnchorNode(anchor1);
                    anchorNode1.setParent(arFragment.getArSceneView().getScene());

                    TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
                    //Node andy = new Node();
                    andy.setParent(anchorNode1);
                    andy.setRenderable(model);
                    andy.select();

//                Box box = (Box) andy.getRenderable().getCollisionShape();
//                Vector3  renderableSize = box.getSize();
//
//                Vector3 transformableNodeScale = andy.getWorldScale();
//                Vector3 finalSize = new Vector3(
//                        renderableSize.x * transformableNodeScale.x,
//                        renderableSize.y * transformableNodeScale.y,
//                        renderableSize.z * transformableNodeScale.z);
                    //  ScaleController scaleController = new ScaleController(andy, arFragment.getTransformationSystem().getPinchRecognizer());
                    //  scaleController.canStartTransformation()

                    andy.getScaleController().setMaxScale(1.5f);
                    andy.getScaleController().setMinScale(1.49f);

                    if (button.isEnabled() == true) {
                        button.setEnabled(false);
                    }

                    //andy.setWorldScale(andy.getLocalScale());

                    andy.setOnTapListener(new Node.OnTapListener() {
                        @Override
                        public void onTap(HitTestResult hitTestResult, MotionEvent motionEvent) {
                            removeNode = andy;

                            if (button.isEnabled() == false) {
                                button.setEnabled(true);
                            }
                        }
                    });


                }
            }
        });


//        arFragment.getArSceneView().getScene().addOnUpdateListener(new Scene.OnUpdateListener() {
//            @Override
//            public void onUpdate(FrameTime frameTime) {
//
//                Frame frame = arFragment.getArSceneView().getArFrame();
//                System.out.println("Inside 1");
//                if (Objects.nonNull(frame)) {
//                     System.out.println("Inside 2");
//                    if (Objects.nonNull(frame.getUpdatedTrackables(Plane.class))) {
//                           System.out.println("Inside 3");
//                          // hitResults = frame.hitTest(motio)
//                        for (Plane plane : frame.getUpdatedTrackables(Plane.class)) {
//                            if (plane.getTrackingState() == TrackingState.TRACKING ) {
//                                float a= plane.getExtentX();
//                                float b= plane.getExtentZ();
//                                      //  System.out.println("X and Y "+ plane.getExtentX()*100 + " " + plane.getExtentZ()*100);
//                            }
//                        }
//                    }
//                }
//            }
//        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.nonNull(removeNode)) {
                    removeNode.setEnabled(false);
//                    Renderable changeCopy = removeNode.getRenderable().makeCopy();
//                    Material temp = removeNode.getRenderable().getMaterial().makeCopy();
//                    com.google.ar.sceneform.rendering.Color color = new Color(android.graphics.Color.rgb(0,255,0));
//                    temp.setFloat3("baseColorTint", color);
//                    changeCopy.setMaterial(temp);
//                    removeNode.setRenderable(changeCopy);
                    button.setEnabled(false);
                    removeNode = null;
                }
            }
        });

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

    private float getDistanceMeters(ArrayList<Float> arayList1, ArrayList<Float> arrayList2) {

        float distanceX = arayList1.get(0) - arrayList2.get(0);
        float distanceY = arayList1.get(1) - arrayList2.get(1);
        float distanceZ = arayList1.get(2) - arrayList2.get(2);
        return (float) Math.sqrt(distanceX * distanceX +
                distanceY * distanceY +
                distanceZ * distanceZ);
    }
}
