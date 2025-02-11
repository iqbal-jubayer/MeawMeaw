package src.java.Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import src.java.myUtils;
import src.java.Components.Sound;

public class Ghast {
  public int x, y, width, height;
  public BufferedImage rightImage, leftImage, rageRightImage, rageLeftImage, currentImage;
  public int faceDirection = 1;
  public double velX, velY;
  public double friction = 0.2;
  public int flyDirection = 1;

  public int trembleCoolDown = 10;
  public int trembleCount;
  public int trembleDirection = 1;

  public Sound ghastSound = new Sound();

  Player player;

  public Ghast(int x, int y, int width, int height, Player player) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    try {
      this.leftImage = ImageIO.read(new File(myUtils.imgPath + "Ghast/idle.png"));
      this.rightImage = myUtils.flip(this.leftImage);
      this.rageLeftImage = ImageIO.read(new File(myUtils.imgPath + "Ghast/rage_idle.png"));
      this.rageRightImage = myUtils.flip(this.rageLeftImage);

    } catch (Exception e) {
      e.printStackTrace();
    }
    this.currentImage = this.rightImage;

    this.ghastSound.setFile(this.screamSoundIndex);
    this.player = player;
  }

  public void update() {

  }

  public void tremble() {
    if (this.trembleCount != 0 && this.trembleCount % this.trembleCoolDown == 0) {
      this.x += 5 * this.trembleDirection;
      this.trembleDirection *= -1;
    }
    this.trembleCount++;
  }

  public double screamCoolDown = myUtils.FPS * 1.5;
  public double screamTimer;
  public int screamSoundIndex = 5;

  public void scream() {
    if (!this.ghastSound.running) {
      if (this.screamTimer % this.screamCoolDown == 0 && this.screamTimer != 0) {
        this.ghastSound.play();
        if (this.screamSoundIndex == 5) {
          this.screamSoundIndex = 4;
        } else {
          this.screamSoundIndex = 5;
        }
        this.ghastSound.setFile(this.screamSoundIndex);
      }
    }
    this.screamTimer++;
  }

  public void draw(Graphics2D g) {
    int x = this.x + this.player.worldPos[0];
    int y = this.y + this.player.worldPos[1];
    g.drawImage(this.currentImage, x, y, this.width, this.height, null);
  }
}
