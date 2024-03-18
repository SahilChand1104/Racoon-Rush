package RacoonRush.map.tile;

import RacoonRush.entity.Player;
import RacoonRush.game.GamePanel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Item extends Tile {
    private final int score;

    private final GamePanel gamePanel;
    private boolean collected;


    public Item( ArrayList<BufferedImage> images, int score, GamePanel gamePanel) {
        super(images);
        this.gamePanel = gamePanel;
        this.score = score;
        collected = false;
        if (score == 50) {
            collected = true;
        }
    }

    @Override
    public boolean onCollide(Player player) {
        if (!collected) {
            collected = true;
            player.updateScore(score);

            if(score == 10){
                gamePanel.PlaySoundEffect(1);
            }
            else if(score == -20){
                gamePanel.PlaySoundEffect(2);
            }
            else if(score == 50){
                gamePanel.PlaySoundEffect(1);
            }
            else if(score == 0 && gamePanel.getPlayer().getDonutsLeft() == 0){
                gamePanel.winGame();
            }
        }
        return true;
    }


    @Override
    public BufferedImage getImage(int x, int y, int animationFrame) {
        if (images == null) {
            return null;
        }
        return collected ? null : images.get(animationFrame % images.size());
    }

    public void setCollected(boolean status) {
        collected = status;
    }
}
