package src.java.Screens;

import java.awt.Graphics2D;
import java.util.LinkedList;

import src.java.GamePanel;
import src.java.myUtils;
import src.java.Components.Sound;
import src.java.Components.TileManager;
import src.java.Components.TransitionAnimation;
import src.java.Entities.BananaCat;
import src.java.Entities.Enemy;
import src.java.Entities.Golem;
import src.java.Entities.Skeleton;
import src.java.Events.KeyHandler;

public class Level {
  public BananaCat player;
  public LinkedList<Enemy> entities = new LinkedList<Enemy>();
  TileManager tileManager;

  public int tileSize = myUtils.tileSize;

  public KeyHandler keyHandler;

  public boolean running = true;
  GamePanel gamePanel;

  public TransitionAnimation fadeOutTransitionAnimation;
  public TransitionAnimation fadeInTransitionAnimation;

  public Sound bgSound = new Sound(8);

  public Level(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
    this.keyHandler = this.gamePanel.keyHandler;

    this.fadeOutTransitionAnimation = new TransitionAnimation(TransitionAnimation.TYPE_FADE_OUT);
    this.fadeInTransitionAnimation = new TransitionAnimation(TransitionAnimation.TYPE_FADE_IN);

    this.player = new BananaCat(100, 0, this.tileSize * 1, this.tileSize * 2, this.gamePanel, this);
    this.tileManager = new TileManager(this.player.getWorldPos());

    this.entities.add(new Skeleton(30, 0, this.tileSize * 1, this.tileSize * 2, 10, this.player.getWorldPos()));
    this.entities.add(new Golem(41, 0, this.tileSize * 2, this.tileSize * 2, 6, this.player.getWorldPos()));

    this.player.setEntities(entities);
    this.player.setTileManager(tileManager);

    this.fadeOutTransitionAnimation.sec = 1;
  }

  public void update() {
    if (!this.fadeOutTransitionAnimation.done) {
      this.fadeOutTransitionAnimation.fadeOut();
    } else if (!this.bgSound.running) {
      this.bgSound.play();
    }

    if (this.running) {
      this.player.update();

      for (int i = 0; i < this.entities.size(); i++) {
        Enemy e = this.entities.get(i);
        e.update();
        e.collisionMechanism(this.tileManager);
        if (e.getDestroy()) {
          this.entities.remove(i);
        }
      }
    } else {
      this.fadeInTransitionAnimation.fadeIn();
      if (this.fadeInTransitionAnimation.done) {
        this.bgSound.stop();
        this.gamePanel.page = 4;
      }
    }
  }

  public void draw(Graphics2D g) {
    this.tileManager.draw(g);
    for (int i = 0; i < this.entities.size(); i++) {
      this.entities.get(i).draw(g);
    }
    this.player.draw(g);
    this.player.drawEnergyBlast(g);
    this.player.drawHpBar(g);
    this.fadeOutTransitionAnimation.draw(g);
    this.fadeInTransitionAnimation.draw(g);
  }

  // public void start() {
  // this.running = true;
  // }
}