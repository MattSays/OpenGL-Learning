package it.mattsay.openglgame.core.rendering.shaders;

public class ShaderSyntaxException extends Exception {

    private String description;

    public ShaderSyntaxException(String description) {
        this.description = description;
    }

    @Override
    public String getMessage() {
        return "Error: " + description;
    }
}
