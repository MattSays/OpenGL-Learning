package it.mattsay.openglgame.game;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.rendering.models.TexturedModel;

public class Game extends Application {

    private StaticShader shader;

    float[] vertices = {
            -0.5f, 0.5f, 0f,//v0
            -0.5f, -0.5f, 0f,//v1
            0.5f, -0.5f, 0f,//v2
            0.5f, 0.5f, 0f,//v3
    };

    int[] indices = {
            0, 1, 3,//top left triangle (v0, v1, v3)
            3, 1, 2//bottom right triangle (v3, v1, v2)
    };

    float[] texCords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0
    };


    TexturedModel model;

    /**
     * Just setting some window properties
     */
    public Game() {
        super("Game", 800, 600);
        Application.LOGGER.setDebug(true);
    }


    /**
     * This method is used for initialize in game objects
     */
    @Override
    protected void init() {

        this.shader = new StaticShader();
        this.shader.create();
        this.model = new TexturedModel(this.vertices, this.indices, this.texCords, "image.png");
        this.model.init();
    }

    /**
     * This method is used for render in game objects
     */
    @Override
    protected void render() {

        this.getWindow().setName("Game - " + this.getFPS());

        getRenderer().begin();

        this.shader.bind();
        getRenderer().render(this.model);
        this.shader.unbind();

        getRenderer().end();
    }

    /**
     * This method is used for dispose in game objects
     */
    @Override
    protected void dispose() {
        this.model.destroy();
        this.shader.destroy();
    }
}
