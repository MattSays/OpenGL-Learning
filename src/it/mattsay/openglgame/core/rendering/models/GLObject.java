package it.mattsay.openglgame.core.rendering.models;

public abstract class GLObject {

    private int id;

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    /**
     * Creates the OpenGL Object
     */
    protected abstract void create();

    /**
     * Binds the object to use it for OpenGL operations
     */
    protected abstract void bind();

    /**
     * Unbinds the object
     */
    protected abstract void unbind();

    /**
     * Deletes the object from the memory
     */
    protected abstract void destroy();
}
