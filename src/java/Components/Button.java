package src.java.Components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import src.java.GamePanel;

public class Button {
  public int x, y, width, height;
  public BufferedImage idleImage, hoverImage, currentImage;
  public boolean hover;
  public GamePanel gamePanel;

  public Button(int x, int y, int width, int height, String idleImagePath, String hoverImagePath, GamePanel gamePanel) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.gamePanel = gamePanel;

    try {
      this.idleImage = ImageIO.read(new File(idleImagePath));
      this.hoverImage = ImageIO.read(new File(hoverImagePath));
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.currentImage = idleImage;
  }

  public void update() {
    if (this.x <= this.gamePanel.mouseMotionHandler.mouseX
        && this.gamePanel.mouseMotionHandler.mouseX <= this.x + this.width
        && this.y <= this.gamePanel.mouseMotionHandler.mouseY
        && this.gamePanel.mouseMotionHandler.mouseY <= this.y + this.height) {
      this.hover = true;
    } else {
      this.hover = false;
    }
  }

  public boolean isClicked() {
    if (this.x <= this.gamePanel.mouseMotionHandler.mouseX
        && this.gamePanel.mouseMotionHandler.mouseX <= this.x + this.width
        && this.y <= this.gamePanel.mouseMotionHandler.mouseY
        && this.gamePanel.mouseMotionHandler.mouseY <= this.y + this.height
        && this.gamePanel.mouseHandler.mouse_clicked) {
      return true;
    }
    return false;
  }

  public void draw(Graphics2D g) {
    if (this.hover) {
      g.drawImage(this.hoverImage, this.x, this.y, this.width, this.height, null);
    } else {
      g.drawImage(this.idleImage, this.x, this.y, this.width, this.height, null);
    }
  }

}
