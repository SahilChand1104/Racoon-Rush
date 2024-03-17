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

    public void drawString(Graphics2D g2, String text, Font font, int x, int y) {
        g2.setFont(font);
        g2.drawString(text, x, y);
    }

    public void draw(Graphics2D g2) {
        components.forEach((type, component) -> component.draw(g2));

    }

    public void loadComponents() {
        MenuComponent playButton = new MenuComponent(gamePanel, 768 / 2 - 200, 200, ComponentType.PLAY, gamePanel.getImageLoader().getMenuImage(ComponentType.PLAY));
        MenuComponent settingsButton = new MenuComponent(gamePanel, 768 / 2 - 200, 400, ComponentType.SETTINGS, gamePanel.getImageLoader().getMenuImage(ComponentType.SETTINGS));


        components.put(ComponentType.BG, new MenuComponent(gamePanel, 0, 0, ComponentType.BG, gamePanel.getImageLoader().getMenuImage(ComponentType.BG)));
        components.put(ComponentType.BANNER, new MenuComponent(gamePanel, 0, 50, ComponentType.BANNER, gamePanel.getImageLoader().getMenuImage(ComponentType.BANNER)));
        components.put(ComponentType.PLAY, playButton);
        selectableComponents.add(playButton);
        components.put(ComponentType.SETTINGS, settingsButton);
        selectableComponents.add(settingsButton);

    }



    public void update() { // depending on the keyhandler, get the correct component and modify it
        KeyHandler keyHandler = gamePanel.getKeyHandler();

        for (MenuComponent component : selectableComponents) {
            component.setSelected(false);
        }

        if (keyHandler.getUI_Pressed(UI_Pressed.UP)) {
            if (selectedComponentIndex > 0) {
                selectedComponentIndex--;
            }
        } else if (keyHandler.getUI_Pressed(UI_Pressed.DOWN)) {
            if (selectedComponentIndex < selectableComponents.size() - 1) {
                selectedComponentIndex++;
            }
        } else if (keyHandler.getUI_Pressed(UI_Pressed.ENTER)) {
            selectableComponents.get(selectedComponentIndex).doAction();
        } else if (keyHandler.getUI_Pressed(UI_Pressed.ESCAPE)) {
            gamePanel.stopGame();
        }

        selectableComponents.get(selectedComponentIndex).setSelected(true);
        //component.update();
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }
}
