package RacoonRush.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;

public class KeyHandler implements KeyListener {
    // Stores which keys are pressed
    private final EnumMap<Move, Boolean> pressed = new EnumMap<>(Move.class);

    public KeyHandler() {
        for (Move move : Move.values()) {
            pressed.put(move, false);
        }
    }

    public boolean get(Move move) {
        return pressed.get(move);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
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
}
