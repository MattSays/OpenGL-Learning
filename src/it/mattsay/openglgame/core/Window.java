package it.mattsay.openglgame.core;

import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;

public class Window {

    /**
     * The id that identifies the window for OpenGL operations
     */
    private long windowId;

    /**
     * Window's properties
     */
    private String name;
    private int width;
    private int height;

    /**
     * Initializes the window with some arguments
     *
     * @param name   The displayed name of the window
     * @param width  The horizontal size of the window
     * @param height The vertical size of the window
     */
    protected Window(String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        glfwSetWindowTitle(this.windowId, name);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected void init() {

        glfwDefaultWindowHints();
        // glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE); Use this for debugging
        glfwWindowHint(GLFW_OPENGL_CORE_PROFILE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        this.windowId = glfwCreateWindow(this.width, this.height, this.name, MemoryUtil.NULL, MemoryUtil.NULL);

        if (this.windowId == MemoryUtil.NULL)
            throw new RuntimeException("Can't create the window");

        glfwMakeContextCurrent(this.windowId);

        glfwSwapInterval(0);

        glfwShowWindow(this.windowId);
    }

    protected void swapBuffers() {
        glfwSwapBuffers(this.windowId);
    }

    protected boolean isClosed() {
        return glfwWindowShouldClose(this.windowId);
    }

    protected void destroy() {
        glfwDestroyWindow(this.windowId);
    }

}
