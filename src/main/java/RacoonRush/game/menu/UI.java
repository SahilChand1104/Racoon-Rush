package RacoonRush.game.menu;

import RacoonRush.game.GamePanel;
import RacoonRush.game.KeyHandler;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

public class UI {
    private final Font arial_40;

    private final GamePanel gamePanel;

    private final EnumMap<ComponentType, MenuComponent> components;
    private final ArrayList<MenuComponent> selectableComponents;
    private int selectedComponentIndex;

    private MenuState menuState;


    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        components = new EnumMap<>(ComponentType.class);
        selectableComponents = new ArrayList<>();
        loadComponents();
        selectedComponentIndex = 0;
        menuState = MenuState.MAIN;
    }

    public void draw(Graphics2D g2) {

        switch (menuState) {
            case MAIN:
                drawMain(g2);
                break;
            case SETTINGS:
                drawSettings(g2);
                break;
            default:
                break;
        }
    }

    private void drawMain(Graphics2D g2) {
        components.forEach((type, component) -> component.draw(g2));
    }

    private void drawSettings(Graphics2D g2) {
        Font titleFont = new Font("Arial", Font.BOLD, 40);
        Font labelFont = new Font("Arial", Font.PLAIN, 20);
        components.get(ComponentType.BG).draw(g2);
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/font/VCR_OSD_MONO_1.001.ttf").openStream());
            labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/font/VCR_OSD_MONO_1.001.ttf").openStream());
        } catch (IOException | FontFormatException e) {
            // Handle font loading exception
            e.printStackTrace();
        }
        int x_align = 100;
        g2.setFont(titleFont.deriveFont(Font.BOLD, 40f));
        GradientPaint gp = new GradientPaint(0, 0, Color.MAGENTA, 500, 0, Color.ORANGE);
        g2.setPaint(gp);
        g2.drawString("Instructions", x_align, 100);
        g2.setFont(labelFont.deriveFont(Font.PLAIN, 20f));
        g2.drawString("Collect all the donuts to win", x_align, 200);
        g2.drawString("Use W A S D to move", x_align, 250);
        g2.drawString("Press P to pause", x_align, 300);
        g2.drawString("Try to catch Uncle Fatih's lost pizza as ", x_align, 350);
        g2.drawString("it teleports around the map", x_align, 375);
        g2.drawString("Press ESC to exit...", x_align, 450);
    }

    public void loadComponents() {
        MenuComponent playButton = new MenuComponent(gamePanel, 768 / 2 - 200, 200, ComponentType.PLAY, gamePanel.getImageLoader().getMenuImage(ComponentType.PLAY));
        MenuComponent instructionsButton = new MenuComponent(gamePanel, 768 / 2 - 200, 400, ComponentType.SETTINGS, gamePanel.getImageLoader().getMenuImage(ComponentType.SETTINGS));


        components.put(ComponentType.BG, new MenuComponent(gamePanel, 0, 0, ComponentType.BG, gamePanel.getImageLoader().getMenuImage(ComponentType.BG)));
        components.put(ComponentType.BANNER, new MenuComponent(gamePanel, 0, 50, ComponentType.BANNER, gamePanel.getImageLoader().getMenuImage(ComponentType.BANNER)));
        components.put(ComponentType.PLAY, playButton);
        selectableComponents.add(playButton);
        components.put(ComponentType.SETTINGS, instructionsButton);
        selectableComponents.add(instructionsButton);

    }



    public void update() { // depending on the keyhandler, get the correct component and modify it
        UIKeyHandler uiKeyHandler = gamePanel.getUIKeyHandler();

        for (MenuComponent component : selectableComponents) {
            component.setSelected(false);
        }

        if (menuState == MenuState.SETTINGS && uiKeyHandler.get(UI_Pressed.ESCAPE)) {
            menuState = MenuState.MAIN;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (uiKeyHandler.get(UI_Pressed.UP)) {
            if (selectedComponentIndex > 0) {
                selectedComponentIndex--;
            }
        } else if (uiKeyHandler.get(UI_Pressed.DOWN)) {
            if (selectedComponentIndex < selectableComponents.size() - 1) {
                selectedComponentIndex++;
            }
        } else if (uiKeyHandler.get(UI_Pressed.ENTER)) {
            selectableComponents.get(selectedComponentIndex).doAction();
        } else if (uiKeyHandler.get(UI_Pressed.ESCAPE) && menuState == MenuState.MAIN) {
            gamePanel.stopGame();
        }

        selectableComponents.get(selectedComponentIndex).setSelected(true);
        //component.update();
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }
}
