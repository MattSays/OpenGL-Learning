package it.mattsay.openglgame.core;

import it.mattsay.openglgame.core.logging.AppLogger;
import it.mattsay.openglgame.core.rendering.models.ModelRenderer;
import it.mattsay.openglgame.core.utils.FPSCounter;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Application {

    private static boolean APP_EXIT;
    public static final AppLogger LOGGER = new AppLogger();
    private FPSCounter counter;
    private Window window;
    private ModelRenderer renderer;

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
        this.renderer = new ModelRenderer();
        APP_EXIT = false;
    }

    public Window getWindow() {
        return window;
    }

    public ModelRenderer getRenderer() {
        return renderer;
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

        this.init();

        LOGGER.info("Application started (" + this.window.getName() + ", " + this.window.getWidth() + ", " + this.window.getHeight() + ")");
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
