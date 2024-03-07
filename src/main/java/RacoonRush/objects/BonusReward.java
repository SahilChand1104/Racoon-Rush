package RacoonRush.objects;

import RacoonRush.game.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class BonusReward extends Object{
    Random rand = new Random();
    double expireTime;

    public BonusReward() {
        name = "Bonus";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/reward/donut_v1.png"));

        }catch(IOException e) {
            e.printStackTrace();
        }
        collision = true;

        expireTime = (double) (30 + rand.nextInt(60));
    }
    public void update(GamePanel gp) {
        gp.player.score += 100;
        // Alert player of score gain
    }

    public void updateTimer(GamePanel gp) {
        // If playing, lower expireTime
        // If expireTime reaches 0, remove bonus reward
    }
}
