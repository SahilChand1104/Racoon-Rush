package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public enum Move {
    UP(KeyEvent.VK_UP, new Point(0, -1)),
    DOWN(KeyEvent.VK_DOWN, new Point(0, 1)),
    LEFT(KeyEvent.VK_LEFT, new Point(-1, 0)),
    RIGHT(KeyEvent.VK_RIGHT, new Point(1, 0));

    private final int keyCode;
    private final Point direction;

    Move(int keyCode, Point direction) {
        this.keyCode = keyCode;
        this.direction = direction;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public Point getDirection() {
        return direction;
    }

    public static Move fromKeyCode(int keyCode) {
        for (Move move : Move.values()) {
            if (move.getKeyCode() == keyCode) {
                return move;
            }
        }
        return null;
    }
}
