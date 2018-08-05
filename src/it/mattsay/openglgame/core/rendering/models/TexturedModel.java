package it.mattsay.openglgame.core.rendering.models;

import it.mattsay.openglgame.core.rendering.Texture;

public class TexturedModel extends RawModel {

    private float[] textureCordinates;
    private Texture texture;


    /**
     * Creates a texture model with the given vertex and indices
     *
     * @param vertices
     * @param indices
     * @param textureCordinates
     */
    public TexturedModel(float[] vertices, int[] indices, float[] textureCordinates, String texturePath) {
        super(vertices, indices);
        this.textureCordinates = textureCordinates;
        this.texture = new Texture(texturePath);
    }

    public Texture getTexture() {
        return texture;
    }

    /**
     * Initializes the model and creates the texture
     */
    @Override
    public void init() {
        super.init();

        this.getVAO().bind();
        this.getVAO().storeData(this.textureCordinates, 1, 2);
        this.getVAO().unbind();

        this.texture.create();
    }

    /**
     * Destroys the model
     */
    @Override
    public void destroy() {
        super.destroy();
        this.texture.destroy();
    }
}
