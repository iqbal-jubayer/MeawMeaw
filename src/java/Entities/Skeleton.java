package src.java.Entities;

import java.io.File;

import javax.imageio.ImageIO;

import src.java.myUtils;

public class Skeleton extends Enemy {

  public Skeleton(int x, int y, int width, int height, int bloockRange, int[] worldPos) {
    super(x, y, width, height, bloockRange, worldPos);
    this.direction = 1;

    try {
      this.imageRight = ImageIO.read(new File(myUtils.imgPath + "skeleton/skeleton.png"));
      this.imageLeft = myUtils.flip(this.imageRight);

      this.readImages(this.deathImageRight, myUtils.imgPath + "skeleton/skeleton_death", false);
      this.readImages(this.deathImageLeft, myUtils.imgPath + "skeleton/skeleton_death", true);

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
