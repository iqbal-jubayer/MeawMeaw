package src.java.Entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;

import src.java.myUtils;
import src.java.Components.Tile;
import src.java.Components.TileManager;

import java.awt.Graphics2D;

public abstract class Enemy {
  public int x;
  public int y;
  public int width;
  public int height;
  public int tileRange;
  public int direction = 1;
  public int tileCovered = 0;
  public int speed = 2;
  public boolean destroy;
  public boolean timesUp;
  public int[] worldPos;
  public int damage = 10;

  public BufferedImage imageRight;
  public BufferedImage imageLeft;
  public LinkedList<BufferedImage> deathImageRight = new LinkedList<BufferedImage>();
  public LinkedList<BufferedImage> deathImageLeft = new LinkedList<BufferedImage>();
  public double destroyIndex;

  public BufferedImage currentImage;

  public Enemy(int x, int y, int width, int height, int bloockRange, int[] worldPos) {
    this.x = x * myUtils.tileSize;
    this.y = y * myUtils.tileSize;
    this.width = width;
    this.height = height;
    this.tileRange = bloockRange;
    this.worldPos = worldPos;
  }

  public boolean getDestroy() {
    return this.destroy;
  }

  public void draw(Graphics2D g) {
    int x = this.worldPos[0] + this.x;
    int y = this.worldPos[1] + this.y;
    if (-myUtils.tileSize < x && x < myUtils.screenWidth + myUtils.tileSize && -myUtils.tileSize < y
        && y < myUtils.screenHeight + myUtils.tileSize) {
      g.drawImage(this.currentImage, x, y, this.width, this.height, null);
    }
  }

  public int[] getPosition() {
    int[] pos = { this.x + this.worldPos[0], this.y + this.worldPos[1] };
    return pos;
  }

  public int[] getSize() {
    int[] size = { this.width, this.height };
    return size;
  }

  public void readImages(LinkedList<BufferedImage> images, String path, boolean flips) {
    try {
      File tempFile = new File(path);
      File[] tempFiles = tempFile.listFiles();
      for (int i = 0; i < tempFiles.length; i++) {
        images.add(ImageIO.read(new File(tempFiles[i].toString())));
        if (flips) {
          images.set(i, myUtils.flip(images.get(i)));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void collisionMechanism(TileManager tileManager) {
    LinkedList<int[]> map = tileManager.getMap();
    LinkedList<Tile> tiles = tileManager.getTiles();

    for (int i = 0; i < map.size(); i++) {
      for (int j = 0; j < map.get(0).length; j++) {
        int index = map.get(i)[j];
        int x = myUtils.tileSize * j;
        int y = myUtils.tileSize * i;
        Tile t = tiles.get(index);
        if (t.solid) {
          if (x < this.x + this.width && this.x < x + myUtils.tileSize) {
            if (y < this.y + this.height && this.y + this.height < y + myUtils.tileSize / 2) {
              this.y = y - this.height;
            } else if (y + myUtils.tileSize / 2 < this.y && this.y < y + myUtils.tileSize) {
              this.y = y + myUtils.tileSize;
            }
          }

          if (y < this.y + this.height && this.y < y + myUtils.tileSize) {
            if (x < this.x + this.width && this.x + this.width < x + myUtils.tileSize / 2) {
              this.x = x - this.width;
              this.tileCovered = this.tileRange * myUtils.tileSize - this.tileCovered;
              this.direction *= -1;
            } else if (x + myUtils.tileSize / 2 < this.x && this.x < x + myUtils.tileSize) {
              this.x = x + myUtils.tileSize;
              this.tileCovered = this.tileRange * myUtils.tileSize - this.tileCovered;
              this.direction *= -1;
            }
          }
        }
      }
    }
  }

  public void update() {
    this.y += myUtils.gravity;
    if (this.timesUp) {
      if (this.direction == 1) {
        this.currentImage = this.deathImageRight.get((int) this.destroyIndex);
      } else {
        this.currentImage = this.deathImageLeft.get((int) this.destroyIndex);
      }

      if (this.destroyIndex <= this.deathImageLeft.size() - 1) {
        this.destroyIndex += 0.2;
      } else {
        this.destroy = true;
      }
    } else {
      this.x += this.speed * this.direction;
      this.tileCovered += this.speed;
      if (this.tileCovered >= this.tileRange * myUtils.tileSize) {
        this.tileCovered = 0;
        this.direction *= -1;
      }
      if (this.direction == 1) {
        this.currentImage = this.imageRight;
      } else {
        this.currentImage = this.imageLeft;
      }
    }
  }

}