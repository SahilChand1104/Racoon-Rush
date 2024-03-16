package RacoonRush.menu;

import RacoonRush.game.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class UI {
    private final Font arial_40;

    private final Menu menu;

    private final ArrayList<MenuComponent> components;


    public UI(Menu menu) {
        this.menu = menu;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        components = new ArrayList<>();
        loadComponents();
    }

    public void drawString(Graphics2D g2, String text, Font font, int x, int y) {
        g2.setFont(font);
        g2.drawString(text, x, y);
    }

    public void draw(Graphics2D g2) {
        for (MenuComponent component : components) {
            component.draw(g2);
        }

    }

    public void loadComponents() {

        // Add Menu Background
        components.add(new MenuComponent(menu, 0, 0, loadImage("/menu/menu_bg.png"), true, false, false));
        // Add Menu Title
        components.add(new MenuComponent(menu, 0, 50, loadImage("/menu/menu_title_v2.png"), false, true, false));
        // Add Play Button
        components.add(new MenuComponent(menu, 768/2-200, 200, loadImage("/menu/menu_label_play_1.png"), false, false, true));
        // Add Settings Button
        components.add(new MenuComponent(menu, 768/2-200, 400, loadImage("/menu/menu_label_settings_0.png"), false, false, false));
    }

    private BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


}
