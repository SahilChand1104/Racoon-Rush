package RaccoonRush.game.menu.component;

import RaccoonRush.game.GamePanel;

import java.awt.*;

public class MenuLabel extends MenuComponent {

    private final String text;
    private final Font font;
    //private final Color color;

    private final Paint paint;

    public MenuLabel(GamePanel gamePanel, int x, int y, String text, Font font, Paint paint) {
        super(gamePanel, x, y);
        this.text = text;
        this.font = font;
        this.paint = paint;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setFont(font);
        g2.setPaint(paint);
        g2.drawString(text, x, y);
    }

    @Override
    public ComponentType getType() {
        return ComponentType.LABEL;
    }

    public String getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }

}
