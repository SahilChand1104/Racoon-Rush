package RacoonRush.game.menu;

import RacoonRush.game.Move;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;

public class UIKeyHandler implements KeyListener {

    private final EnumMap<UI_Pressed, Boolean> pressedUI = new EnumMap<>(UI_Pressed.class);

    public UIKeyHandler () {
        for (UI_Pressed ui_pressed : UI_Pressed.values()) {
            pressedUI.put(ui_pressed, false);
        }
    }

    public boolean get(UI_Pressed key) {
        return pressedUI.get(key);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

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
}
