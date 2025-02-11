package src.java.Screens;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import src.java.GamePanel;
import src.java.myUtils;
import src.java.Components.Sound;
import src.java.Components.TileManager;
import src.java.Components.TransitionAnimation;
import src.java.Entities.AppleCat;
import src.java.Entities.BananaCat;

public class EndAnimation implements AnimationScreen {
  public int oneKeyFrame;
  public Sound sound;
  public int keyFrameTimer;
  public GamePanel gamePanel;

  public TileManager tileManager;
  public BananaCat bananaCat;
  public AppleCat appleCat;

  public TransitionAnimation transitionOut = new TransitionAnimation(TransitionAnimation.TYPE_FADE_OUT);
  public TransitionAnimation transitionIn = new TransitionAnimation(TransitionAnimation.TYPE_FADE_IN);

  public BufferedImage endCredit;
  public boolean drawEndCredit;
  public int endCreditX, endCreditY, endCreditWidth, endCreditHeight;

  public EndAnimation(GamePanel gamePanel) {
    this.gamePanel = gamePanel;

    this.tileManager = new TileManager();
    this.bananaCat = new BananaCat(3 * this.gamePanel.tileSize, 8 * this.gamePanel.tileSize, this.gamePanel.tileSize,
        this.gamePanel.tileSize * 2, this.gamePanel);
    this.appleCat = new AppleCat(27 * this.gamePanel.tileSize, 8 * this.gamePanel.tileSize, this.gamePanel.tileSize,
        this.gamePanel.tileSize * 2, this.gamePanel);
    this.appleCat.faceDirection *= -1;
    this.appleCat.currentImage = this.appleCat.runImageLeft.get(0);
    this.tileManager.loadMap(myUtils.mapPath + "animationMap.txt");

    this.sound = new Sound(0);

    try {
      this.endCredit = ImageIO.read(new File(myUtils.imgPath + "EndCredit.png"));
      this.endCreditY = this.gamePanel.screenHeight;
      this.endCreditWidth = this.gamePanel.screenWidth;
      this.endCreditHeight = (int) (((float) this.endCredit.getHeight() / this.endCredit.getWidth())
          * this.endCreditWidth);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void update() {
    this.updateKeyFrame();
    this.updateAnimation();
    this.eventControl();
  }

  @Override
  public void updateKeyFrame() {
    switch (this.oneKeyFrame) {
      case 0:
        if (this.appleCat.x <= this.gamePanel.tileSize * 22 && this.transitionOut.done) {
          this.oneKeyFrame++;
          this.appleCat.runImageLeftIndex = 0;
          this.appleCat.currentImage = this.appleCat.runImageLeft.get((int) this.appleCat.runImageLeftIndex);
        }
        break;

      case 1:
        if (this.keyFrameTimer >= this.gamePanel.FPS) {
          this.oneKeyFrame++;
          this.keyFrameTimer = 0;
          this.appleCat.velX = 0;
          this.sound.setFile(7);
          this.sound.loop();
        }
        break;

      case 2:
        if (this.keyFrameTimer >= this.gamePanel.FPS * 2.5 && !this.sound.running) {
          this.oneKeyFrame++;
          this.bananaCat.isHappy = false;
          this.sound.stop();
          this.sound.setFile(0);
        }
        break;
      case 3:
        if (this.bananaCat.x + this.bananaCat.width >= this.appleCat.x) {
          this.oneKeyFrame++;
          this.sound.loop();
          this.keyFrameTimer = 0;
        }
        break;
      case 4:
        if (this.keyFrameTimer >= this.gamePanel.FPS * 2) {
          this.oneKeyFrame++;
        }
        break;
      case 5:
        if (this.transitionIn.done) {
          this.oneKeyFrame++;
          this.drawEndCredit = true;
        }
        break;
      case 6:
        if (this.endCreditY <= -this.endCreditHeight + this.gamePanel.screenHeight) {
          this.oneKeyFrame++;
        }
        break;

      default:
        break;
    }
  }

  public int abc = 0;

  @Override
  public void updateAnimation() {
    switch (this.oneKeyFrame) {
      case 0:
        this.transitionOut.update();
        this.appleCat.x += this.appleCat.velX * this.appleCat.faceDirection;
        this.appleCat.velX *= 0.2;
        this.appleCat.goLeft();
        this.keyFrameTimer = 0;
        break;

      case 1:
        this.keyFrameTimer++;
        break;

      case 2:
        this.bananaCat.isHappy = true;
        this.bananaCat.doHappyDance();
        this.keyFrameTimer++;
        break;
      case 3:
        this.bananaCat.x += (int) this.bananaCat.velX * this.bananaCat.faceDirection;
        this.bananaCat.velX *= 0.05;
        this.bananaCat.goRight();

        this.appleCat.x += (int) this.appleCat.velX * this.appleCat.faceDirection;
        this.appleCat.velX *= 0.05;
        this.appleCat.goLeft();
        break;
      case 4:
        this.keyFrameTimer++;
        break;
      case 5:
        this.transitionIn.update();
        break;
      case 6:
        this.endCreditY -= 1;
        break;

      default:
        break;
    }
  }

  @Override
  public void eventControl() {
  }

  @Override
  public void draw(Graphics2D g) {
    this.tileManager.draw(g);
    this.bananaCat.draw(g);
    this.appleCat.draw(g);
    this.transitionOut.draw(g);
    this.transitionIn.draw(g);
    if (this.drawEndCredit) {
      g.drawImage(this.endCredit, this.endCreditX, this.endCreditY, this.endCreditWidth, this.endCreditHeight, null);
    }

  }
}