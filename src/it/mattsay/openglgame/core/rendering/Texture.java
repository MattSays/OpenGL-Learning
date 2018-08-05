package it.mattsay.openglgame.core.rendering;


import it.mattsay.openglgame.core.Application;
import it.mattsay.openglgame.core.logging.AppLogger;
import it.mattsay.openglgame.core.rendering.objects.GLObject;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture extends GLObject {

    private int width, height;
    private ByteBuffer image;

    public Texture(String texturePath) {
        String textureFile = "resources/textures/" + texturePath;

        Application.LOGGER.debug("Decoding " + textureFile, AppLogger.DebugType.TEXTURE);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer camp = BufferUtils.createIntBuffer(1);

        this.image = STBImage.stbi_load(textureFile, width, height, camp, 4);
        if (this.image == null) {
            Application.LOGGER.err("Can't decode the image", AppLogger.ErrorType.IMAGE_LOAD);
            Application.LOGGER.err("Cause: " + STBImage.stbi_failure_reason(), AppLogger.ErrorType.IMAGE_LOAD);
            return;
        }

        this.width = width.get();
        this.height = height.get();

        Application.LOGGER.debug("Image:", AppLogger.DebugType.TEXTURE);
        Application.LOGGER.debug("- Width: " + this.width, AppLogger.DebugType.TEXTURE);
        Application.LOGGER.debug("- Height: " + this.height, AppLogger.DebugType.TEXTURE);
        Application.LOGGER.debug("Image file decoded successfully!", AppLogger.DebugType.TEXTURE);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Creates the OpenGL Object
     */
    @Override
    public void create() {
        this.create(false);
    }

    public void create(boolean repeat) {
        setId(GL11.glGenTextures());
        this.bind();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, repeat ? GL11.GL_REPEAT : GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, repeat ? GL11.GL_REPEAT : GL12.GL_CLAMP_TO_EDGE);


        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.image);
        this.unbind();
    }


    /**
     * Binds the object to use it for OpenGL operations
     */
    @Override
    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.getId());
    }

    /**
     * Unbinds the object
     */
    @Override
    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    /**
     * Deletes the object from the memory
     */
    @Override
    public void destroy() {
        Application.LOGGER.debug("Destroying the texture", AppLogger.DebugType.TEXTURE);
        GL11.glDeleteTextures(this.getId());
    }
}
