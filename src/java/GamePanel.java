package src.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import src.java.Components.TileManager;
import src.java.Events.KeyHandler;
import src.java.Events.MouseHandler;
import src.java.Events.MouseMotionHandler;
import src.java.Screens.BossLevel;
import src.java.Screens.EndAnimation;
import src.java.Screens.Level;
import src.java.Screens.MainMenu;
import src.java.Screens.StartAnimation;

public class GamePanel extends JPanel implements Runnable {
  public int tileSize = myUtils.tileSize;
  public int screenWidth = myUtils.screenWidth;
  public int screenHeight = myUtils.screenHeight;
  public int FPS = myUtils.FPS;

  public int page = 0;

  public Thread gameThread;
  public KeyHandler keyHandler = new KeyHandler();
  public MouseHandler mouseHandler = new MouseHandler();
  public MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();

  public MainMenu mainMenu;
  public StartAnimation startAnimation;
  public Level level;
  public BossLevel bossLevel;
  public EndAnimation endAnimation;

  public TileManager tileManager;

  public GamePanel() {
    this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight));
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);
    this.addKeyListener(this.keyHandler);
    this.addMouseListener(this.mouseHandler);
    this.addMouseMotionListener(this.mouseMotionHandler);
    this.setFocusable(true);

    this.mainMenu = new MainMenu(this);
    this.startAnimation = new StartAnimation(this);
    this.level = new Level(this);
    this.bossLevel = new BossLevel(this);
    this.endAnimation = new EndAnimation(this);
  }

  public void startGameThread() {
    this.gameThread = new Thread(this);
    this.gameThread.start();
  }

  @Override
  public void run() {

    double drawInterval = 1000000000 / this.FPS;
    long lastTime = System.nanoTime();
    long currentTime;
    // int frame = 0;
    // long lastCheck = System.currentTimeMillis();

    while (true) {
      currentTime = System.nanoTime();

      if (currentTime - lastTime >= drawInterval) {
        this.update();
        this.repaint();
        this.revalidate();
        lastTime = currentTime;
        // frame++;
      }
      // if (System.currentTimeMillis() - lastCheck >= 1000) {
      // lastCheck = System.currentTimeMillis();
      // System.out.println("FPS: " + frame);
      // frame = 0;
      // }
    }
  }

  public void update() {
    switch (this.page) {
      case 0:
        this.mainMenu.update(this.level);
        break;
      case 1:
        this.startAnimation.update();
        break;
      case 2:
        this.level.update();
        break;
      case 3:
        this.bossLevel.update();
        break;
      case 4:
        this.endAnimation.update();
        break;

      default:
        break;
    }

  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    g.clearRect(0, 0, this.screenWidth, this.screenHeight);

    switch (this.page) {
      case 0:
        this.mainMenu.draw(g2);
        break;
      case 1:
        this.startAnimation.draw(g2);
        break;
      case 2:
        this.level.draw(g2);
        break;
      case 3:
        this.bossLevel.draw(g2);
        break;
      case 4:
        this.endAnimation.draw(g2);
        break;

      default:
        break;
    }

    g2.dispose();

  }
}