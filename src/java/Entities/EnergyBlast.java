package src.java.Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;

import src.java.myUtils;
import src.java.Components.Tile;
import src.java.Components.TileManager;

public class EnergyBlast {
  public int x;
  public int y;
  public int width = 16;
  public int height = 16;
  public int direction;
  public int travelX;
  public boolean timesUp;
  public int speed = 4;

  public LinkedList<BufferedImage> imagesRight = new LinkedList<BufferedImage>();
  public LinkedList<BufferedImage> imagesLeft = new LinkedList<BufferedImage>();
  public double count;

  public LinkedList<BufferedImage> destRight = new LinkedList<BufferedImage>();
  public LinkedList<BufferedImage> destLeft = new LinkedList<BufferedImage>();
  public double destCount;

  public BufferedImage currentImage;
  public boolean destroy;

  public int[] worldPos = { 0, 0 };

  public EnergyBlast(int x, int y, int direction, int[] worldPos) {
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.worldPos = worldPos;

    this.readImages(this.imagesRight, myUtils.imgPath + "energy_blast", false);
    this.readImages(this.imagesLeft, myUtils.imgPath + "energy_blast", true);
    this.readImages(this.destRight, myUtils.imgPath + "energy_blast_destroy", false);
    this.readImages(this.destLeft, myUtils.imgPath + "energy_blast_destroy", true);
    if (this.direction == 1) {
      this.currentImage = this.imagesRight.get(0);
    } else {
      this.currentImage = this.imagesLeft.get(0);
    }
  }

  public boolean getDestroy() {
    return this.destroy;
  }

  public void readImages(LinkedList<BufferedImage> images, String path, boolean flip) {
    try {
      File tempFile = new File(path);
      File[] tempFiles = tempFile.listFiles();
      for (int i = 0; i < tempFiles.length; i++) {
        images.add(ImageIO.read(tempFiles[i]));
        if (flip) {
          images.set(i, myUtils.flip(images.get(i)));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void update() {
    if (this.count <= this.imagesRight.size() - 1) {
      this.count += 0.2;
      this.x += 1 * this.direction;
      this.y -= 1;
      this.width += 2;
      this.height += 2;
    }
    this.travelX += this.speed;
    this.x += this.speed * this.direction;

    if (this.travelX >= 16 * 15) {
      this.timesUp = true;
    }

    if (this.timesUp) {
      if (this.destCount <= this.destRight.size() - 1) {
        this.destCount += 0.2;
      } else {
        this.destroy = true;
      }
      if (this.direction == 1) {
        this.currentImage = this.destRight.get((int) this.destCount);
      } else {
        this.currentImage = this.destLeft.get((int) this.destCount);
      }
    } else {
      if (this.direction == 1) {
        this.currentImage = this.imagesRight.get((int) this.count);
      } else {
        this.currentImage = this.imagesLeft.get((int) this.count);
      }
    }

  }

  public void draw(Graphics2D g) {
    g.drawImage(this.currentImage, this.x, this.y, this.width, this.height, null);
  }

  public void collide(Enemy entity) {
    int[] entityPos = entity.getPosition();
    int[] entitySize = entity.getSize();

    if (entityPos[1] <= this.y + this.height / 2 && this.y + this.height / 2 <= entityPos[1] + entitySize[1]) {
      if ((entityPos[0] + entitySize[0] / 2 <= this.x && this.x <= entityPos[0] + entitySize[0])
          || (entityPos[0] <= this.x + this.width && this.x + this.width <= entityPos[0] + entitySize[0] / 2)) {
        entity.timesUp = true;
        this.timesUp = true;
      }
    }
  }

  public void collide(TileManager tileManager) {
    for (int i = 0; i < tileManager.map.size(); i++) {
      for (int j = 0; j < tileManager.map.get(0).length; j++) {
        int index = tileManager.map.get(i)[j];
        int tileSize = myUtils.tileSize;
        int x = tileSize * j + this.worldPos[0];
        int y = tileSize * i + this.worldPos[1];
        Tile t = tileManager.tiles.get(index);
        if (t.solid && this.x - tileSize * 2 < x && x < this.x + tileSize * 2
            && this.y - tileSize * 2 < y && y < this.y + tileSize * 2) {
          if (y <= this.y + this.height / 2 && this.y + this.height / 2 <= y +
              tileSize) {
            if ((x < this.x + this.width && this.x + this.width < x + tileSize / 2)
                || (x + tileSize / 2 < this.x && this.x < x + tileSize)) {
              this.speed = 0;
              this.timesUp = true;
            }
          }
        }
      }
    }
  }

}