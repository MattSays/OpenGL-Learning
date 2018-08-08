package it.mattsay.openglgame.core.rendering.shaders;

import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.logging.AppLogger;
import it.mattsay.openglgame.core.rendering.objects.GLBufferObject;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public abstract class ShaderProgram extends GLBufferObject {

    private int vertexShader;
    private int fragmentShader;
    private Map<String, Integer> uniforms;
    private FloatBuffer matrixBuffer;

    /**
     * Loads the vertex shader and the fragment shader
     *
     * @param vertex
     * @param fragment
     */
    public ShaderProgram(String vertex, String fragment) {
        this.matrixBuffer = BufferUtils.createFloatBuffer(16);
        this.uniforms = new HashMap<String, Integer>();
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
        Application.LOGGER.debug("Creating shader (" + sourcePath + ")", AppLogger.DebugType.SHADER);
        int id = GL20.glCreateShader(type);
        Application.LOGGER.debug("Attaching shader source", AppLogger.DebugType.SHADER);
        GL20.glShaderSource(id, builder.toString());
        Application.LOGGER.debug("Compiling shader", AppLogger.DebugType.SHADER);
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
        Application.LOGGER.debug("Creating the program", AppLogger.DebugType.SHADER_PROGRAM);
        this.setId(GL20.glCreateProgram());
        this.setup();
    }


    /**
     * Attaches the shaders to the program
     */
    private void setup() {
        Application.LOGGER.debug("Attaching shaders", AppLogger.DebugType.SHADER_PROGRAM);
        GL20.glAttachShader(this.getId(), this.vertexShader);
        GL20.glAttachShader(this.getId(), this.fragmentShader);

        Application.LOGGER.debug("Binding attributes", AppLogger.DebugType.SHADER_PROGRAM);
        this.bindAttributes();

        Application.LOGGER.debug("Linking program", AppLogger.DebugType.SHADER_PROGRAM);
        GL20.glLinkProgram(this.getId());

        if (GL20.glGetProgrami(this.getId(), GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
            Application.LOGGER.err(GL20.glGetProgramInfoLog(this.getId(), 500).replace('\n', ' '), AppLogger.ErrorType.PROGRAM_LINK);

        Application.LOGGER.debug("Validating program", AppLogger.DebugType.SHADER_PROGRAM);
        GL20.glValidateProgram(this.getId());

        if (GL20.glGetProgrami(this.getId(), GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE)
            Application.LOGGER.err(GL20.glGetProgramInfoLog(this.getId(), 500).replace('\n', ' '), AppLogger.ErrorType.PROGRAM_VALIDATE);

        Application.LOGGER.debug("Loading the uniforms", AppLogger.DebugType.SHADER_PROGRAM);
        this.setAllUniformLocations();

        Application.LOGGER.debug("ShaderProgram has been setting up successfully", AppLogger.DebugType.SHADER_PROGRAM);
    }

    /**
     * Binds all the attributes
     */
    protected abstract void bindAttributes();

    /**
     * Gets all the uniform locations
     */
    protected abstract void setAllUniformLocations();

    /**
     * Binds the attribute
     *
     * @param attribute
     * @param name
     */
    protected void bindAttribute(int attribute, String name) {
        GL20.glBindAttribLocation(this.getId(), attribute, name);
    }

    private int getUnifrom(String name) {
        if (this.uniforms.containsKey(name)) {
            return this.uniforms.get(name);
        }
        return -1;
    }

    protected void setUniformLocation(String name) {
        this.uniforms.put(name, GL20.glGetUniformLocation(this.getId(), name));
    }

    public void setUniform(String name, float value) {
        if (getUnifrom(name) != -1) {
            GL20.glUniform1f(this.uniforms.get(name), value);
            return;
        }
        Application.LOGGER.err("Uniform not found", AppLogger.ErrorType.UNIFORM_NOT_FOUND);
    }

    public void setUniform(String name, Vector3f vector) {
        if (getUnifrom(name) != -1) {
            GL20.glUniform3f(this.uniforms.get(name), vector.x, vector.y, vector.z);
            return;
        }
        Application.LOGGER.err("Uniform not found", AppLogger.ErrorType.UNIFORM_NOT_FOUND);

    }

    public void setUniform(String name, boolean value) {
        if (getUnifrom(name) != -1) {
            GL20.glUniform1f(this.uniforms.get(name), value ? 1 : 0);
            return;
        }
        Application.LOGGER.err("Uniform not found", AppLogger.ErrorType.UNIFORM_NOT_FOUND);
    }

    public void setUniform(String name, Matrix4f matrix) {
        if (getUnifrom(name) != -1) {
            matrix.get(matrixBuffer);
            GL20.glUniformMatrix4fv(this.uniforms.get(name), false, matrixBuffer);
            return;
        }
        Application.LOGGER.err("Uniform not found", AppLogger.ErrorType.UNIFORM_NOT_FOUND);
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
        Application.LOGGER.debug("Destroying the program and the shaders", AppLogger.DebugType.SHADER_PROGRAM);
        this.unbind();

        GL20.glDetachShader(this.getId(), this.fragmentShader);
        GL20.glDetachShader(this.getId(), this.vertexShader);

        GL20.glDeleteProgram(this.getId());
        GL20.glDeleteShader(this.fragmentShader);
        GL20.glDeleteShader(this.vertexShader);
    }
}
