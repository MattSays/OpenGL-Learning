package it.mattsay.openglgame.core.rendering.sprites;

import it.mattsay.openglgame.core.rendering.Camera;
import it.mattsay.openglgame.core.rendering.Renderer;
import it.mattsay.openglgame.core.rendering.Texture;
import it.mattsay.openglgame.core.rendering.models.TexturedModel;
import it.mattsay.openglgame.core.rendering.shaders.ModelShader;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class SpriteRenderer extends Renderer {

    private float[] modelVertices = {
            -0.5f, 0.5f, 0f,//v0
            -0.5f, -0.5f, 0f,//v1
            0.5f, -0.5f, 0f,//v2
            0.5f, 0.5f, 0f,//v3
    };
    private int[] modelIndices = {
            0, 1, 3,
            3, 1, 2
    };
    private float[] modelTextureCordinates = {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };

    private TexturedModel model;

    private ModelShader shader;

    private Camera camera;


    public SpriteRenderer() {
        this.model = new TexturedModel(modelVertices, modelIndices, modelTextureCordinates);
        this.model.init();
        this.shader = new ModelShader();
        this.shader.create();
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Starts the rendering process
     */
    public void begin() {
        this.begin(this.model);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.model.getVAO().enableVertexAttribArray(1);

        this.shader.bind();
        if (camera != null) this.shader.setCameraMatrix(camera);
        else this.shader.setCameraMatrix();
    }

    /**
     * Renders the texture at the given position with the given rotation and scale
     *
     * @param texture
     * @param x
     * @param y
     * @param xRot
     * @param yRot
     * @param width
     * @param height
     */
    public void render(Texture texture, float x, float y, float xRot, float yRot, float width, float height) {
        this.shader.setEntityMatrix(new Vector2f(x, y), new Vector2f(xRot, yRot), new Vector2f(width, height));

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bind();
        this.render(this.model);
        texture.unbind();
    }

    /**
     * Renders the texture at the given position with the given rotation
     *
     * @param texture
     * @param x
     * @param y
     * @param xRot
     * @param yRot
     */
    public void render(Texture texture, float x, float y, float xRot, float yRot) {
        this.render(texture, x, y, xRot, yRot, texture.getWidth(), texture.getHeight());
    }

    /**
     * Renders the texture at the given position
     *
     * @param texture
     * @param x
     * @param y
     */
    public void render(Texture texture, float x, float y) {
        this.render(texture, x, y, 0f, 0f);
    }

    /**
     * Renders the texture
     *
     * @param texture
     */
    public void render(Texture texture) {
        this.render(texture, 0, 0, 0, 0, texture.getWidth(), texture.getHeight());
    }


    /**
     * Stops the rendering process
     */
    public void end() {
        this.end(this.model);
        this.model.getVAO().disableVertexAttribArray(1);
        GL11.glDisable(GL11.GL_BLEND);
        this.shader.unbind();
    }

    /**
     * Destroys the renderer
     */
    @Override
    public void destroy() {
        super.destroy();
        this.model.destroy();
        this.shader.destroy();
    }

}
