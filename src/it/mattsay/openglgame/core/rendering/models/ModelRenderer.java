package it.mattsay.openglgame.core.rendering.models;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class ModelRenderer {

    /**
     * Setups the OpenGL scene
     */
    public void begin() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClearColor(0, 1, 0, 1);

    }

    /**
     * Renders the model with glDrawElements
     *
     * @param model
     */
    public void render(RawModel model) {
        model.getVAO().bind();
        model.getVAO().enableVertexAttribArray(0);

        //IMPORTANT: Bind the EBO before call glDrawElements
        model.getVAO().getEBO().bind();
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
        model.getVAO().getEBO().unbind();

        model.getVAO().disableVertexAttribArray(0);
        model.getVAO().unbind();
    }

    /**
     * Renders the model with its textures
     *
     * @param model
     */
    public void render(TexturedModel model) {
        model.getVAO().bind();
        model.getVAO().enableVertexAttribArray(0);
        model.getVAO().enableVertexAttribArray(1);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        model.getTexture().bind();

        //IMPORTANT: Bind the EBO before call glDrawElements
        model.getVAO().getEBO().bind();
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVerticesCount(), GL11.GL_UNSIGNED_INT, 0);
        model.getVAO().getEBO().unbind();

        model.getTexture().unbind();

        model.getVAO().disableVertexAttribArray(1);
        model.getVAO().disableVertexAttribArray(0);
        model.getVAO().unbind();

    }

    public void end() {

    }

}
