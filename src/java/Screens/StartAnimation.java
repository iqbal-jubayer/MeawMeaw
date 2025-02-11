package src.java.Screens;

import java.awt.Graphics2D;

import src.java.GamePanel;
import src.java.myUtils;
import src.java.Components.Sound;
import src.java.Components.TileManager;
import src.java.Components.TransitionAnimation;
import src.java.Entities.AppleCat;
import src.java.Entities.BananaCat;
import src.java.Entities.Ghast;

public class StartAnimation implements AnimationScreen {

  public int oneKeyFrame;
  GamePanel gamePanel;
  public Sound sound;
  public int keyFrameTimer;

  public TileManager tileManager;
  public AppleCat appleCat;
  public BananaCat bananaCat;
  public Ghast ghast;

  public TransitionAnimation transitIn = new TransitionAnimation(TransitionAnimation.TYPE_FADE_IN);
  public TransitionAnimation transitOut = new TransitionAnimation(TransitionAnimation.TYPE_FADE_OUT);

  public StartAnimation(GamePanel gamePanel) {
    this.gamePanel = gamePanel;

    this.appleCat = new AppleCat(0, 8 * this.gamePanel.tileSize, this.gamePanel.tileSize * 1,
        this.gamePanel.tileSize * 2,
        this.gamePanel);
    this.bananaCat = new BananaCat(0 - this.gamePanel.tileSize * 1, 8 * this.gamePanel.tileSize,
        this.gamePanel.tileSize * 1, this.gamePanel.tileSize * 2,
        this.gamePanel);
    this.appleCat.worldPos[0] = 0;
    this.appleCat.worldPos[1] = 0;
    this.tileManager = new TileManager(this.appleCat.getWorldPos());
    this.tileManager.loadMap(myUtils.mapPath + "animationMap.txt");
    this.appleCat.setTileManager(tileManager);
    this.bananaCat.setTileManager(tileManager);

    this.ghast = new Ghast(-this.gamePanel.tileSize * 3 - 10, 3 * this.gamePanel.tileSize, this.gamePanel.tileSize * 3,
        this.gamePanel.tileSize * 3, this.bananaCat);

    this.sound = new Sound(1);
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
        if ((this.bananaCat.x + this.bananaCat.width + this.appleCat.width) == 240) {
          this.oneKeyFrame++;
        }
        break;

      case 1:
        if (this.ghast.x == 450) {
          this.oneKeyFrame++;
        }
        break;
      case 2:
        this.oneKeyFrame++;
        break;
      case 3:
        if (this.ghast.x == this.appleCat.x + this.appleCat.width) {
          this.oneKeyFrame++;
        }
        break;

      case 4:
        this.oneKeyFrame++;
        break;

      case 5:
        this.oneKeyFrame++;
        break;

      case 6:
        this.sound.setFile(2);
        this.sound.play();
        this.oneKeyFrame++;
        break;

      case 7:
        if (this.ghast.y == 120) {
          this.oneKeyFrame++;
        }
        break;

      case 8:
        if (!this.sound.running) {
          this.oneKeyFrame++;
        }
        break;

      case 9:
        this.oneKeyFrame++;
        break;
      case 10:
        if (this.keyFrameTimer >= this.bananaCat.trembleCoolDown * 4 + 1) {
          this.oneKeyFrame++;
          this.keyFrameTimer = 0;
        }
        break;
      case 11:
        if (this.keyFrameTimer == myUtils.FPS) {
          this.sound.setFile(1);
          this.sound.play();
          this.oneKeyFrame++;
          this.keyFrameTimer = 0;
        }
        break;
      case 12:
        if (this.keyFrameTimer >= this.ghast.trembleCoolDown * 4 + 1) {
          this.oneKeyFrame++;
          this.keyFrameTimer = 0;
        }
        break;
      case 13:
        if (this.keyFrameTimer >= myUtils.FPS) {
          this.oneKeyFrame++;
        }
        break;
      case 14:
        this.oneKeyFrame++;
        break;
      case 15:
        if (this.ghast.x >= myUtils.screenWidth - this.ghast.width * 2) {
          this.oneKeyFrame++;
        }
        break;
      case 16:
        if (this.transitIn.done) {
          this.oneKeyFrame++;
          this.keyFrameTimer = 0;
        }
        break;
      case 17:
        if (this.keyFrameTimer >= myUtils.FPS * 1) {
          this.gamePanel.page++;
          this.sound.stop();
        }
        break;
      case -1:
        if (this.transitIn.done) {
          this.oneKeyFrame = 17;
        }
        break;

      default:
        break;
    }
  }

  @Override
  public void updateAnimation() {
    switch (this.oneKeyFrame) {
      case 0:
        this.bananaCat.x += this.bananaCat.velX;
        this.bananaCat.velX *= 0.2;

        this.appleCat.x += this.appleCat.velX;
        this.appleCat.velX *= 0.2;
        this.appleCat.goRight();
        this.bananaCat.goRight();
        this.transitOut.update();
        break;
      case 1:
        this.ghast.scream();

        this.ghast.x += this.ghast.velX;
        this.ghast.velX *= 0.2;
        this.ghast.velX += 1;
        break;
      case 2:
        this.ghast.currentImage = this.ghast.leftImage;
        this.ghast.faceDirection = -1;
        break;
      case 3:
        this.ghast.scream();
        this.ghast.x += this.ghast.velX * this.ghast.faceDirection;
        this.ghast.velX *= 0.9;
        this.ghast.velX += 0.05;

        this.ghast.y += this.ghast.velY;
        if (this.ghast.velY >= 1.3) {
          this.ghast.velY *= 0.5;
        }
        this.ghast.velY += 0.1;
        break;
      case 4:
        this.ghast.currentImage = this.ghast.rageLeftImage;
        break;
      case 5:
        this.appleCat.currentImage = this.appleCat.runImageLeft.get(0);
        this.appleCat.x = this.ghast.x + this.ghast.width / 2;
        this.appleCat.y = this.ghast.y - this.appleCat.height;
        this.bananaCat.isCrying = true;
        this.bananaCat.currentImage = this.bananaCat.cryImageRight.get(0);
        this.bananaCat.x -= (this.bananaCat.cryImageWidth - this.bananaCat.width) / 2;
        break;
      case 6:
        this.ghast.currentImage = this.ghast.leftImage;
        this.ghast.velX = 0;
        this.ghast.velY = 0;
        break;
      case 7:
        this.ghast.x += 1;
        this.appleCat.x += 1;
        this.ghast.y -= 1;
        this.appleCat.y -= 1;
        break;
      case 8:
        if (!this.sound.running) {
          this.sound = new Sound();
          this.sound.setFile(1);
          this.sound.play();
        }
        break;
      case 9:
        this.bananaCat.currentImage = this.bananaCat.runImageRight.get(0);
        this.bananaCat.x += (this.bananaCat.cryImageWidth - this.bananaCat.width) / 2;
        this.bananaCat.cryImageWidth = this.bananaCat.width;
        this.keyFrameTimer = 0;
        this.sound.setFile(6);
        break;
      case 10:
        if (!this.sound.running) {
          this.sound.play();
        }
        this.bananaCat.tremble();
        keyFrameTimer++;
        break;
      case 11:
        keyFrameTimer++;
        break;
      case 12:
        this.ghast.tremble();
        keyFrameTimer++;
        break;
      case 13:
        this.keyFrameTimer++;
        break;
      case 14:
        this.ghast.faceDirection *= -1;
        this.appleCat.faceDirection *= -1;
        this.ghast.currentImage = this.ghast.rightImage;
        this.appleCat.currentImage = this.appleCat.runImageRight.get(0);
        break;
      case 15:
        this.ghast.x += this.ghast.velX;
        this.ghast.velX *= 0.1;
        this.ghast.velX += 1;

        this.appleCat.x += this.appleCat.velX;
        this.appleCat.velX *= 0.1;
        this.appleCat.velX += 1;
        break;

      case 16:
        this.ghast.x += this.ghast.velX;
        this.ghast.velX *= 0.1;
        this.ghast.velX += 1;

        this.appleCat.x += this.appleCat.velX;
        this.appleCat.velX *= 0.1;
        this.appleCat.velX += 1;
        this.transitIn.update();
        break;
      case 17:
        this.keyFrameTimer++;
        break;
      case -1:
        this.transitIn.update();
        break;

      default:
        break;
    }
  }

  @Override
  public void eventControl() {
    if (this.gamePanel.keyHandler.keySpace) {
      this.oneKeyFrame = -1;
    }
  }

  @Override
  public void draw(Graphics2D g) {
    this.tileManager.draw(g);
    this.appleCat.draw(g);
    this.bananaCat.drawAnimationMode(g);
    this.ghast.draw(g);
    this.transitOut.draw(g);
    this.transitIn.draw(g);
  }
}