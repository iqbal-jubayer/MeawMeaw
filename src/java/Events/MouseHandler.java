package src.java.Events;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
  public int mouseX, mouseY;
  public boolean mouse_clicked;

  @Override
  public void mouseClicked(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    this.mouseX = e.getX();
    this.mouseY = e.getY();
    mouse_clicked = true;
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    mouse_clicked = false;
  }

}