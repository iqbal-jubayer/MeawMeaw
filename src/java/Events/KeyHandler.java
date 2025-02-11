package src.java.Events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

  public boolean keyW, keyA, keyS, keyD, keyX, keyShift, keySpace, keyQ;

  @Override
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_W) {
      keyW = true;
    } else if (code == KeyEvent.VK_A) {
      keyA = true;
    } else if (code == KeyEvent.VK_S) {
      keyS = true;
    } else if (code == KeyEvent.VK_D) {
      keyD = true;
    } else if (code == KeyEvent.VK_X) {
      keyX = true;
    } else if (code == KeyEvent.VK_SHIFT) {
      keyShift = true;
    } else if (code == KeyEvent.VK_SPACE) {
      keySpace = true;
    } else if (code == KeyEvent.VK_Q) {
      keyQ = true;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_W) {
      keyW = false;
    } else if (code == KeyEvent.VK_A) {
      keyA = false;
    } else if (code == KeyEvent.VK_S) {
      keyS = false;
    } else if (code == KeyEvent.VK_D) {
      keyD = false;
    } else if (code == KeyEvent.VK_X) {
      keyX = false;
    } else if (code == KeyEvent.VK_SHIFT) {
      keyShift = false;
    } else if (code == KeyEvent.VK_SPACE) {
      keySpace = false;
    } else if (code == KeyEvent.VK_Q) {
      keyQ = false;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

}