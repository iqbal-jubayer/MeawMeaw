package src.java.Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import src.java.GamePanel;
import src.java.Screens.Level;
import src.java.myUtils;
import src.java.Components.Sound;
import src.java.Components.Tile;
import src.java.Components.TileManager;
import java.util.LinkedList;

public class Player {
  public int[] worldPos = { 0, 0 };
  public int x, y, width, height;
  public int idleX, idleY, idlePosX, idlePosY;
  public double velX, velY, acc = 1.5;
  public double gravity = 0.2, friction = 0.3, maxVelY = 3;
  public int jumpHeight = 7;

  public GamePanel gamePanel;

  public int hp = 100;
  public boolean moveWorld = false, moveWorldX = false, moveWorldY = false;
  public int faceDirection = 1;
  public boolean flyStatus = false;

  public boolean shiftHold = false, spaceHold = false;

  public boolean hurtStatus = false;
  public int hurtTimer, hurtHealTime = 2;

  public LinkedList<BufferedImage> runImageRight = new LinkedList<BufferedImage>();
  public float runImageRightIndex;
  public LinkedList<BufferedImage> runImageLeft = new LinkedList<BufferedImage>();
  public float runImageLeftIndex;
  public BufferedImage currentImage;

  public LinkedList<EnergyBlast> energyBlastList = new LinkedList<EnergyBlast>();
  public LinkedList<Enemy> entities = new LinkedList<Enemy>();

  public TileManager tileManager;

  public Sound walkSound = new Sound(3);

  public int trembleCoolDown = 10;
  public int trembleCount;
  public int trembleDirection = 1;

  public Level level;

  public Player(int x, int y, int width, int height, GamePanel gamePanel) {
    this.gamePanel = gamePanel;
    this.x = x;
    this.y = y;
    this.idleX = this.gamePanel.screenWidth / 4;
    this.idleY = this.gamePanel.screenHeight / 4;
    this.width = width;
    this.height = height;
    this.tileManager = this.gamePanel.tileManager;
  }

  public Player(int x, int y, int width, int height, GamePanel gamePanel, Level level) {
    this.gamePanel = gamePanel;
    this.level = level;
    this.x = x;
    this.y = y;
    this.idleX = this.gamePanel.screenWidth / 4;
    this.idleY = this.gamePanel.screenHeight / 4;
    this.width = width;
    this.height = height;
    this.tileManager = this.gamePanel.tileManager;
  }

  public int[] getWorldPos() {
    return this.worldPos;
  }

  public void setHealth(int hp) {
    this.hp = hp;
  }

  public void setEntities(LinkedList<Enemy> entities) {
    this.entities = entities;
  }

  public void setTileManager(TileManager tileManager) {
    this.tileManager = tileManager;
  }

  public void doFire() {
    if (this.gamePanel.keyHandler.keyShift) {
      if (!this.shiftHold) {
        EnergyBlast tempE;
        if (this.faceDirection == -1) {
          tempE = new EnergyBlast(this.x + this.width / 2, this.y + this.height / 2, this.faceDirection,
              this.worldPos);
        } else {
          tempE = new EnergyBlast(this.x + this.width / 2, this.y + this.height / 2, this.faceDirection,
              this.worldPos);
        }
        this.energyBlastList.add(tempE);
      }
      this.shiftHold = true;
    } else if (shiftHold) {
      shiftHold = false;
    }

  }

  public void goRight() {
    this.velX += this.acc;
    this.faceDirection = 1;

    if (!this.flyStatus) {
      if (!this.walkSound.running) {
        this.walkSound.play();
      }
      this.runImageRightIndex += 0.1;
    }
    if (this.runImageRightIndex > this.runImageRight.size()) {
      this.runImageRightIndex = 0;
    }
    this.currentImage = this.runImageRight.get((int) this.runImageRightIndex);

  }

  public void goLeft() {
    this.velX += this.acc;
    this.faceDirection = -1;
    if (!this.flyStatus) {
      if (!this.walkSound.running) {
        this.walkSound.play();
      }
      this.runImageLeftIndex += 0.1;
    }
    if (this.runImageLeftIndex > this.runImageLeft.size()) {
      this.runImageLeftIndex = 0;
    }
    this.currentImage = this.runImageLeft.get((int) this.runImageLeftIndex);

  }

  public void doJump() {
    if (this.gamePanel.keyHandler.keySpace && !this.flyStatus && !this.spaceHold) {
      this.spaceHold = true;
      this.velY = -this.jumpHeight;
    } else if (!this.gamePanel.keyHandler.keySpace && this.spaceHold) {
      this.spaceHold = false;
    }

  }

  public void doMove() {
    if (this.gamePanel.keyHandler.keyD) {
      this.goRight();
    } else if (this.gamePanel.keyHandler.keyA) {
      this.goLeft();
    }
  }

  public void tremble() {
    if (this.trembleCount != 0 && this.trembleCount % trembleCoolDown == 0) {
      this.x += 5 * this.trembleDirection;
      this.trembleDirection *= -1;
    }
    this.trembleCount++;
  }

  public void hurtUpdate() {
    if (this.hurtStatus) {
      this.hurtTimer++;
      if (this.hurtTimer > this.hurtHealTime * myUtils.FPS) {
        this.hurtStatus = false;
        this.hurtTimer = 0;
      }
    }
  }

  public void worldMove() {
    if (this.moveWorldX) {
      this.worldPos[0] -= (int) this.velX * this.faceDirection;
      if (this.worldPos[0] > 0) {
        this.worldPos[0] = 0;
        this.moveWorldX = false;
        this.idlePosX = -1;
      } else if (this.worldPos[0] < -(this.tileManager.mapDimension[0] * this.gamePanel.tileSize
          - this.gamePanel.screenWidth)) {
        this.worldPos[0] = -(this.tileManager.mapDimension[0] * this.gamePanel.tileSize - this.gamePanel.screenWidth);
        this.moveWorldX = false;
        this.idlePosX = 1;
      }
    } else {
      this.x += (int) this.velX * this.faceDirection;
      if (this.idlePosX == 1) {
        if (this.x <= this.idleX) {
          this.moveWorldX = true;
          this.x = this.idleX;
        }
      } else {
        if (this.x >= this.idleX) {
          this.moveWorldX = true;
          this.x = this.idleX;
        }
      }
      if (this.x <= 0) {
        this.x = 0;
      } else if (this.x >= this.gamePanel.screenWidth - this.width) {
        this.x = this.gamePanel.screenWidth - this.width;
      }
    }

    if (this.moveWorldY) {
      this.worldPos[1] -= (int) this.velY;
      if (this.worldPos[1] > -5) {
        this.worldPos[1] = -5;
        this.moveWorldY = false;
        this.idlePosY = -1;
      } else if (this.worldPos[1] < -(this.tileManager.mapDimension[1] * this.gamePanel.tileSize
          - this.gamePanel.screenHeight)) {
        this.worldPos[1] = -(this.tileManager.mapDimension[1] * this.gamePanel.tileSize - this.gamePanel.screenHeight);
        this.moveWorldY = false;
        this.idlePosY = 1;
      }
    } else {
      this.y += (int) this.velY;
      if (this.idlePosY == 1) {
        if (this.y < this.idleY) {
          this.moveWorldY = true;
          this.y = this.idleY;
        }
      } else if (this.y > this.idleY) {
        this.moveWorldY = true;
        this.y = this.idleY;
      }
      if (this.y < 0) {
        this.y = 0;
        this.velY = 0;
      } else if (this.y > this.gamePanel.screenHeight - this.height) {
        this.y = this.gamePanel.screenHeight - this.height;
        this.velY = 0;
      }
    }
  }

  public void movementUpdate() {
    this.worldMove();

    this.velX *= this.friction;
    if (this.velY < this.maxVelY) {
      this.velY += this.gravity;
    }
  }

  public void fireUpdate() {
    for (int i = 0; i < this.energyBlastList.size(); i++) {
      EnergyBlast e = this.energyBlastList.get(i);
      if (e.getDestroy()) {
        this.energyBlastList.remove(i);
      }
      e.update();
      for (int j = 0; j < this.entities.size(); j++) {
        e.collide(this.entities.get(j));
      }
      e.collide(this.tileManager);
    }
  }

  public void readImages(LinkedList<BufferedImage> images, String path, boolean flips) {
    try {
      File tempFile = new File(path);
      File[] tempFiles = tempFile.listFiles();
      for (int i = 0; i < tempFiles.length; i++) {
        images.add(ImageIO.read(tempFiles[i]));
        if (flips) {
          images.set(i, myUtils.flip(images.get(i)));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void update() {
    this.hurtUpdate();
    this.movementUpdate();

    for (int i = 0; i < this.entities.size(); i++) {
      this.playerEntityCollide(this.entities.get(i));
    }

    this.fireUpdate();
    this.control();
    this.collisionMechanism(this.tileManager);
  }

  public void draw(Graphics2D g) {
    g.drawImage(this.currentImage, this.x, this.y, this.width, this.height, null);
  }

  public void drawEnergyBlast(Graphics2D g) {
    for (int i = 0; i < this.energyBlastList.size(); i++) {
      EnergyBlast e = this.energyBlastList.get(i);
      e.draw(g);
    }
  }

  public void drawHpBar(Graphics2D g) {
    if (this.hp >= 50) {
      g.setColor(Color.GREEN);
    } else if (25 <= this.hp && this.hp < 50) {
      g.setColor(Color.ORANGE);
    } else {
      g.setColor(Color.RED);
    }
    g.fillRect(10, 10, (int) ((this.hp / 100.0) * (8 * this.gamePanel.tileSize)), this.gamePanel.tileSize / 2);
    g.setColor(Color.BLACK);
    g.drawRect(10, 10, (8 * this.gamePanel.tileSize), this.gamePanel.tileSize / 2);
  }

  public void control() {
    doMove();
    doFire();
    doJump();

    if (this.gamePanel.keyHandler.keyW) {
      this.velY = -5;
    }
    if (this.gamePanel.keyHandler.keyS) {
      this.velY = 5;
    }
  }

  public int collisionX(int x, int y) {
    if (y < this.y + this.height - 10 && this.y < y + this.gamePanel.tileSize) {
      if (x < this.x + this.width && this.x + this.width < x + this.gamePanel.tileSize / 2) {
        return -1;
      } else if (x + this.gamePanel.tileSize / 2 < this.x && this.x < x + this.gamePanel.tileSize) {
        return 1;
      }
    }
    return 0;
  }

  public int collideY(int x, int y) {
    if (x < this.x + this.width && this.x < x + this.gamePanel.tileSize) {
      if (y < this.y + this.height && this.y + this.height < y + this.gamePanel.tileSize / 2) {
        return -1;
      } else if (y + this.gamePanel.tileSize / 2 < this.y && this.y < y + this.gamePanel.tileSize) {
        return 1;
      }
    }
    return 0;
  }

  public void collisionMechanism(TileManager tileManager) {

    this.flyStatus = true;
    for (int i = 0; i < tileManager.map.size(); i++) {
      for (int j = 0; j < tileManager.map.get(0).length; j++) {
        int index = tileManager.map.get(i)[j];
        int x = this.gamePanel.tileSize * j + this.worldPos[0];
        int y = this.gamePanel.tileSize * i + this.worldPos[1];
        Tile t = tileManager.tiles.get(index);

        if (this.x - this.gamePanel.tileSize * 2 < x && x < this.x + this.gamePanel.tileSize * 2
            && this.y - this.gamePanel.tileSize * 2 < y && y < this.y + this.gamePanel.tileSize * 2) {

          int cy = this.collideY(x, y);
          if (t.status.equals("solid")) {
            if (cy == -1) {
              if (this.moveWorldY) {
                this.worldPos[1] = this.y + this.height - this.gamePanel.tileSize * i;
              } else {
                this.y = y - this.height;
              }
              this.flyStatus = false;
            } else if (cy == 1) {
              if (this.moveWorldY) {
                this.worldPos[1] = this.y - this.gamePanel.tileSize - this.gamePanel.tileSize * i;
              } else {
                this.y = y + this.gamePanel.tileSize;
              }
              this.velY = 0;
            }
          } else if (t.status.equals("end")) {
            if (cy == 1 || cy == -1) {
              this.level.running = false;
            }
          }

          int cx = this.collisionX(x, y);
          if (t.status.equals("solid")) {
            if (cx == -1) {
              if (this.moveWorldX) {
                this.worldPos[0] = this.x + this.width - this.gamePanel.tileSize * j;
              } else {
                this.x = x - this.width;
              }
            } else if (cx == 1) {
              if (this.moveWorldX) {
                this.worldPos[0] = this.x - this.gamePanel.tileSize - this.gamePanel.tileSize
                    * j;
              } else {
                this.x = x + this.gamePanel.tileSize;
              }
            }
          } else if (t.status.equals("end")) {
            if (cx == 1 || cx == -1) {
              this.level.running = false;
            }
          }
        }
      }
    }
  }

  public void playerEntityCollide(Enemy entity) {
    int x = entity.x + this.worldPos[0];
    int y = entity.y + this.worldPos[1];
    if (y < this.y + this.height && this.y < y + this.gamePanel.tileSize) {
      if ((x < this.x + this.width && this.x + this.width < x + this.gamePanel.tileSize / 2)
          || (x + this.gamePanel.tileSize / 2 < this.x && this.x < x + this.gamePanel.tileSize)) {
        if (!this.hurtStatus) {
          this.hurtStatus = true;
          this.setHealth(this.hp - entity.damage);
        }
      }
    }
  }
}