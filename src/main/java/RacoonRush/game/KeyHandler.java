package RacoonRush.game;

import RacoonRush.game.menu.UI_Pressed;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;

public class KeyHandler implements KeyListener {
    // Stores which keys are pressed
    private final EnumMap<Move, Boolean> pressed = new EnumMap<>(Move.class);
    private final EnumMap<UI_Pressed, Boolean> pressedUI = new EnumMap<>(UI_Pressed.class);

    public KeyHandler() {
        for (Move move : Move.values()) {
            pressed.put(move, false);
        }
    }

    public boolean get(Move move) {
        //System.out.println(pressed.get(move));
        return pressed.get(move);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Key Pressed");
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            System.out.println("W Pressed");
            pressed.put(Move.UP, true);
            pressedUI.put(UI_Pressed.UP, true);
        }
        if (code == KeyEvent.VK_S) {
            pressed.put(Move.DOWN, true);
            pressedUI.put(UI_Pressed.DOWN, true);
        }
        if (code == KeyEvent.VK_A) {
            pressed.put(Move.LEFT, true);
            pressedUI.put(UI_Pressed.LEFT, true);
        }
        if (code == KeyEvent.VK_D) {
            pressed.put(Move.RIGHT, true);
            pressedUI.put(UI_Pressed.RIGHT, true);
        }
        if (code == KeyEvent.VK_ENTER) {
            pressedUI.put(UI_Pressed.ENTER, true);
        }
        if (code == KeyEvent.VK_ESCAPE) {
            pressedUI.put(UI_Pressed.ESCAPE, true);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            pressed.put(Move.UP, false);
            pressedUI.put(UI_Pressed.UP, false);
        }
        if (code == KeyEvent.VK_S) {
            pressed.put(Move.DOWN, false);
            pressedUI.put(UI_Pressed.DOWN, false);
        }
        if (code == KeyEvent.VK_A) {
            pressed.put(Move.LEFT, false);
            pressedUI.put(UI_Pressed.LEFT, false);
        }
        if (code == KeyEvent.VK_D) {
            pressed.put(Move.RIGHT, false);
            pressedUI.put(UI_Pressed.RIGHT, false);
        }
        if (code == KeyEvent.VK_ENTER) {
            pressedUI.put(UI_Pressed.ENTER, false);
        }
        if (code == KeyEvent.VK_ESCAPE) {
            pressedUI.put(UI_Pressed.ESCAPE, false);
        }
    }
}
