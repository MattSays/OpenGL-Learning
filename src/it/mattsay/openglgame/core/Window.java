package it.mattsay.openglgame.core;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Window {

    /**
     * The id that identifies the window for OpenGL operations
     */
    private static long windowId;

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

    public static int getWidth() {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(windowId, buffer, null);
        return buffer.get(0);
    }

    public static int getHeight() {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(windowId, null, buffer);
        return buffer.get(0);
    }

    public void setName(String name) {
        glfwSetWindowTitle(windowId, name);
    }

    public int getInitialWidth() {
        return width;
    }

    public int getInitiaHeight() {
        return height;
    }

    protected void init() {

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_OPENGL_CORE_PROFILE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        windowId = glfwCreateWindow(this.width, this.height, this.name, MemoryUtil.NULL, MemoryUtil.NULL);

        if (windowId == MemoryUtil.NULL)
            throw new RuntimeException("Can't create the window");

        glfwMakeContextCurrent(windowId);

        glfwSwapInterval(0);
    }

    protected void show() {
        glfwShowWindow(windowId);
    }

    protected void swapBuffers() {
        glfwSwapBuffers(windowId);
    }

    protected boolean isClosed() {
        return glfwWindowShouldClose(windowId);
    }

    protected void destroy() {
        glfwDestroyWindow(windowId);
    }

}
