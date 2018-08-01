package it.mattsay.openglgame.core.logging;

import it.mattsay.openglgame.core.Application;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AppLogger {

    private Path outputName;
    private SimpleDateFormat dateFormat;

    /**
     * Creates the log file and if the file count is equal or more than 8 the logger deletes all the log files
     */
    public AppLogger() {
        this.dateFormat = new SimpleDateFormat();
        String outputName = "logs/" + this.getCurrentTime("yyyy-MM-dd-HH-mm-ss") + ".log";
        File file = new File(outputName);

        if (file.getParentFile().listFiles().length >= 8) {
            for (File f :
                    file.getParentFile().listFiles()) {
                f.delete();
            }
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.outputName = file.toPath();

    }

    /**
     * Prints the message with the correct log level and appends to the log file
     *
     * @param level
     * @param message
     */
    private void log(Level level, String message) {
        String log = "[" + getCurrentTime("MM/dd/yyyy - HH:mm:ss") + "] " + "{" + level.name() + "} " + message;
        System.out.println(level.getColor() + log + "\033[0m");
        try {
            log += "\n";
            Files.write(this.outputName, log.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void info(String message) {
        this.log(Level.INFO, message);
    }

    public void warn(String message) {
        this.log(Level.WARNING, message);
    }

    public void err(String message, ErrorType error) {
        if (error == null) error = ErrorType.UNKNOWN;
        this.log(Level.ERROR, "[" + error.name() + "] " + message);
    }

    /**
     * Used in GLFWErrorCallback
     *
     * @param error
     * @param message
     */
    public void glfwError(int error, long message) {
        this.err("(" + Integer.toHexString(error) + ")" + " " + GLFWErrorCallback.getDescription(message), ErrorType.GLFW);
    }

    /**
     * Check for opengl error
     */
    public void checkOpenGLError() {
        int error = GL11.glGetError();
        err(Integer.toHexString(error), ErrorType.OPENGL);
    }

    /**
     * Used for instantly close the application with a message
     *
     * @param message
     */
    public void crash(String message, ErrorType type) {
        this.err(message, type);
        Application.exit();
    }

    /**
     * Gets the current time from the format
     *
     * @param format
     * @return current time
     */
    private String getCurrentTime(String format) {
        dateFormat.applyPattern(format);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public enum Level {
        INFO("\033[0m"), ERROR("\033[0;31m"), WARNING("\033[0;33m");

        private String color;

        Level(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }

    public enum ErrorType {
        GLFW, UNKNOWN, CRASH, OUT_OF_BOUNDS, OPENGL
    }

}
