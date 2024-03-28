package RacoonRush.game.menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;

/**
 * Class for handling the key inputs for the UI
 * Similar to the KeyHandler class, but only for the UI and play/pause functionality
 * Contains extra keybindings for the UI
 */
public class UIKeyHandler implements KeyListener {

    // Enum for the different keys that can be pressed
    private final EnumMap<UI_Pressed, Boolean> pressedUI = new EnumMap<>(UI_Pressed.class);

    /**
     * Constructor for the UIKeyHandler
     */
    public UIKeyHandler () {
        for (UI_Pressed ui_pressed : UI_Pressed.values()) {
            pressedUI.put(ui_pressed, false);
        }
    }

    /**
     * Returns the state of the key
     * @param key the key
     * @return the boolean state of the key
     */
    public boolean get(UI_Pressed key) {
        return pressedUI.get(key);
    }

    /**
     * Adds the action to the pressedUI map
     * @param e the key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            pressedUI.put(UI_Pressed.UP, true);
        }
        if (code == KeyEvent.VK_S) {
            pressedUI.put(UI_Pressed.DOWN, true);
        }
        if (code == KeyEvent.VK_A) {
            pressedUI.put(UI_Pressed.LEFT, true);
        }
        if (code == KeyEvent.VK_D) {
            pressedUI.put(UI_Pressed.RIGHT, true);
        }
        if (code == KeyEvent.VK_ENTER) {
            pressedUI.put(UI_Pressed.ENTER, true);
        }
        if (code == KeyEvent.VK_ESCAPE) {
            pressedUI.put(UI_Pressed.ESCAPE, true);
        }
        if (code == KeyEvent.VK_P) {
            pressedUI.put(UI_Pressed.PAUSE, true);
        }
    }

    /**
     * Removes the action from the pressedUI map
     * @param e the key
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            pressedUI.put(UI_Pressed.UP, false);
        }
        if (code == KeyEvent.VK_S) {
            pressedUI.put(UI_Pressed.DOWN, false);
        }
        if (code == KeyEvent.VK_A) {
            pressedUI.put(UI_Pressed.LEFT, false);
        }
        if (code == KeyEvent.VK_D) {
            pressedUI.put(UI_Pressed.RIGHT, false);
        }
        if (code == KeyEvent.VK_ENTER) {
            pressedUI.put(UI_Pressed.ENTER, false);
        }
        if (code == KeyEvent.VK_ESCAPE) {
            pressedUI.put(UI_Pressed.ESCAPE, false);
        }
        if (code == KeyEvent.VK_P) {
            pressedUI.put(UI_Pressed.PAUSE, false);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}
