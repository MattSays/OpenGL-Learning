package it.mattsay.openglgame.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Input {

    private final static int MAX_KEYBOARD_KEYS = 348;
    private final static int MAX_MOUSE_BUTTONS = 8;
    private static Map<Integer, Integer> keyboardKeys;
    private static Map<Integer, Integer> mouseButtons;

    private static double mouseX = 0f;
    private static double mouseY = 0f;
    private static double mouseXOffset = 0f;
    private static double mouseYOffset = 0f;

    static {
        keyboardKeys = new HashMap<>();
        for (int i = 32; i < MAX_KEYBOARD_KEYS; i++) {
            keyboardKeys.put(i, GLFW_RELEASE);
        }

        mouseButtons = new HashMap<>();
        for (int i = 0; i < MAX_MOUSE_BUTTONS; i++) {
            mouseButtons.put(i, GLFW_RELEASE);
        }
    }

    static void keyboardCallback(long window, int key, int scancode, int action, int mods) {
        keyboardKeys.put(key, action);
    }

    /**
     * Checks if the given key is pressed
     *
     * @param key
     * @return
     */
    public static boolean isKeyPressed(int key) {
        if (!isKeyExisting(key)) return false;
        return keyboardKeys.get(key) == GLFW_PRESS;
    }

    /**
     * Check if the given key exists
     *
     * @param key
     * @return
     */
    private static boolean isKeyExisting(int key) {
        return !(key > MAX_KEYBOARD_KEYS || key < 32);
    }

    /**
     * Returns a list of the pressed keys
     *
     * @return
     */
    public static List<Integer> getPressedKeys() {
        List<Integer> keys = new ArrayList<>();
        for (int key : keyboardKeys.keySet()) {
            if (isKeyPressed(key)) keys.add(key);
        }
        return keys;
    }


    static void mouseButtonsCallback(long window, int button, int action, int mods) {
        mouseButtons.put(button, action);
    }

    /**
     * Checks if the given mouse button is pressed
     *
     * @param button
     * @return
     */
    public static boolean isButtonPressed(int button) {
        if (!isButtonExisting(button)) return false;
        return mouseButtons.get(button) == GLFW_PRESS;
    }

    /**
     * Checks if the given mouse button exists
     *
     * @param button
     * @return
     */
    private static boolean isButtonExisting(int button) {
        return !(button > MAX_MOUSE_BUTTONS || button < 0);
    }

    /**
     * Returns a list of the pressed mouse buttons
     *
     * @return
     */
    public static List<Integer> getPressedButton() {
        List<Integer> buttons = new ArrayList<>();
        for (int button : mouseButtons.keySet()) {
            if (isButtonPressed(button)) buttons.add(button);
        }
        return buttons;
    }

    static void mousePosCallback(long window, double xpos, double ypos) {
        mouseX = xpos;
        mouseY = ypos;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    static void mouseScrollCallback(long window, double xoffset, double yoffset) {
        mouseXOffset = xoffset;
        mouseYOffset = yoffset;
    }

    public static double getMouseXOffset() {
        return mouseXOffset;
    }

    public static double getMouseTOffset() {
        return mouseYOffset;
    }

}
