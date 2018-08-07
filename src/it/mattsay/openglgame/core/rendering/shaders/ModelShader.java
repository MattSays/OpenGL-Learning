package it.mattsay.openglgame.core.rendering.shaders;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.Window;
import it.mattsay.openglgame.core.entities.Camera;
import org.joml.Matrix4f;
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
        this.setUniformLocation("projectionMatrix");
        this.setUniformLocation("transformationMatrix");
        this.setUniformLocation("viewMatrix");
    }

    /**
     * Sets the projection matrix
     */
    public void setProjectionMatrix() {
        float aspectRatio = (float) Window.getWidth() / (float) Window.getHeight();
        float yScale = (float) ((1f / Math.tan(Math.toRadians(Application.FOV / 2f) * aspectRatio)));
        float xScale = yScale / aspectRatio;
        float frustumLenght = Application.FAR_PLANE - Application.NEAR_PLANE;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.m00(xScale);
        projectionMatrix.m11(yScale);
        projectionMatrix.m22(-((Application.FAR_PLANE + Application.NEAR_PLANE) / frustumLenght));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * Application.NEAR_PLANE * Application.FAR_PLANE) / frustumLenght));
        projectionMatrix.m33(0);
        this.setUniform("projectionMatrix", projectionMatrix);
    }

    /**
     * Sets the camera view matrix
     *
     * @param camera
     */
    public void setCameraMatrix(Camera camera) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
        matrix.setRotationXYZ((float) Math.toRadians(camera.getRotation().x), (float) Math.toRadians(camera.getRotation().y), (float) Math.toRadians(camera.getRotation().z));
        this.setUniform("viewMatrix", matrix);
    }

    /**
     * Sets the view matrix without having any camera/s object
     */
    public void setCameraMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.translate(0f, 0f, 0f);
        this.setUniform("viewMatrix", matrix);
    }

    /**
     * Sets entity transformation matrix
     *
     * @param position
     * @param rotation
     * @param scale
     */
    public void setEntityMatrix(Vector3f position, Vector3f rotation, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        if (position != null) matrix.setTranslation(position);
        if (rotation != null)
            matrix.setRotationXYZ((float) Math.toRadians(rotation.x), (float) Math.toRadians(rotation.y), (float) Math.toRadians(rotation.z));
        matrix.scale(scale);
        this.setUniform("transformationMatrix", matrix);
    }

}
