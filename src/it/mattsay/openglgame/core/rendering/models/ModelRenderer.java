package it.mattsay.openglgame.core.rendering.models;

import org.lwjgl.opengl.GL11;

public class ModelRenderer {

    /**
     * Setups the OpenGL scene
     */
    public void begin() {
        GL11.glClearColor(1, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Renders the model with glDrawArrays
     *
     * @param model
     */
    public void render(RawModel model) {
        model.getVAO().bind();
        model.getVAO().enableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVerticesCount());
        model.getVAO().disableVertexAttribArray(0);
        model.getVAO().unbind();
    }

    public void end() {

    }

}
