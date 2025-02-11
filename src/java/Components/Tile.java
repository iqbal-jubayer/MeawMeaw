package src.java.Components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import src.java.myUtils;

public class Tile {
  public int x;
  public int y;
  public int width = myUtils.tileSize;
  public int height = myUtils.tileSize;
  public BufferedImage image;
  public int inX, inY;
  public boolean solid;
  public String status = "non-solid";

  public Tile() {
    try {
      image = ImageIO.read(new File(myUtils.imgPath + "newTile.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g) {
    g.drawImage(this.image.getSubimage(16 * this.inX, 16 * this.inY, 16, 16), this.x, this.y, this.width, this.height,
        null);
  }

  public void draw(int x, int y, Graphics2D g) {
    g.drawImage(this.image, x, y, this.width, this.height,
        null);
  }
}

class SolidTile extends Tile {

  public SolidTile(int inX, int inY) {
    this.inX = inX;
    this.inY = inY;
    this.image = this.image.getSubimage(16 * this.inX, 16 * this.inY, 16, 16);
    this.solid = true;
    this.status = "solid";
  }
}

class NonSolidTile extends Tile {

  public NonSolidTile(int inX, int inY) {
    this.inX = inX;
    this.inY = inY;
    this.image = this.image.getSubimage(16 * this.inX, 16 * this.inY, 16, 16);
  }
}

class EndTile extends Tile{
  public EndTile(int inX, int inY) {
    this.inX = inX;
    this.inY = inY;
    this.image = this.image.getSubimage(16 * this.inX, 16 * this.inY, 16, 16);
    this.status = "end";
  }
}