package RacoonRush.game.menu;

import RacoonRush.game.GamePanel;
import RacoonRush.game.KeyHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;

public class UI {
    private final Font arial_40;

    private final GamePanel gamePanel;

    private final EnumMap<ComponentType, MenuComponent> components;


    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        components = new EnumMap<>(ComponentType.class);
        loadComponents();
    }

    public void drawString(Graphics2D g2, String text, Font font, int x, int y) {
        g2.setFont(font);
        g2.drawString(text, x, y);
    }

    public void draw(Graphics2D g2) {
        components.forEach((type, component) -> component.draw(g2));

    }

    public void loadComponents() {

        components.put(ComponentType.BG, new MenuComponent(gamePanel, 0, 0, ComponentType.BG, gamePanel.getImageLoader().getMenuImage(ComponentType.BG)));
        components.put(ComponentType.BANNER, new MenuComponent(gamePanel, 0, 50, ComponentType.BANNER, gamePanel.getImageLoader().getMenuImage(ComponentType.BANNER)));
        components.put(ComponentType.PLAY, new MenuComponent(gamePanel, 768 / 2 - 200, 200, ComponentType.PLAY, gamePanel.getImageLoader().getMenuImage(ComponentType.PLAY)));
        components.put(ComponentType.SETTINGS, new MenuComponent(gamePanel, 768 / 2 - 200, 400, ComponentType.SETTINGS, gamePanel.getImageLoader().getMenuImage(ComponentType.SETTINGS)));
    }



    public void update() { // depending on the keyhandler, get the correct component an modify it
        KeyHandler keyHandler = gamePanel.getKeyHandler();

        if (keyHandler.getUI_Pressed(UI_Pressed.UP)) {
            components.get(ComponentType.PLAY).setSelected(true);
            components.get(ComponentType.SETTINGS).setSelected(false);
        } else if (keyHandler.getUI_Pressed(UI_Pressed.DOWN)) {
            components.get(ComponentType.SETTINGS).setSelected(true);
            components.get(ComponentType.PLAY).setSelected(false);
            System.out.println("DOWN");
        } else if (keyHandler.getUI_Pressed(UI_Pressed.LEFT)) {
            System.out.println("LEFT");
        } else if (keyHandler.getUI_Pressed(UI_Pressed.RIGHT)) {
            System.out.println("RIGHT");
        } else if (keyHandler.getUI_Pressed(UI_Pressed.ENTER)) {
            if (components.get(ComponentType.PLAY).isSelected()) {
                gamePanel.startGame();
            }
        } else if (keyHandler.getUI_Pressed(UI_Pressed.ESCAPE)) {
            System.out.println("ESCAPE");
        }
        //component.update();
    }




}
