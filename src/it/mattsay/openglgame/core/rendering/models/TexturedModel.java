package it.mattsay.openglgame.core.rendering.models;

public class TexturedModel extends RawModel {

    private float[] textureCordinates;


    /**
     * Creates a texture model with the given vertex and indices
     *
     * @param vertices
     * @param indices
     * @param textureCordinates
     */
    public TexturedModel(float[] vertices, int[] indices, float[] textureCordinates) {
        super(vertices, indices);
        this.textureCordinates = textureCordinates;
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
    }

    /**
     * Destroys the model
     */
    @Override
    public void destroy() {
        super.destroy();
    }
}
