package RaccoonRush.game.menu;

import RaccoonRush.game.GameManager;
import RaccoonRush.game.menu.component.*;
import RaccoonRush.game.menu.component.MenuComponent;
import RaccoonRush.game.menu.component.MenuLabel;
import RaccoonRush.game.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

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
        MenuImageLoader imageLoader = new MenuImageLoader();

        MenuButton playButton = new MenuButton(
                (GamePanel.config.screenWidth() - imageLoader.menuPlay.getWidth()) / 2, GamePanel.config.tileSize() * 4,
                imageLoader.menuPlay, imageLoader.menuPlaySelected,
                ButtonType.PLAY
        );
        playButton.setSelected(true);

        MenuButton instructionsButton = new MenuButton(
                (GamePanel.config.screenWidth() - imageLoader.menuInstructions.getWidth()) / 2, GamePanel.config.tileSize() * 8,
                imageLoader.menuInstructions, imageLoader.menuInstructionsSelected,
                ButtonType.SETTINGS
        );

        // Add the selectable components to the array list
        // Load the selectable components in the order they appear on the screen, from top to bottom
        selectables.add(playButton);
        selectables.add(instructionsButton);

        MenuBG bg = new MenuBG(0, 0, imageLoader.menuBackground);

        MenuBanner title = new MenuBanner(0, GamePanel.config.tileSize(), imageLoader.menuTitle);

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

    /**
     * Moves the selection up or down by deselecting the old button and selecting the new button
     * Guaranteed to be within bounds of the buttonComponents array
     * @param direction the direction to move the selection in
     */
    private void moveSelection(int direction) {
        selectables.get(buttonComponentIndex).setSelected(false);
        buttonComponentIndex = Math.clamp(buttonComponentIndex + direction, 0, selectables.size() - 1);
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

        MenuLabel instructions = new MenuLabel(xAlign, yAlign, "Instructions", boldFont, white);

        instructions.draw(g2);

        yAlign += 50;

        MenuLabel label2 = new MenuLabel(xAlign, yAlign, "Avoid collecting the radioactive waste", plainFont, gp);
        label2.draw(g2);

        yAlign += 25;

        MenuLabel label3 = new MenuLabel(xAlign, yAlign, "You lose if your score drops below 0!", plainFont, gp);
        label3.draw(g2);

        yAlign += 25;

        MenuLabel label4 = new MenuLabel(xAlign, yAlign, "Use W A S D to move", plainFont, gp);
        label4.draw(g2);

        yAlign += 50;

        MenuLabel label5 = new MenuLabel(xAlign, yAlign, "Press P to pause", plainFont, gp);
        label5.draw(g2);

        yAlign += 50;

        MenuLabel label6Line1 = new MenuLabel(xAlign, yAlign, "Try to catch Uncle Fatih's lost pizza", plainFont, gp);
        label6Line1.draw(g2);

        yAlign += 25; // Adjust as needed for spacing

        MenuLabel label6Line2 = new MenuLabel(xAlign, yAlign, "as it teleports around the map", plainFont, gp);
        label6Line2.draw(g2);

        yAlign += 25;

        MenuLabel label7 = new MenuLabel(xAlign, yAlign, "Press ESC to exit...", plainFont, gp);
        label7.draw(g2);
    }

    /**
     * Draws the gameover menu based on the current win status
     * @param g2 the graphics2D object
     */
    public void drawGameover(Graphics2D g2) {
        Font font = g2.getFont().deriveFont(Font.BOLD, 24f);
        FontMetrics fontMetrics = g2.getFontMetrics(font);

        Color textColor = winStatus ? Color.GREEN : Color.RED;
        String[] messages = winStatus ? new String[] {
                "You win!",
                "Score: " + gamePanel.getScore(),
                "Time: " + gamePanel.getGameTime().getFormattedTime()
        } : new String[] {
                "You lose! Better luck next time!",
                "Donuts remaining: " + gamePanel.getMapManager().getDonutsLeft(),
        };

        // Draw each message individually
        int yPos = GamePanel.config.screenHeight() / 2;
        for (String message : messages) {
            int xPos = (GamePanel.config.screenWidth() - fontMetrics.stringWidth(message)) / 2;
            MenuLabel label = new MenuLabel(xPos, yPos, message, font, textColor);
            label.draw(g2);
            yPos += fontMetrics.getHeight();
        }

        // Draw exit message
        String exitMessage = "Press ESC to quit";
        int exitXPos = (GamePanel.config.screenWidth() - fontMetrics.stringWidth(exitMessage)) / 2;
        int exitYPos = GamePanel.config.screenHeight() - GamePanel.config.tileSize() * 2;
        MenuLabel exitLabel = new MenuLabel(exitXPos, exitYPos, exitMessage, font, textColor);
        exitLabel.draw(g2);
    }

    /**
     * @return the index of the currently selected button component
     */
    public int getButtonComponentIndex() {return buttonComponentIndex;}

    public MenuState getMenuState() { return menuState; }

    public ArrayList<MenuComponent> getComponents() {
        return components;
    }
}
