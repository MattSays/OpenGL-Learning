package it.mattsay.openglgame.game;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.rendering.Camera;
import it.mattsay.openglgame.core.rendering.Texture;
import it.mattsay.openglgame.core.rendering.sprites.Sprite;
import it.mattsay.openglgame.core.rendering.sprites.SpriteRenderer;
import org.lwjgl.opengl.GL11;

public class Game extends Application {

    private SpriteRenderer renderer;
    private Sprite sprite;

    private Camera camera;

    /**
     * Just setting some window properties
     */
    public Game() {
        super("Game", 1280, 720);
        Application.LOGGER.setDebug(true);
    }


    /**
     * This method is used for initialize in game objects
     */
    @Override
    protected void init() {
        this.camera = new Camera();
        this.renderer = new SpriteRenderer();
        this.renderer.setCamera(camera);

        this.sprite = new Sprite(new Texture("image.png"));
        this.sprite.init();

    }

    /**
     * This method is used for render in game objects
     */
    @Override
    protected void render() {
        this.getWindow().setName("Game - " + this.getFPS());

        GL11.glClearColor(0, 1, 0, 1);

        renderer.begin();
        sprite.render(renderer);
        renderer.end();
    }

    /**
     * This method is used for dispose in game objects
     */
    @Override
    protected void dispose() {
        this.renderer.destroy();
        this.sprite.destroy();
    }
}
