package it.mattsay.openglgame.core.rendering.models;

import org.lwjgl.opengl.GL15;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class EBO extends VBO {

    /**
     * Binds the object to use it for OpenGL operations
     */
    @Override
    protected void bind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.getId());
    }

    /**
     * Unbinds the object
     */
    @Override
    protected void unbind() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * Adds the given data to the VBO
     *
     * @param buffer
     */
    @Override
    protected void setData(Buffer buffer) {
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, (IntBuffer) buffer, GL15.GL_STATIC_DRAW);
    }

}
