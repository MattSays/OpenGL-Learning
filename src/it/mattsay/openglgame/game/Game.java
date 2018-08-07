package it.mattsay.openglgame.game;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.entities.Camera;
import it.mattsay.openglgame.core.entities.Entity;
import it.mattsay.openglgame.core.rendering.models.TexturedModel;
import org.joml.Vector3f;

public class Game extends Application {

    float[] vertices = {
            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,

            -0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, 0.5f,

            -0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, 0.5f

    };

    float[] textureCoords = {
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0,
            0, 0,
            0, 1,
            1, 1,
            1, 0


    };

    int[] indices = {
            0, 1, 3,
            3, 1, 2,
            4, 5, 7,
            7, 5, 6,
            8, 9, 11,
            11, 9, 10,
            12, 13, 15,
            15, 13, 14,
            16, 17, 19,
            19, 17, 18,
            20, 21, 23,
            23, 21, 22

    };

    private Entity entity;

    private Camera camera;

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
        this.camera = new Camera();
        this.getRenderer().setCamera(camera);
        this.entity = new Entity(new TexturedModel(vertices, indices, textureCoords, "image_inverted.png"), new Vector3f(0f, 0f, -0.7f));
        this.entity.init();
        this.entity.scale(-0.7f);

    }

    /**
     * This method is used for render in game objects
     */
    @Override
    protected void render() {
        this.getWindow().setName("Game - " + this.getFPS());
        //this.camera.translate(-0.001f, 0f ,0f);
        this.entity.rotate(0.1f, 0.1f, 0.1f);


        getRenderer().begin();
        getRenderer().render(this.entity);
        getRenderer().end();
    }

    /**
     * This method is used for dispose in game objects
     */
    @Override
    protected void dispose() {
        this.entity.destroy();
    }
}
