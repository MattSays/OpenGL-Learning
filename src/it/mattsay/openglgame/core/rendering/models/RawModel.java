package it.mattsay.openglgame.core.rendering.models;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.logging.AppLogger;
import it.mattsay.openglgame.core.rendering.objects.VAO;

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
        Application.LOGGER.debug("Creating and setting up VAO", AppLogger.DebugType.VAO);
        this.vao.create();

        this.vao.bind();
        Application.LOGGER.debug("Storing vertices data", AppLogger.DebugType.VAO);
        this.vao.storeData(this.vertices, 0, 3);
        Application.LOGGER.debug("Storing indices data", AppLogger.DebugType.VAO);
        this.vao.storeIndicesData(this.indices);
        this.vao.unbind();

        Application.LOGGER.debug("VAO has been setting up successfully", AppLogger.DebugType.VAO);
    }

    public int getVerticesCount() {
        return this.verticesCount;
    }

    public VAO getVAO() {
        return this.vao;
    }

    /**
     * Destroys the model
     */
    public void destroy() {
        Application.LOGGER.debug("Destroying VAO", AppLogger.DebugType.VAO);
        this.vao.destroy();
    }

}
