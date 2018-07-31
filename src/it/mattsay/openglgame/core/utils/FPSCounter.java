package it.mattsay.openglgame.core.utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class FPSCounter {

    private int frameCount;
    private int fps;
    private double previousTime;

    public FPSCounter() {
        this.fps = 0;
        this.frameCount = 0;
        this.previousTime = glfwGetTime();
    }

    public int getFPS() {
        return fps;
    }

    /**
     * Sets the current frame count every second
     */
    public void update() {
        double currentTime = glfwGetTime();
        frameCount++;

        if (currentTime - previousTime >= 1.0) {

            fps = frameCount;

            frameCount = 0;
            previousTime = currentTime;
        }
    }

}
