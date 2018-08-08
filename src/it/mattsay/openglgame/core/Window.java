package it.mattsay.openglgame.core;

import it.mattsay.openglgame.core.logging.AppLogger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

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
    private static int width;
    private static int height;

    /**
     * Initializes the window with some arguments
     *
     * @param name   The displayed name of the window
     * @param width  The horizontal size of the window
     * @param height The vertical size of the window
     */
    protected Window(String name, int width, int height) {
        this.name = name;
        Window.width = width;
        Window.height = height;
    }

    public String getName() {
        return name;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
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

    private static void sizeCallback(long window, int width, int height) {
        Window.width = width;
        Window.height = height;
        GL11.glViewport(0, 0, width, height);
        Application.LOGGER.debug("Window size change to " + width + "," + height, AppLogger.DebugType.WINDOW);
    }

    /**
     * Initializes the window
     */
    public void init() {

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_OPENGL_CORE_PROFILE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        windowId = glfwCreateWindow(width, height, this.name, MemoryUtil.NULL, MemoryUtil.NULL);

        if (windowId == MemoryUtil.NULL)
            throw new RuntimeException("Can't create the window");

        glfwSetWindowSizeCallback(windowId, Window::sizeCallback);
        glfwSetKeyCallback(windowId, Input::keyboardCallback);
        glfwSetMouseButtonCallback(windowId, Input::mouseButtonsCallback);
        glfwSetCursorPosCallback(windowId, Input::mousePosCallback);
        glfwSetScrollCallback(windowId, Input::mouseScrollCallback);

        glfwMakeContextCurrent(windowId);

        glfwSwapInterval(0);
    }

    public void show() {
        glfwShowWindow(windowId);
    }

    void swapBuffers() {
        glfwSwapBuffers(windowId);
    }

    public boolean isClosed() {
        return glfwWindowShouldClose(windowId);
    }

    protected void destroy() {
        glfwDestroyWindow(windowId);
    }

}
