package RacoonRush.game.menu;

import RacoonRush.game.Config;
import RacoonRush.game.GamePanel;
import RacoonRush.game.ImageLoader;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

public class UI {
    private final GamePanel gamePanel;

    // Enum map for all menu components
    private final EnumMap<ComponentType, MenuComponent> components;

    // List of all selectable components
    private final ArrayList<MenuComponent> selectableComponents;
    private int selectedComponentIndex;
    private MenuState menuState;
    private Font font = null;
    private boolean winStatus;


    /**
     * Constructor for the UI
     * @param gamePanel the gamePanel
     */
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        components = new EnumMap<>(ComponentType.class);
        selectableComponents = new ArrayList<>();
        // Load the menu components
        loadComponents();
        selectedComponentIndex = 0;
        menuState = MenuState.MAIN;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/VCR_OSD_MONO_1.001.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
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

        // Increment the selected component index depending on the key press
        if (uiKeyHandler.get(UI_Pressed.ESCAPE)) {
            if (menuState == MenuState.SETTINGS) {
                menuState = MenuState.MAIN;
                try {
                    Thread.sleep(500); // This prevents ESC from closing the game
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.exit(0);
            }
        } else if (uiKeyHandler.get(UI_Pressed.UP)) {
            selectedComponentIndex = Math.max(selectedComponentIndex - 1, 0);
        } else if (uiKeyHandler.get(UI_Pressed.DOWN)) {
            selectedComponentIndex = Math.min(selectedComponentIndex + 1, selectableComponents.size() - 1);
        } else if (uiKeyHandler.get(UI_Pressed.ENTER)) {
            selectableComponents.get(selectedComponentIndex).doAction();
        }

        // Set the selected component to true
        selectableComponents.get(selectedComponentIndex).setSelected(true);
    }

    /**
     * Draws the UI depending on the menu state
     * @param g2 the graphics2D object
     */
    public void draw(Graphics2D g2) {
        switch (menuState) {
            case MAIN -> drawMain(g2);
            case SETTINGS -> drawSettings(g2);
            case GAMEOVER -> drawGameover(g2);
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
        // Draw the background
        components.get(ComponentType.BG).draw(g2);
        // Draw the instructions
        int x_align = 100;
        int y_align = 100;
        g2.setFont(font.deriveFont(Font.BOLD, 40f));
        GradientPaint gp = new GradientPaint(0, 0, Color.MAGENTA, 500, 0, Color.ORANGE);
        g2.setPaint(gp);
        g2.drawString("Instructions", x_align, y_align);
        g2.setFont(font.deriveFont(Font.PLAIN, 20f));
        g2.drawString("Collect all the donuts to win", x_align, y_align + 50);
        g2.drawString("Avoid collecting the radioactive waste", x_align, y_align + 100);
        g2.drawString("You will lose if your score drops below 0!", x_align, y_align + 125);
        y_align += 25;
        g2.drawString("Use W A S D to move", x_align, y_align + 150);
        g2.drawString("Press P to pause", x_align, y_align + 200);
        g2.drawString("Try to catch Uncle Fatih's lost pizza as ", x_align, y_align + 250);
        g2.drawString("it teleports around the map", x_align, y_align + 275);

        g2.drawString("Press ESC to exit...", x_align, y_align + 350);
    }

    public void drawGameover(Graphics2D g2) {
        Config config = gamePanel.getConfig();

        g2.setFont(font.deriveFont(Font.BOLD, 24f));
        FontMetrics fontMetrics = g2.getFontMetrics();

        g2.setColor(winStatus ? Color.GREEN : Color.RED);
        String[] messages = winStatus ? new String[] {
                "You win!",
                "Score: " + gamePanel.getScore(),
                "Time: " + gamePanel.getGameTime().getFormattedTime()
        } : new String[] {
                "You lose! Better luck next time!",
                "Donuts remaining: " + gamePanel.getMapManager().getDonutsLeft(),
        };

        for (int i = 0; i < messages.length; i++) {
            g2.drawString(
                    messages[i],
                    (config.screenWidth() - fontMetrics.stringWidth(messages[i])) / 2,
                    config.screenHeight() / 2 + fontMetrics.getHeight() * i
            );
        }

        String exitMessage = "Press ESC to quit";
        g2.drawString(exitMessage, (config.screenWidth() - fontMetrics.stringWidth(exitMessage)) / 2, config.screenHeight() - 100);
    }

    /**
     * Loads the menu components
     */
    public void loadComponents() {
        ImageLoader imageLoader = gamePanel.getImageLoader();

        MenuComponent playButton = new MenuComponent(gamePanel, 768 / 2 - 200, 200, ComponentType.PLAY, imageLoader.getMenuImage(ComponentType.PLAY));
        MenuComponent instructionsButton = new MenuComponent(gamePanel, 768 / 2 - 200, 400, ComponentType.SETTINGS, imageLoader.getMenuImage(ComponentType.SETTINGS));

        components.put(ComponentType.BG, new MenuComponent(gamePanel, 0, 0, ComponentType.BG, imageLoader.getMenuImage(ComponentType.BG)));
        components.put(ComponentType.BANNER, new MenuComponent(gamePanel, 0, 50, ComponentType.BANNER, imageLoader.getMenuImage(ComponentType.BANNER)));
        components.put(ComponentType.PLAY, playButton);
        components.put(ComponentType.SETTINGS, instructionsButton);

        // Add the selectable components to the list
        // Load the selectable components in the order they appear on the screen, from top to bottom
        selectableComponents.add(playButton);
        selectableComponents.add(instructionsButton);

    }

    /**
     * Sets the menu state
     * @param menuState the menu state
     */
    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }

    public void setMenuState(MenuState menuState, boolean winStatus) {
        this.menuState = menuState;
        this.winStatus = winStatus;
    }
}
