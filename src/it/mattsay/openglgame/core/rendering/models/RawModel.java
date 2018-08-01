package it.mattsay.openglgame.core.rendering.models;

public class RawModel {

    private VAO vao;
    private int verticesCount;
    private float[] vertices;
    private int[] indices;

    /**
     * Creates a model with the given vertex and indices
     *
     * @param vertices
     * @param indices
     */
    public RawModel(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.vao = new VAO();
        this.indices = indices;
        this.verticesCount = indices.length;
    }

    /**
     * Initializes the model with a vao setup
     */
    public void init() {
        this.vao.create();

        this.vao.bind();
        this.vao.storeVerticesData(this.vertices);
        this.vao.storeIndicesData(this.indices);
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
