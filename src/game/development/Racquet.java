package game.development;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Racquet {
  private static final int WIDTH = 50;
  private static final int HEIGHT = 10;
  public boolean speedUp = true;
  public int x = 120;
  public int y;
  public int xa = 0;
  private Game game;
  
  public Racquet(Game game, int y) {
    this.game= game;
    this.y = y;
  }
  
  public void move() {
    if (x + xa > 0 && x + xa < game.getWidth()- WIDTH)
      x = x + xa;
  }
  
  public void paint(Graphics2D g) {
    g.fillRect(x, y, WIDTH, HEIGHT);
  }
  
  public Rectangle getBounds() {
    return new Rectangle(x, y, WIDTH, HEIGHT);
  }
  
  public int getBottomY(){
      if( y == 30)
        return y + HEIGHT;
      else
        return y - HEIGHT;
  }
  
  public void powerSpeedUp(){
      game.speed+=10;
      speedUp = false;
  }
}