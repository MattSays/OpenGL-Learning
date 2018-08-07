package it.mattsay.openglgame.core.entities;

import org.joml.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f rotation;

    public Camera() {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    /**
     * Translates the camera with the given offsets
     *
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        this.position.add(x, y, z);
    }

    /**
     * Rotates the camera with the given offsets
     *
     * @param xRot
     * @param yRot
     * @param zRot
     */
    public void rotate(float xRot, float yRot, float zRot) {
        this.rotation.add(xRot, yRot, zRot);
    }

}
