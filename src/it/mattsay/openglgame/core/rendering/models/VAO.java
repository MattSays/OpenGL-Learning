package it.mattsay.openglgame.core.rendering.models;

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

    private ArrayList<VBO> vbos;

    protected VAO() {
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
    protected EBO getEBO() {
        for (VBO v : this.vbos) {
            if (v instanceof EBO) {
                return (EBO) v;
            }
        }
        return null;
    }

    /**
     * Creates the OpenGL Object
     */
    @Override
    protected void create() {
        setId(GL30.glGenVertexArrays());
    }

    /**
     * Binds the object to use it for OpenGL operations
     */
    @Override
    protected void bind() {
        GL30.glBindVertexArray(getId());
    }

    /**
     * Store the vertices data to a vbo
     *
     * @param verticesData
     */
    protected void storeVerticesData(float[] verticesData) {
        VBO vbo = new VBO();
        vbo.create();
        vbo.bind();

        FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(verticesData.length);
        dataBuffer.put(verticesData);
        dataBuffer.flip();

        vbo.setData(dataBuffer);

        this.vbos.add(vbo);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);

        vbo.unbind();

    }

    /**
     * Store the vertices data to a ebo
     *
     * @param indicesData
     */
    protected void storeIndicesData(int[] indicesData) {
        EBO ebo = new EBO();
        ebo.create();
        ebo.bind();

        IntBuffer dataBuffer = BufferUtils.createIntBuffer(indicesData.length);
        dataBuffer.put(indicesData);
        dataBuffer.flip();

        ebo.setData(dataBuffer);

        this.vbos.add(ebo);

        ebo.unbind();
    }

    /**
     * Unbinds the object
     */
    @Override
    protected void unbind() {
        GL30.glBindVertexArray(0);
    }

    /**
     * Deletes the object from the memory
     */
    @Override
    protected void destroy() {
        GL30.glDeleteVertexArrays(getId());

        for (VBO v : this.vbos) {
            v.unbind();
            v.destroy();
        }
    }

    /**
     * Enables the VertexAttribArray at the given index
     *
     * @param index
     */
    protected void enableVertexAttribArray(int index) {
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
     * Enables all the VertexAttribArrays
     */
    protected void enableAllVertexAttribArrays() {
        for (VBO vbo : this.vbos) {
            this.enableVertexAttribArray(this.vbos.indexOf(vbo));
        }
    }

    /**
     * Disables the VertexAttribArray at the given index
     *
     * @param index
     */
    protected void disableVertexAttribArray(int index) {
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

    /**
     * Disables all the VertexAttribArrays
     */
    protected void disableAllVertexAttribArrays() {
        for (VBO vbo : this.vbos) {
            this.disableVertexAttribArray(this.vbos.indexOf(vbo));
        }
    }
}
