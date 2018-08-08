package it.mattsay.openglgame.core.rendering;

import org.joml.Vector2f;

public class Camera {

    private Vector2f position, rotation;

    public Camera() {
        this.position = new Vector2f();
        this.rotation = new Vector2f();
    }

    public Camera(Vector2f position, Vector2f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getRotation() {
        return rotation;
    }

    public void setRotation(Vector2f rotation) {
        this.rotation = rotation;
    }

    /**
     * Translates the camera with the given offsets
     *
     * @param x
     * @param y
     */
    public void translate(float x, float y) {
        this.position.add(x, y);
    }

    /**
     * Rotates the camera with the given offsets
     *
     * @param xRot
     * @param yRot
     */
    public void rotate(float xRot, float yRot) {
        this.rotation.add(xRot, yRot);
    }

}
