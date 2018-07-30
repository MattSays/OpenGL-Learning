package it.mattsay.openglgame.core;

import it.mattsay.openglgame.core.logging.AppLogger;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Application {

    private static boolean APP_EXIT;
    private AppLogger logger;
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
        this.logger = new AppLogger();
        APP_EXIT = false;
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
        logger.info("Starting..");
        this.glInit();
        this.loop();
        this.end();
        logger.info("Stopping...");
    }

    /**
     * Initializes all OpenGL's stuff
     */
    private void glInit() {

        if (!glfwInit())
            throw new IllegalStateException("Can't initialize GLFW");

        window.init();

        glfwSetErrorCallback((int error, long description) -> logger.openglError(error, description));

        GL.createCapabilities();

        this.init();

        logger.info("Application started (" + this.window.getName() + ", " + this.window.getWidth() + ", " + this.window.getHeight() + ")");
    }

    /**
     * The main loop where all things are rendered
     */
    private void loop() {
        while (!this.window.isClosed() && !APP_EXIT) {
            this.render();

            glfwPollEvents();

            this.window.swapBuffers();
        }
    }

    /**
     * Used for free all objects previously initialized
     */
    private void end() {
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
