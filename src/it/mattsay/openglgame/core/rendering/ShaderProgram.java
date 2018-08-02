package it.mattsay.openglgame.core.rendering;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.logging.AppLogger;
import it.mattsay.openglgame.core.rendering.models.GLObject;
import it.mattsay.openglgame.core.utils.ShaderSyntaxException;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class ShaderProgram extends GLObject {

    private int vertexShader;
    private int fragmentShader;

    /**
     * Loads the vertex shader and the fragment shader
     *
     * @param vertex
     * @param fragment
     */
    public ShaderProgram(String vertex, String fragment) {
        try {
            this.vertexShader = loadShader("resources/shaders/" + vertex + ".glsl", GL20.GL_VERTEX_SHADER);
            this.fragmentShader = loadShader("resources/shaders/" + fragment + ".glsl", GL20.GL_FRAGMENT_SHADER);
        } catch (IOException e) {
            Application.LOGGER.err("Can't read the file", AppLogger.ErrorType.IO);
        } catch (ShaderSyntaxException e) {
            Application.LOGGER.err(e.getMessage().replace('\n', ' '), AppLogger.ErrorType.SHADER_SYNTAX);
        }
    }

    /**
     * Loads the shader with the given path
     *
     * @param sourcePath
     * @param type       GL_VERTEX_SHADER or GL_FRAGMENT_SHADER
     * @return the shader id
     * @throws IOException           if the path does not exist
     * @throws ShaderSyntaxException if the syntax of the shader is incorrect
     */
    private int loadShader(String sourcePath, int type) throws IOException, ShaderSyntaxException {
        StringBuilder builder = new StringBuilder();
        Files.readAllLines(new File(sourcePath).toPath()).forEach(line -> builder.append(line + "\n"));
        int id = GL20.glCreateShader(type);

        GL20.glShaderSource(id, builder.toString());
        GL20.glCompileShader(id);

        if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new ShaderSyntaxException(GL20.glGetShaderInfoLog(id, 512));
        }
        return id;
    }


    /**
     * Creates the OpenGL Object
     */
    @Override
    public void create() {
        this.setId(GL20.glCreateProgram());
    }

    /**
     * Attaches the shaders to the program
     */
    public void setup() {
        GL20.glAttachShader(this.getId(), this.vertexShader);
        GL20.glAttachShader(this.getId(), this.fragmentShader);

        this.bindAttributes();

        GL20.glLinkProgram(this.getId());

        if (GL20.glGetProgrami(this.getId(), GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
            Application.LOGGER.err(GL20.glGetProgramInfoLog(this.getId(), 500).replace('\n', ' '), AppLogger.ErrorType.PROGRAM_LINK);

        GL20.glValidateProgram(this.getId());

        if (GL20.glGetProgrami(this.getId(), GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE)
            Application.LOGGER.err(GL20.glGetProgramInfoLog(this.getId(), 500).replace('\n', ' '), AppLogger.ErrorType.VALIDATE);

        Application.LOGGER.info("Shader has been setting up successfully");
    }

    /**
     * Binds all the attributes
     */
    protected abstract void bindAttributes();

    /**
     * Binds the attribute
     *
     * @param attribute
     * @param name
     */
    protected void bindAttribute(int attribute, String name) {
        GL20.glBindAttribLocation(this.getId(), attribute, name);
    }

    /**
     * Binds the object to use it for OpenGL operations
     */
    @Override
    public void bind() {
        GL20.glUseProgram(this.getId());
    }

    /**
     * Unbinds the object
     */
    @Override
    public void unbind() {
        GL20.glUseProgram(0);
    }

    /**
     * Deletes the object from the memory
     */
    @Override
    public void destroy() {
        this.unbind();

        GL20.glDetachShader(this.getId(), this.fragmentShader);
        GL20.glDetachShader(this.getId(), this.vertexShader);

        GL20.glDeleteProgram(this.getId());
        GL20.glDeleteShader(this.fragmentShader);
        GL20.glDeleteShader(this.vertexShader);
    }
}
