package src.java.Entities;

import src.java.GamePanel;
import src.java.myUtils;

public class AppleCat extends Player {
  public AppleCat(int x, int y, int width, int height, GamePanel gamePanel) {
    super(x, y, width, height, gamePanel);

    this.readImages(this.runImageLeft, myUtils.imgPath + "apple_cat_run/", false);
    this.readImages(this.runImageRight, myUtils.imgPath + "apple_cat_run/", true);
    this.currentImage = this.runImageRight.get(0);
  }
}