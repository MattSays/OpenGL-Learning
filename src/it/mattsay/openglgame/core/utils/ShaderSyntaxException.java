package it.mattsay.openglgame.core.utils;

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
