package RacoonRush.objects;

import RacoonRush.game.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Reward extends Object {
    public Reward() {
        name = "Reward";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/reward/donut_v1.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }

    public void update(GamePanel gp) {
        gp.player.rewardCount++;
        if (gp.player.rewardCount == 99999) { //replace 99999 with number of regular rewards in map
            // Allow player to use exit cell and move to next level/win game
        }
        else {
            // Alert player of how many more regular rewards they need
        }
    }
}
