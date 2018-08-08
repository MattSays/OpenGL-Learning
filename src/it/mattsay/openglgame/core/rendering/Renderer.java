package it.mattsay.openglgame.core.rendering;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.rendering.models.RawModel;
import org.lwjgl.opengl.GL11;

public class Renderer {

    private boolean rendering = false;

    /**
     * Starts the rendering process
     */
    public void begin(RawModel model) {
        this.rendering = true;
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        model.getVAO().bind();
        model.getVAO().enableVertexAttribArray(0);
    }

    /**
     * Renders the object
     *
     * @param model
     */
    public void render(RawModel model) {
        if (!rendering)
            Application.LOGGER.crash(this.getClass().getSimpleName() + ".begin() must be called", "Rendering");
        //IMPORTANT: Bind the EBO before call glDrawElements
        model.getVAO().getEBO().bind();
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
        model.getVAO().getEBO().unbind();
    }

    /**
     * Stops the rendering process
     *
     * @param model
     */
    public void end(RawModel model) {
        this.rendering = false;
        model.getVAO().disableVertexAttribArray(0);
        model.getVAO().unbind();
    }

    /**
     * Destroys the renderer
     */
    public void destroy() {
        if (rendering) Application.LOGGER.crash(this.getClass().getSimpleName() + ".end() must be called", "Rendering");
    }
}
