package RaccoonRush.game.menu;

import RaccoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuImageLoader {

    protected BufferedImage menuBackground;
    protected BufferedImage menuTitle;
    protected BufferedImage menuPlay;
    protected BufferedImage menuPlaySelected;
    protected BufferedImage menuInstructions;
    protected BufferedImage menuInstructionsSelected;


    MenuImageLoader() {
        loadMenuImages();
    }

    public void loadMenuImages() {
        menuBackground = loadImage("/menu/menu_bg.png", GamePanel.config.screenWidth(), GamePanel.config.screenHeight());
        menuTitle = loadImage("/menu/menu_title_v2.png", GamePanel.config.screenWidth());
        menuPlay = loadImage("/menu/menu_label_play_0.png");
        menuPlaySelected = loadImage("/menu/menu_label_play_1.png");
        menuInstructions = loadImage("/menu/menu_label_instructions_0.png");
        menuInstructionsSelected = loadImage("/menu/menu_label_instructions_1.png");
    }

    /**
     * This method loads the image from the specified path.
     * @param path The path of the image to be loaded.
     * @return The loaded and resized image.
     */
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method loads the image from the specified path and resizes it in the horizontal direction based on the provided width.
     * @param path The path of the image to be loaded.
     * @param width The width of the resized image.
     * @return The loaded and resized image.
     */
    private BufferedImage loadImage(String path, int width) {
        BufferedImage image = loadImage(path);
        image = resizeImage(image, width, image.getHeight());
        return image;
    }

    /**
     * This method loads the image from the specified path and resizes it to the appropriate size.
     * @param path The path of the image to be loaded.
     * @param width The width of the resized image.
     * @param height The height of the resized image.
     * @return The loaded and resized image.
     */
    private BufferedImage loadImage(String path, int width, int height) {
        BufferedImage image = loadImage(path);
        image = resizeImage(image, width, height);
        return image;
    }


    /**
     * This method resizes the image to the specified width and height.
     * @param image The image to be resized.
     * @param width The width of the resized image.
     * @param height The height of the resized image.
     * @return The resized image.
     */
    public BufferedImage resizeImage(BufferedImage image, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        resizedImage.getGraphics().drawImage(image, 0, 0, width, height, null);
        return resizedImage;
    }
}
