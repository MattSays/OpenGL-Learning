package it.mattsay.openglgame.core;

import it.mattsay.openglgame.core.logging.AppLogger;
import it.mattsay.openglgame.core.utils.FPSCounter;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Application {

    public static final AppLogger LOGGER = new AppLogger();
    public static final float FOV = 70f, NEAR_PLANE = 0.1f, FAR_PLANE = 1000f;

    private static boolean APP_EXIT;
    private FPSCounter counter;
    private Window window;


    /**
     * This is the main core of the application
     *
     * @param name
     * @param width
     * @param height
     */
    public Application(String name, int width, int height) {
        this.window = new Window(name, width, height);
        this.counter = new FPSCounter();
        APP_EXIT = false;
    }

    public Window getWindow() {
        return window;
    }

    protected int getFPS() {
        return counter.getFPS();
    }

    /**
     * Exit from the application everywhere
     */
    public static void exit() {
        APP_EXIT = true;
    }

    /**
     * Use this method for starting the application
     */
    public void run() {
        LOGGER.info("Starting...");
        this.glInit();
        this.loop();
        this.end();
        LOGGER.info("Stopping...");
    }

    /**
     * Initializes all OpenGL's stuff
     */
    private void glInit() {

        if (!glfwInit())
            throw new IllegalStateException("Can't initialize GLFW");

        window.init();

        glfwSetErrorCallback((int error, long description) -> LOGGER.glfwError(error, description));

        GL.createCapabilities();
        //GLUtil.setupDebugMessageCallback(); Use this for debugging

        GL11.glViewport(0, 0, Window.getWidth(), Window.getHeight());

        this.init();

        window.show();

        LOGGER.info("Application started (" + this.window.getName() + ", " + Window.getWidth() + ", " + Window.getHeight() + ")");
    }

    /**
     * The main loop where all things are rendered
     */
    private void loop() {
        while (!this.window.isClosed() && !APP_EXIT) {
            this.render();

            glfwPollEvents();

            this.counter.update();

            this.window.swapBuffers();
        }
    }

    /**
     * Used for free all objects previously initialized
     */
    private void end() {
        this.window.destroy();
        this.dispose();
    }

    /**
     * This method is used for initialize in game objects
     */
    protected abstract void init();

    /**
     * This method is used for render in game objects
     */
    protected abstract void render();

    /**
     * This method is used for dispose in game objects
     */
    protected abstract void dispose();

}
