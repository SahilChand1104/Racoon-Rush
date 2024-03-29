package RacoonRush.game.menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;

/**
 * Class for handling the key inputs for the Menu
 * Similar to the KeyHandler class, but only for the Menu and play/pause functionality
 * Contains extra keybindings for the Menu
 */
public class MenuKeyHandler implements KeyListener {

    // Enum for the different keys that can be pressed
    private final EnumMap<MenuKey, Boolean> pressedMenuKey = new EnumMap<>(MenuKey.class);

    /**
     * Constructor for the MenuKeyHandler
     */
    public MenuKeyHandler() {
        for (MenuKey menuKey : MenuKey.values()) {
            pressedMenuKey.put(menuKey, false);
        }
    }

    /**
     * Returns the state of the key
     * @param key the key
     * @return the boolean state of the key
     */
    public boolean get(MenuKey key) {
        return pressedMenuKey.get(key);
    }

    /**
     * Adds the action to the pressedUI map
     * @param e the key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            pressedMenuKey.put(MenuKey.UP, true);
        }
        if (code == KeyEvent.VK_S) {
            pressedMenuKey.put(MenuKey.DOWN, true);
        }
        if (code == KeyEvent.VK_A) {
            pressedMenuKey.put(MenuKey.LEFT, true);
        }
        if (code == KeyEvent.VK_D) {
            pressedMenuKey.put(MenuKey.RIGHT, true);
        }
        if (code == KeyEvent.VK_ENTER) {
            pressedMenuKey.put(MenuKey.ENTER, true);
        }
        if (code == KeyEvent.VK_ESCAPE) {
            pressedMenuKey.put(MenuKey.ESCAPE, true);
        }
        if (code == KeyEvent.VK_P) {
            pressedMenuKey.put(MenuKey.PAUSE, true);
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
            pressedMenuKey.put(MenuKey.UP, false);
        }
        if (code == KeyEvent.VK_S) {
            pressedMenuKey.put(MenuKey.DOWN, false);
        }
        if (code == KeyEvent.VK_A) {
            pressedMenuKey.put(MenuKey.LEFT, false);
        }
        if (code == KeyEvent.VK_D) {
            pressedMenuKey.put(MenuKey.RIGHT, false);
        }
        if (code == KeyEvent.VK_ENTER) {
            pressedMenuKey.put(MenuKey.ENTER, false);
        }
        if (code == KeyEvent.VK_ESCAPE) {
            pressedMenuKey.put(MenuKey.ESCAPE, false);
        }
        if (code == KeyEvent.VK_P) {
            pressedMenuKey.put(MenuKey.PAUSE, false);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}
