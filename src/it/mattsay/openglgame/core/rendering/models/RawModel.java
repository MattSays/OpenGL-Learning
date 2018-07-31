package it.mattsay.openglgame.core.rendering.models;

public class RawModel {

    private VAO vao;
    private int verticesCount;
    private float[] positions;

    public RawModel(float[] positions) {
        this.positions = positions;
        this.vao = new VAO();
        this.verticesCount = positions.length / 3;
    }

    /**
     * Initializes the model with a vao setup
     */
    public void init() {
        this.vao.create();

        this.vao.bind();
        this.vao.storeData(this.positions);
        this.vao.unbind();
    }

    protected int getVerticesCount() {
        return this.verticesCount;
    }

    protected VAO getVAO() {
        return this.vao;
    }

    /**
     * Destroys the model
     */
    public void destroy() {
        this.vao.destroy();
    }

}
