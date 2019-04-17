package com.google.ar.sceneform.samples.hellosceneform;

public class FurnitureInfoClass {

    private String name;
    private int furnitureId;
    private int src;
    private float length;
    private float width;
    private float height;

    public FurnitureInfoClass(String name, int furnitureId, int src, float length, float width, float height) {
        this.name = name;
        this.furnitureId = furnitureId;
        this.src = src;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFurnitureId() {
        return furnitureId;
    }

    public void setFurnitureId(int furnitureId) {
        this.furnitureId = furnitureId;
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
