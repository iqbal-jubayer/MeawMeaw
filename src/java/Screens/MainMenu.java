package src.java.Screens;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import src.java.GamePanel;
import src.java.myUtils;
import src.java.Components.Button;
import src.java.Components.Sound;
import src.java.Components.TransitionAnimation;
import src.java.Events.KeyHandler;
import src.java.Events.MouseHandler;
import src.java.Events.MouseMotionHandler;

public class MainMenu {
  public BufferedImage mainMenuBg;

  public Sound sound;
  public boolean bg_music_on = false;
  public KeyHandler keyHandler;
  public MouseHandler mouseHandler;
  public MouseMotionHandler mouseMotionHandler;

  GamePanel gamePanel;
  public Button startButton;

  public boolean startTransition;
  public TransitionAnimation transitionInTransitionAnimation = new TransitionAnimation(
      TransitionAnimation.TYPE_FADE_IN);

  public boolean freeze;

  public MainMenu(GamePanel gamePanel) {
    this.gamePanel = gamePanel;

    this.sound = new Sound();
    this.keyHandler = this.gamePanel.keyHandler;
    this.mouseHandler = this.gamePanel.mouseHandler;
    this.mouseMotionHandler = this.gamePanel.mouseMotionHandler;

    try {
      mainMenuBg = ImageIO.read(new File(myUtils.imgPath + "/main_menu_background.jpg"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    startButton = new Button(myUtils.tileSize * 17, 4 * myUtils.tileSize, 5 * myUtils.tileSize,
        (int) (1.5 * myUtils.tileSize),
        myUtils.imgPath + "/start_img_idle.png",
        myUtils.imgPath + "/start_img_hover.png", this.gamePanel);

    this.sound.setFile(0);
  }

  public void update(Level level) {
    if (!this.freeze) {
      this.sound.loop();
    }
    this.startButton.update();
    if (this.startButton.isClicked() && !this.freeze) {
      this.freeze = true;
    }
    if (this.freeze) {
      this.transitionInTransitionAnimation.fadeIn();
      if (this.transitionInTransitionAnimation.done) {
        this.gamePanel.page++;
        this.sound.stop();
        this.transitionInTransitionAnimation = new TransitionAnimation(TransitionAnimation.TYPE_FADE_IN);
        this.freeze = false;
      }
    }
  }

  public void draw(Graphics2D g) {
    g.drawImage(mainMenuBg, 0, 0, myUtils.screenWidth, myUtils.screenHeight, null);
    startButton.draw(g);
    if (this.freeze) {
      this.transitionInTransitionAnimation.draw(g);
    }
  }
}