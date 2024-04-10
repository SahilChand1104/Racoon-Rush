package RaccoonRush.game.menu;

import RaccoonRush.game.GameManager;
import RaccoonRush.game.menu.component.*;
import RaccoonRush.game.menu.component.MenuComponent;
import RaccoonRush.util.Config;
import RaccoonRush.game.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * Class for the Menu, responsible for the main menu, settings menu, and gameover menu.
 * The menu does not run while the game is actively being played.
 */
public class Menu implements GameManager {
    private final GamePanel gamePanel;
    private final ArrayList<MenuComponent> components;
    private final ArrayList<MenuButton> selectables;
    private int buttonComponentIndex;
    private MenuState menuState;
    private Font font;
    private boolean winStatus;

    /**
     * Constructor for the Menu
     * @param gamePanel the gamePanel
     */
    public Menu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        components = new ArrayList<>();
        selectables = new ArrayList<>();
        loadComponents();
        buttonComponentIndex = 0;
        menuState = MenuState.MAIN;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/font/VCR_OSD_MONO_1.001.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MenuButton> getSelectables() {
        return selectables;
    }

    /**
     * Loads the menu components
     */
    public void loadComponents() {
        Config config = gamePanel.getConfig();
        MenuImageLoader imageLoader = new MenuImageLoader(gamePanel);

        MenuButton playButton = new MenuButton(
                gamePanel,
                (config.screenWidth() - imageLoader.menuPlay.getWidth()) / 2, config.tileSize() * 4,
                imageLoader.menuPlay, imageLoader.menuPlaySelected,
                ButtonType.PLAY
        );
        playButton.setSelected(true);

        MenuButton instructionsButton = new MenuButton(
                gamePanel,
                (config.screenWidth() - imageLoader.menuInstructions.getWidth()) / 2, config.tileSize() * 8,
                imageLoader.menuInstructions, imageLoader.menuInstructionsSelected,
                ButtonType.SETTINGS
        );

        // Add the selectable components to the array list
        // Load the selectable components in the order they appear on the screen, from top to bottom
        selectables.add(playButton);
        selectables.add(instructionsButton);

        MenuBG bg = new MenuBG(gamePanel, 0, 0, imageLoader.menuBackground);

        MenuBanner title = new MenuBanner(gamePanel, 0, config.tileSize(), imageLoader.menuTitle);

        // Add the components to the array list
        // Load the components in the order they appear on the screen, from back to front
        components.add(bg); // Background should always be first
        components.add(title);
        components.add(playButton);
        components.add(instructionsButton);


    }

    /**
     * Updates the menu based on the key inputs
     */
    public void update() {
        // Use the Menu key handler, not the game key handler
        MenuKeyHandler menuKeyHandler = gamePanel.getMenuKeyHandler();

        // Handle key press
        if (menuKeyHandler.get(MenuKey.ESCAPE)) {
            escapePressed();
        } else if (menuKeyHandler.get(MenuKey.ENTER)) {
            doAction(selectables.get(buttonComponentIndex));
        } else if (menuKeyHandler.get(MenuKey.UP)) {
            moveSelection(-1);
        } else if (menuKeyHandler.get(MenuKey.DOWN)) {
            moveSelection(1);
        }
    }

    /**
     * Handles the escape key press
     */
    private void escapePressed() {
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
    }

    /**
     * Performs the appropriate action based on the button type
     * @param menuButton the button component to perform the action on
     */
    private void doAction(MenuButton menuButton) {
        switch (menuButton.getButtonType()) {
            case PLAY -> gamePanel.playGame();
            case SETTINGS -> menuState = MenuState.SETTINGS;
        }
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
    /**
     * Moves the selection up or down by deselecting the old button and selecting the new button
     * Guaranteed to be within bounds of the buttonComponents array
     * @param direction the direction to move the selection in
     */
    private void moveSelection(int direction) {
        selectables.get(buttonComponentIndex).setSelected(false);
        buttonComponentIndex = clamp(buttonComponentIndex + direction, 0, selectables.size() - 1);
        selectables.get(buttonComponentIndex).setSelected(true);
    }

    /**
     * Stops the game and sets the menu state to GAMEOVER
     * @param winStatus whether the game has been won
     */
    public void stopGame(boolean winStatus) {
        this.winStatus = winStatus;
        menuState = MenuState.GAMEOVER;
    }

    /**
     * Draws the Menu depending on the menu state
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
        components.forEach(component -> component.draw(g2));
        //componentsEnum.forEach((type, component) -> component.draw(g2));
    }

    /**
     * Draws the settings menu
     * @param g2 the graphics2D object
     */
    private void drawSettings(Graphics2D g2) {
        // Draw the background
        components.getFirst().draw(g2);
        // Draw the banner
        //components.get(1).draw(g2);
        //componentsEnum.get(ComponentType.BG).draw(g2);
        // Draw the instructions

        int xAlign = 100;
        int yAlign = 100;

        Font boldFont = font.deriveFont(Font.BOLD, 40f);
        Font plainFont = font.deriveFont(Font.PLAIN, 20f);
        GradientPaint gp = new GradientPaint(0, 0, Color.MAGENTA, 500, 0, Color.ORANGE);
        Color white = new Color(255, 255, 255);

        MenuLabel instructions = new MenuLabel(gamePanel, xAlign, yAlign, "Instructions", boldFont, white);

        instructions.draw(g2);

        // Make the rest of the instructions use menu label objects
        // Old code:

        g2.setFont(plainFont);
        g2.setPaint(gp);


        g2.drawString("Collect all the donuts to win", xAlign, yAlign + 50);
        g2.drawString("Avoid collecting the radioactive waste", xAlign, yAlign + 100);
        g2.drawString("You lose if your score drops below 0!", xAlign, yAlign + 125);
        yAlign += 25;
        g2.drawString("Use W A S D to move", xAlign, yAlign + 150);
        g2.drawString("Press P to pause", xAlign, yAlign + 200);
        g2.drawString("Try to catch Uncle Fatih's lost pizza as ", xAlign, yAlign + 250);
        g2.drawString("it teleports around the map", xAlign, yAlign + 275);

        g2.drawString("Press ESC to exit...", xAlign, yAlign + 350);

        // end of old code
    }

    /**
     * Draws the gameover menu based on the current win status
     * @param g2 the graphics2D object
     */
    public void drawGameover(Graphics2D g2) {
        Config config = gamePanel.getConfig();

        // Convert to menu label objects too:

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
        g2.drawString(
                exitMessage,
                (config.screenWidth() - fontMetrics.stringWidth(exitMessage)) / 2,
                config.screenHeight() - config.tileSize() * 2
        );
    }

    /**
     * Returns the index of the currently selected button component
     * @return the index of the currently selected button component
     */
    public int getButtonComponentIndex() {return buttonComponentIndex;}

    public MenuState getMenuState() { return menuState; }

    public ArrayList<MenuComponent> getComponents() {
        return components;
    }
}
