package it.mattsay.openglgame.core.rendering.shaders;

import it.mattsay.openglgame.core.Window;
import it.mattsay.openglgame.core.rendering.Camera;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class ModelShader extends ShaderProgram {
    public ModelShader() {
        super("vModelShader", "fModelShader");
    }

    /**
     * Setup the position attributes
     */
    @Override
    protected void bindAttributes() {
        this.bindAttribute(0, "position");
        this.bindAttribute(1, "texCords");
    }

    /**
     * Gets all the uniform locations
     */
    @Override
    protected void setAllUniformLocations() {
        this.setUniformLocation("modelMatrix");
        this.setUniformLocation("cameraMatrix");
    }

    /**
     * Sets the camera matrix
     *
     * @param camera
     */
    public void setCameraMatrix(Camera camera) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(-camera.getPosition().x, -camera.getPosition().y, 0f);
        matrix.setRotationXYZ((float) Math.toRadians(camera.getRotation().x), (float) Math.toRadians(camera.getRotation().y), (float) Math.toRadians(0f));
        this.setUniform("cameraMatrix", matrix);
    }

    /**
     * Sets the view matrix without having any camera/s object
     */
    public void setCameraMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.ortho2D(-Window.getWidth() / 2, Window.getWidth() / 2, -Window.getHeight() / 2, Window.getHeight() / 2);
        matrix.translate(0f, 0f, 0f);
        this.setUniform("cameraMatrix", matrix);
    }

    /**
     * Sets entity transformation matrix
     *
     * @param position
     * @param rotation
     * @param scale
     */
    public void setEntityMatrix(Vector2f position, Vector2f rotation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        if (position != null) matrix.setTranslation(position.x, position.y, 0f);
        if (rotation != null)
            matrix.setRotationXYZ((float) Math.toRadians(rotation.x), (float) Math.toRadians(rotation.y), (float) Math.toRadians(0f));
        matrix.scale(new Vector3f(scale.x / Window.getWidth(), scale.y / Window.getHeight(), 0f));
        this.setUniform("modelMatrix", matrix);
    }

}
