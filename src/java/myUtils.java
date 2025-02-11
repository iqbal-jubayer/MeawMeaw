package src.java;

import java.awt.image.BufferedImage;

public class myUtils {
  public static int tileSize = 32;
  public static int gravity = 2;
  public static int cols = 25;
  public static int rows = 12;
  public static int screenWidth = tileSize * cols;
  public static int screenHeight = tileSize * rows;
  public static int FPS = 120;

  public static String imgPath = "./src/assets/media/img/";
  public static String audioPath = "./src/assets/media/audio/";
  public static String mapPath = "./src/assets/maps/";

  public static BufferedImage flip(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        newImage.setRGB(width - 1 - x, y, image.getRGB(x, y));
      }
    }
    return newImage;
  }
}
