package it.mattsay.openglgame.core.rendering.models;

import org.lwjgl.opengl.GL15;

import java.nio.Buffer;
import java.nio.FloatBuffer;

public class VBO extends GLObject {

    /**
     * Creates the OpenGL Object
     */
    @Override
    protected void create() {
        setId(GL15.glGenBuffers());
    }

    /**
     * Binds the object to use it for OpenGL operations
     */
    @Override
    protected void bind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, getId());
    }

    /**
     * Unbinds the object
     */
    @Override
    protected void unbind() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Deletes the object from the memory
     */
    @Override
    protected void destroy() {
        GL15.glDeleteBuffers(getId());
    }

    /**
     * Adds the given data to the VBO
     *
     * @param buffer
     */
    protected void setData(Buffer buffer) {
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, (FloatBuffer) buffer, GL15.GL_STATIC_DRAW);
    }
}
