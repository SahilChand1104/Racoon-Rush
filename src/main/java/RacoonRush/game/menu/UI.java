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

    // Enum map for all menu components
    private final EnumMap<ComponentType, MenuComponent> components;

    // List of all selectable components
    private final ArrayList<MenuComponent> selectableComponents;
    private int selectedComponentIndex;

    private MenuState menuState;


    /**
     * Constructor for the UI
     * @param gamePanel the gamePanel
     */
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        components = new EnumMap<>(ComponentType.class);
        selectableComponents = new ArrayList<>();
        // Load the menu components
        loadComponents();
        selectedComponentIndex = 0;
        menuState = MenuState.MAIN;
    }

    /**
     * Draws the UI depending on the menu state
     * @param g2 the graphics2D object
     */
    public void draw(Graphics2D g2) {

        switch (menuState) {
            case MAIN: // Draw the main menu
                drawMain(g2);
                break;
            case SETTINGS: // Draw the settings menu
                drawSettings(g2);
                break;
            default:
                break;
        }
    }

    /**
     * Draws the main menu
     * @param g2 the graphics2D object
     */
    private void drawMain(Graphics2D g2) {
        components.forEach((type, component) -> component.draw(g2));
    }

    /**
     * Draws the settings menu
     * @param g2 the graphics2D object
     */
    private void drawSettings(Graphics2D g2) {
        Font titleFont = new Font("Arial", Font.BOLD, 40);
        Font labelFont = new Font("Arial", Font.PLAIN, 20);
        // Draw the background
        components.get(ComponentType.BG).draw(g2);
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/font/VCR_OSD_MONO_1.001.ttf").openStream());
            labelFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/font/VCR_OSD_MONO_1.001.ttf").openStream());
        } catch (IOException | FontFormatException e) {
            // Handle font loading exception
            e.printStackTrace();
        }

        // Draw the instructions
        int x_align = 100;
        int y_align = 100;
        g2.setFont(titleFont.deriveFont(Font.BOLD, 40f));
        GradientPaint gp = new GradientPaint(0, 0, Color.MAGENTA, 500, 0, Color.ORANGE);
        g2.setPaint(gp);
        g2.drawString("Instructions", x_align, y_align);
        g2.setFont(labelFont.deriveFont(Font.PLAIN, 20f));
        g2.drawString("Collect all the donuts to win", x_align, y_align+50);
        g2.drawString("Avoid collecting the radioactive waste", x_align, y_align+100);
        g2.drawString("You will lose if your score drops below 0!", x_align, y_align+125);
        y_align+= 25;
        g2.drawString("Use W A S D to move", x_align, y_align+150);
        g2.drawString("Press P to pause", x_align, y_align+200);
        g2.drawString("Try to catch Uncle Fatih's lost pizza as ", x_align, y_align+250);
        g2.drawString("it teleports around the map", x_align, y_align+275);

        g2.drawString("Press ESC to exit...", x_align, y_align+350);
    }

    /**
     * Loads the menu components
     */
    public void loadComponents() {
        MenuComponent playButton = new MenuComponent(gamePanel, 768 / 2 - 200, 200, ComponentType.PLAY, gamePanel.getImageLoader().getMenuImage(ComponentType.PLAY));
        MenuComponent instructionsButton = new MenuComponent(gamePanel, 768 / 2 - 200, 400, ComponentType.SETTINGS, gamePanel.getImageLoader().getMenuImage(ComponentType.SETTINGS));


        components.put(ComponentType.BG, new MenuComponent(gamePanel, 0, 0, ComponentType.BG, gamePanel.getImageLoader().getMenuImage(ComponentType.BG)));
        components.put(ComponentType.BANNER, new MenuComponent(gamePanel, 0, 50, ComponentType.BANNER, gamePanel.getImageLoader().getMenuImage(ComponentType.BANNER)));
        components.put(ComponentType.PLAY, playButton);
        components.put(ComponentType.SETTINGS, instructionsButton);
        // Add the selectable components to the list
        // Load the selectable components in the order they appear on the screen, from top to bottom
        selectableComponents.add(playButton);
        selectableComponents.add(instructionsButton);

    }


    /**
     * Updates the UI
     */
    public void update() {

        // Use the UI key handler, not the game key handler
        UIKeyHandler uiKeyHandler = gamePanel.getUIKeyHandler();

        // Initialize all to false
        for (MenuComponent component : selectableComponents) {
            component.setSelected(false);
        }


        if (menuState == MenuState.SETTINGS && uiKeyHandler.get(UI_Pressed.ESCAPE)) {
            menuState = MenuState.MAIN;
            try {
                Thread.sleep(500); // This prevents ESC from closing the game
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // Increment the selected component index depending on the key press
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

        // Set the selected component to true
        selectableComponents.get(selectedComponentIndex).setSelected(true);
    }

    /**
     * Sets the menu state
     * @param menuState the menu state
     */
    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }
}
