package it.mattsay.openglgame.core.entities;

import it.mattsay.openglgame.core.rendering.models.TexturedModel;
import org.joml.Vector3f;

public class Entity {

    private TexturedModel model;
    private Vector3f position, rotation;
    private float scale;

    public Entity(TexturedModel model, Vector3f position) {
        this.model = model;
        this.position = position;
        this.rotation = new Vector3f();
        this.scale = 1.0f;
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

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public TexturedModel getModel() {
        return model;
    }

    /**
     * Translates the entity with the given offsets
     *
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        this.position.add(x, y, z);
    }

    /**
     * Rotates the entity with the given offsets
     *
     * @param xRot
     * @param yRot
     * @param zRot
     */
    public void rotate(float xRot, float yRot, float zRot) {
        this.rotation.add(xRot, yRot, zRot);
    }

    /**
     * Scales the entity with the given offset
     *
     * @param scale
     */
    public void scale(float scale) {
        this.scale += scale;
    }

    /**
     * Initializes the entity
     */
    public void init() {
        this.model.init();
    }

    /**
     * Destroys the entity
     */
    public void destroy() {
        this.model.destroy();
    }

}
