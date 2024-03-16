package RacoonRush.menu;

import RacoonRush.game.Config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class MenuComponent {

    private final Menu menu;

    private final int x;
    private final int y;

    private final BufferedImage image;

    private boolean fill;
    private boolean stretch;

    private boolean selected;

    public MenuComponent(Menu menu, int x, int y, BufferedImage image, boolean fill, boolean stretch, boolean selected) {
        this.menu = menu;

        this.x = x;
        this.y = y;
        this.image = image;
        this.fill = fill;
        this.stretch = stretch;
        this.selected = selected;

    }

    public void draw(Graphics2D g2) {
        if (fill) {
            g2.drawImage(image, x, y, Math.max(menu.getHeight(), menu.getWidth()) , Math.max(menu.getHeight(), menu.getWidth()), null);
        } else if (stretch) {
            g2.drawImage(image, x, y, menu.getWidth(), image.getHeight(),null);
        } else {
            g2.drawImage(image, x, y, null);
        }

    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
