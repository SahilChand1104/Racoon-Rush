package RacoonRush.menu;

import RacoonRush.game.KeyHandler;
import RacoonRush.game.Move;

import java.awt.*;
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
        components.add(new MenuComponent(menu, 0, 0, ComponentType.BG));
        // Add Menu Title
        components.add(new MenuComponent(menu, 0, 50, ComponentType.BANNER));
        // Add Play Button
        components.add(new MenuComponent(menu, 768/2-200, 200, ComponentType.PLAY));
        // Add Settings Button
        components.add(new MenuComponent(menu, 768/2-200, 400, ComponentType.SETTINGS));
    }



    public void update() {
        KeyHandler keyHandler = menu.getKeyHandler();
        for (MenuComponent component : components) {

            component.update();
        }
    }


}
