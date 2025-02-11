package src.java.Components;

import java.util.LinkedList;
import java.util.Scanner;

import src.java.myUtils;

import java.awt.Graphics2D;
import java.io.File;

public class TileManager {
  public LinkedList<Tile> tiles = new LinkedList<Tile>();
  public LinkedList<int[]> map = new LinkedList<int[]>();
  public int[] worldPos = { 0, 0 };
  public int[] mapDimension = { 0, 0 };

  public LinkedList<int[]> getMap() {
    return this.map;
  }

  public LinkedList<Tile> getTiles() {
    return this.tiles;
  }

  public TileManager(int[] worldPos) {
    this.worldPos = worldPos;
    this.addTiles();
    this.loadMap(myUtils.mapPath + "map.txt");
  }

  public TileManager() {
    this.addTiles();
    this.loadMap(myUtils.mapPath + "map.txt");
  }

  public void addTiles() {
    this.tiles.add(new SolidTile(0, 0));
    this.tiles.add(new NonSolidTile(1, 0));
    this.tiles.add(new NonSolidTile(2, 0));
    this.tiles.add(new NonSolidTile(3, 0));
    this.tiles.add(new NonSolidTile(4, 0));
    this.tiles.add(new NonSolidTile(5, 0));
    this.tiles.add(new NonSolidTile(6, 0));
    this.tiles.add(new NonSolidTile(7, 0));
    this.tiles.add(new NonSolidTile(8, 0));
    this.tiles.add(new NonSolidTile(9, 0));
    this.tiles.add(new NonSolidTile(10, 0));
    this.tiles.add(new NonSolidTile(0, 1));
    this.tiles.add(new NonSolidTile(1, 1));
    this.tiles.add(new NonSolidTile(2, 1));
    this.tiles.add(new NonSolidTile(0, 2));
    this.tiles.add(new NonSolidTile(1, 2));
    this.tiles.add(new NonSolidTile(2, 2));

    this.tiles.add(new NonSolidTile(3, 1));
    this.tiles.add(new EndTile(4, 1));
    this.tiles.add(new NonSolidTile(3, 2));
    this.tiles.add(new EndTile(4, 2));
  }

  public void loadMap(String path) {
    this.map = new LinkedList<int[]>();
    try {

      File file = new File(path);
      Scanner reader = new Scanner(file);
      while (reader.hasNextLine()) {
        String[] tempArr = reader.nextLine().split(" ");
        int[] tempArrInt = new int[tempArr.length];
        for (int i = 0; i < tempArrInt.length; i++) {
          tempArrInt[i] = Integer.parseInt(tempArr[i]);
        }
        this.map.add(tempArrInt);
      }
      reader.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
    this.mapDimension[0] = this.map.get(0).length;
    this.mapDimension[1] = this.map.size();
  }

  public void draw(Graphics2D g) {
    for (int i = 0; i < this.map.size(); i++) {
      for (int j = 0; j < this.map.get(0).length; j++) {
        int index = this.map.get(i)[j];
        int x = myUtils.tileSize * j + this.worldPos[0];
        int y = myUtils.tileSize * i + this.worldPos[1];
        if (-myUtils.tileSize <= x && x <= myUtils.screenWidth + myUtils.tileSize) {
          this.tiles.get(index).draw(x, y, g);
        }
      }
    }
  }
}
