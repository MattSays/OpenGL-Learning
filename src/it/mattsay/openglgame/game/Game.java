package it.mattsay.openglgame.game;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.rendering.models.RawModel;

public class Game extends Application {


    float[] vertices = {
            // Left bottom triangle
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            // Right top triangle
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f,
            -0.5f, 0.5f, 0f
    };
    RawModel model;

    /**
     * Just setting some window properties
     */
    public Game() {
        super("Game", 800, 600);
    }


    /**
     * This method is used for initialize in game objects
     */
    @Override
    protected void init() {
        this.model = new RawModel(this.vertices);
        this.model.init();
    }

    /**
     * This method is used for render in game objects
     */
    @Override
    protected void render() {

        this.getWindow().setName("Game - " + this.getFPS());

        getRenderer().begin();

        getRenderer().render(this.model);
    }

    /**
     * This method is used for dispose in game objects
     */
    @Override
    protected void dispose() {
        this.model.destroy();
    }
}
