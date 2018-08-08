package it.mattsay.openglgame.core.rendering.sprites;

import it.mattsay.openglgame.core.rendering.Texture;
import org.joml.Vector2f;

public class Sprite {

    private Texture texture;
    private Vector2f position, rotation, scale;

    public Sprite(Texture texture, Vector2f position, Vector2f rotation, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Sprite(Texture texture, Vector2f position, Vector2f rotation) {
        this(texture, position, rotation, new Vector2f(texture.getWidth(), texture.getHeight()));
    }

    public Sprite(Texture texture, Vector2f position) {
        this(texture, position, new Vector2f());
    }

    public Sprite(Texture texture) {
        this(texture, new Vector2f());
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setPosition(float x, float y) {
        this.position = new Vector2f(x, y);
    }

    public Vector2f getRotation() {
        return rotation;
    }

    public void setRotation(Vector2f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y) {
        this.rotation = new Vector2f(x, y);
    }

    public Vector2f getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = new Vector2f(scale, scale);
    }

    public void setScale(float scaleX, float scaleY) {
        this.scale = new Vector2f(scaleX, scaleY);
    }

    public Texture getTexture() {
        return texture;
    }

    /**
     * Translates the entity with the given offsets
     *
     * @param x
     * @param y
     */
    public void translate(float x, float y) {
        this.position.add(x, y);
    }

    /**
     * Rotates the entity with the given offsets
     *
     * @param xRot
     * @param yRot
     */
    public void rotate(float xRot, float yRot) {
        this.rotation.add(xRot, yRot);
    }

    /**
     * Scales the entity with the given offset
     *
     * @param scale
     */
    public void scale(float scale) {
        this.scale.add(scale, scale);
    }

    public void scale(float scaleX, float scaleY) {
        this.scale.add(scaleX, scaleY);
    }

    /**
     * Initializes the entity
     */
    public void init() {
        this.texture.create();
    }

    /**
     * Renders the sprite with the given renderer
     *
     * @param renderer
     */
    public void render(SpriteRenderer renderer) {
        renderer.render(this.texture, this.position.x, this.position.y, this.rotation.x, this.rotation.y, this.scale.x, this.scale.y);
    }

    /**
     * Destroys the entity
     */
    public void destroy() {
        this.texture.destroy();
    }

}
