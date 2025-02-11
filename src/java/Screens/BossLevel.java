package src.java.Screens;

import java.awt.Graphics2D;

import src.java.GamePanel;
import src.java.myUtils;
import src.java.Components.TileManager;
import src.java.Entities.BananaCat;
import src.java.Entities.Ghast;

public class BossLevel {
  public TileManager tileManager;
  public GamePanel gamePanel;

  public BananaCat bananaCat;
  public Ghast ghast;

  public BossLevel(GamePanel gamePanel) {

    this.gamePanel = gamePanel;

    this.bananaCat = new BananaCat(this.gamePanel.tileSize * 3, 0, this.gamePanel.tileSize, this.gamePanel.tileSize * 2,
        this.gamePanel);
    this.tileManager = new TileManager(this.bananaCat.getWorldPos());
    this.tileManager.loadMap(myUtils.mapPath + "animationMap.txt");
    this.ghast = new Ghast(this.gamePanel.screenWidth - this.gamePanel.tileSize * 5,
        this.tileManager.mapDimension[1] * this.gamePanel.tileSize - this.gamePanel.tileSize * 10,
        this.gamePanel.tileSize * 3, this.gamePanel.tileSize * 3, this.bananaCat);

    this.ghast.faceDirection = -1;
    this.ghast.currentImage = this.ghast.leftImage;
    this.bananaCat.setTileManager(this.tileManager);
  }

  public void update() {
    this.bananaCat.update();
    this.ghast.update();
  }

  public void draw(Graphics2D g) {
    this.tileManager.draw(g);
    this.bananaCat.draw(g);
    this.bananaCat.drawEnergyBlast(g);
    this.bananaCat.drawHpBar(g);
    this.ghast.draw(g);
  }
}
