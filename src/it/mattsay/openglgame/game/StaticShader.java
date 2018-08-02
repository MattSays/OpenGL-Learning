package it.mattsay.openglgame.game;

import it.mattsay.openglgame.core.rendering.ShaderProgram;

public class StaticShader extends ShaderProgram {
    public StaticShader() {
        super("vertexShader", "fragmentShader");
    }

    /**
     * Setup the position attribute
     */
    @Override
    protected void bindAttributes() {
        this.bindAttribute(0, "position");
    }
}
