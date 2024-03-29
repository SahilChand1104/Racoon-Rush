package RacoonRush.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;

/**
 * KeyHandler class
 * This is responsible for taking in the user's input and storing it in a EnumMap
 */
public class KeyHandler implements KeyListener {
    // EnumMap to store the user's input
    private final EnumMap<Move, Boolean> pressed = new EnumMap<>(Move.class);

    /**
     * Constructor for KeyHandler
     * Initializes the EnumMap with all the possible moves and sets them to false
     */
    public KeyHandler() {
        for (Move move : Move.values()) {
            pressed.put(move, false);
        }
    }

    /**
     * Getter for the EnumMap
     * @param move the move to get the value of
     * @return the boolean value of the move
     */
    public boolean get(Move move) {
        return pressed.get(move);
    }

    /**
     * Method to handle the key being pressed
     * @param e the KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            pressed.put(Move.UP, true);
        }
        if (code == KeyEvent.VK_S) {
            pressed.put(Move.DOWN, true);
        }
        if (code == KeyEvent.VK_A) {
            pressed.put(Move.LEFT, true);
        }
        if (code == KeyEvent.VK_D) {
            pressed.put(Move.RIGHT, true);
        }
    }

    /**
     * Method to handle the key being released
     * @param e the KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            pressed.put(Move.UP, false);
        }
        if (code == KeyEvent.VK_S) {
            pressed.put(Move.DOWN, false);
        }
        if (code == KeyEvent.VK_A) {
            pressed.put(Move.LEFT, false);
        }
        if (code == KeyEvent.VK_D) {
            pressed.put(Move.RIGHT, false);
        }
    }

    /**
     * (NOT USED) Method to handle the key being typed
     * @param e the KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }
}
