package it.mattsay.openglgame.core.rendering.objects;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.logging.AppLogger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;


public class VAO extends GLObject {

    private EBO ebo;

    private ArrayList<VBO> vbos;

    public VAO() {
        this.vbos = new ArrayList<>();
    }

    protected ArrayList<VBO> getVbos() {
        return vbos;
    }

    protected VBO getVBO(int index) {
        return this.vbos.get(index);
    }


    /**
     * Returns the EBO
     */
    public EBO getEBO() {
        return this.ebo;
    }

    /**
     * Creates the OpenGL Object
     */
    @Override
    public void create() {
        setId(GL30.glGenVertexArrays());
    }

    /**
     * Binds the object to use it for OpenGL operations
     */
    @Override
    public void bind() {
        GL30.glBindVertexArray(getId());
    }

    /**
     * Store the vertices data to a vbo
     *
     * @param data
     */
    public void storeData(float[] data, int index, int size) {
        VBO vbo = new VBO();
        vbo.create();
        vbo.bind();

        FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(data.length);
        dataBuffer.put(data);
        dataBuffer.flip();

        vbo.setData(dataBuffer);

        this.vbos.add(vbo);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);

        vbo.unbind();

    }

    /**
     * Store the vertices data to a ebo
     *
     * @param indicesData
     */
    public void storeIndicesData(int[] indicesData) {
        EBO ebo = new EBO();
        ebo.create();
        ebo.bind();

        IntBuffer dataBuffer = BufferUtils.createIntBuffer(indicesData.length);
        dataBuffer.put(indicesData);
        dataBuffer.flip();

        ebo.setData(dataBuffer);

        this.ebo = ebo;

        ebo.unbind();
    }

    /**
     * Unbinds the object
     */
    @Override
    public void unbind() {
        GL30.glBindVertexArray(0);
    }

    /**
     * Deletes the object from the memory
     */
    @Override
    public void destroy() {
        GL30.glDeleteVertexArrays(getId());

        for (VBO v : this.vbos) {
            v.unbind();
            v.destroy();
        }
        ebo.unbind();
        ebo.destroy();
    }

    /**
     * Enables the VertexAttribArray at the given index
     *
     * @param index
     */
    public void enableVertexAttribArray(int index) {
        if (index < 0 || index > this.vbos.size() - 1) {
            Application.LOGGER.crash(
                    "Out of Bounds index:" + index +
                            " maximum index:" + (this.vbos.size() - 1) +
                            " mininum index: 0"
                    , AppLogger.ErrorType.OUT_OF_BOUNDS);
            return;
        }
        GL20.glEnableVertexAttribArray(index);
    }



    /**
     * Disables the VertexAttribArray at the given index
     *
     * @param index
     */
    public void disableVertexAttribArray(int index) {
        if (index < 0 || index > this.vbos.size() - 1) {
            Application.LOGGER.crash(
                    "Out of Bounds index:" + index +
                            " maximum index:" + (this.vbos.size() - 1) +
                            " mininum index: 0"
                    , AppLogger.ErrorType.OUT_OF_BOUNDS);
            return;
        }

        GL20.glDisableVertexAttribArray(index);
    }


}
