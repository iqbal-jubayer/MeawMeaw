package src.java.Entities;

import java.io.File;

import javax.imageio.ImageIO;

import src.java.myUtils;

public class Golem extends Enemy {

  public Golem(int x, int y, int width, int height, int bloockRange, int[] worldPos) {
    super(x, y, width, height, bloockRange, worldPos);
    this.direction = 1;

    try {
      this.imageRight = ImageIO.read(new File(myUtils.imgPath + "golem/idle.png"));
      this.imageLeft = myUtils.flip(this.imageRight);

      this.readImages(this.deathImageRight, myUtils.imgPath + "golem/death", false);
      this.readImages(this.deathImageLeft, myUtils.imgPath + "golem/death", true);

      if (this.direction == 1) {
        this.currentImage = this.imageRight;
      } else {
        this.currentImage = this.imageLeft;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
