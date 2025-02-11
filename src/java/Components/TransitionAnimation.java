package src.java.Components;
import java.awt.Color;
import java.awt.Graphics2D;

import src.java.myUtils;

public class TransitionAnimation {
  public double alpha = 0;
  public double sec = 1;
  Color c;
  public boolean fade; // F - fadeout; T - fadein

  public int[] rgb = { 0, 0, 0 };

  public boolean done;

  public static int TYPE_FADE_IN = 0;
  public static int TYPE_FADE_OUT = 1;
  public int type;

  public TransitionAnimation(int type) {
    this.type = type;
    if (this.type == TransitionAnimation.TYPE_FADE_IN) {
      this.alpha = 0;
    } else {
      this.alpha = 255;
    }
  }

  public TransitionAnimation() {
  }

  public void update() {
    if (this.type == TYPE_FADE_IN) {
      this.fadeIn();
    } else {
      this.fadeOut();
    }
  }

  public void fadeOut() {
    if (this.alpha > 0) {
      this.alpha -= 255 / (myUtils.FPS * sec);
    }else{
      this.done = true;
      this.alpha = 0;
    }
    c = new Color(this.rgb[0], this.rgb[1], this.rgb[2], (int) this.alpha);
  }

  public void fadeIn() {
    if (this.alpha < 255) {
      this.alpha += 255 / (myUtils.FPS * sec);
    }
    if (this.alpha >= 255) {
      this.done = true;
      this.alpha = 255;
    }
    c = new Color(this.rgb[0], this.rgb[1], this.rgb[2], (int) this.alpha);
  }

  public void draw(Graphics2D g) {
    g.setColor(c);
    g.fillRect(0, 0, myUtils.screenWidth, myUtils.screenHeight);
  }
}