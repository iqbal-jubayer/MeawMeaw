package src.java.Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import src.java.GamePanel;
import src.java.myUtils;
import src.java.Screens.Level;

public class BananaCat extends Player {
  public LinkedList<BufferedImage> cryImageLeft = new LinkedList<BufferedImage>();
  public float cryImageRightIndex = 0;
  public LinkedList<BufferedImage> cryImageRight = new LinkedList<BufferedImage>();
  public float cryImageLeftIndex = 0;

  public int cryImageWidth, cryImageHeight;
  public boolean isCrying;

  public LinkedList<BufferedImage> happyImage = new LinkedList<BufferedImage>();
  public float happyImageIndex = 0;

  public int happyImageWidth, happyImageHeight;
  public boolean isHappy;

  public BananaCat(int x, int y, int width, int height, GamePanel gamePanel) {
    super(x, y, width, height, gamePanel);

    this.readImages(this.runImageLeft, myUtils.imgPath + "banana_cat_run/", false);
    this.readImages(this.runImageRight, myUtils.imgPath + "banana_cat_run/", true);
    this.readImages(this.cryImageRight, myUtils.imgPath + "banana_cat_cry/", false);
    this.readImages(this.cryImageLeft, myUtils.imgPath + "banana_cat_cry/", true);
    this.readImages(this.happyImage, myUtils.imgPath + "happy_cat/", false);
    this.currentImage = this.runImageRight.get(0);

    this.cryImageHeight = height;
    this.cryImageWidth = (this.cryImageRight.get(0).getWidth() / this.runImageLeft.get(0).getWidth()) * width;

    this.happyImageHeight = height;
    this.happyImageWidth = (this.happyImage.get(0).getWidth() / this.runImageLeft.get(0).getWidth()) * width;
  }

  public BananaCat(int x, int y, int width, int height, GamePanel gamePanel, Level level) {
    super(x, y, width, height, gamePanel, level);

    this.readImages(this.runImageLeft, myUtils.imgPath + "banana_cat_run/", false);
    this.readImages(this.runImageRight, myUtils.imgPath + "banana_cat_run/", true);
    this.readImages(this.cryImageRight, myUtils.imgPath + "banana_cat_cry/", false);
    this.readImages(this.cryImageLeft, myUtils.imgPath + "banana_cat_cry/", true);
    this.readImages(this.happyImage, myUtils.imgPath + "happy_cat/", false);
    this.currentImage = this.runImageRight.get(0);

    this.cryImageHeight = height;
    this.cryImageWidth = (this.cryImageRight.get(0).getWidth() / this.runImageLeft.get(0).getWidth()) * width;

    this.happyImageHeight = height;
    this.happyImageWidth = (this.happyImage.get(0).getWidth() / this.runImageLeft.get(0).getWidth()) * width;
  }

  public void doHappyDance() {
    this.happyImageIndex += 0.1;
    if (this.happyImageIndex >= this.happyImage.size()) {
      this.happyImageIndex = 0;
    }
    this.currentImage = this.happyImage.get((int) this.happyImageIndex);
  }

  public void drawAnimationMode(Graphics2D g) {
    if (this.isCrying) {
      g.drawImage(this.currentImage, this.x, this.y, this.cryImageWidth, this.cryImageHeight, null);
    } else if (this.isHappy) {
      g.drawImage(this.currentImage, this.x, this.y, this.happyImageWidth, this.happyImageHeight, null);
    } else {
      g.drawImage(this.currentImage, this.x, this.y, this.width, this.height, null);
    }
  }
}
