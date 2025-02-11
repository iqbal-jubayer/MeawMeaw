package src.java.Screens;

import java.awt.Graphics2D;

public interface AnimationScreen {

  public void updateKeyFrame();

  public void updateAnimation();

  public void eventControl();

  public void draw(Graphics2D g);
}
