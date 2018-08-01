package it.mattsay.openglgame.game;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.rendering.models.RawModel;

public class Game extends Application {


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


    RawModel indicesModel;

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
        this.indicesModel = new RawModel(this.vertices, this.indices);
        this.indicesModel.init();
    }

    /**
     * This method is used for render in game objects
     */
    @Override
    protected void render() {

        this.getWindow().setName("Game - " + this.getFPS());

        getRenderer().begin();

        getRenderer().render(this.indicesModel);
    }

    /**
     * This method is used for dispose in game objects
     */
    @Override
    protected void dispose() {
        this.indicesModel.destroy();
    }
}
