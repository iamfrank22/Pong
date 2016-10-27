package game.development;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {
  
  Ball ball = new Ball(this);
  Racquet racquet1 = new Racquet(this, 30);
  Racquet racquet2 = new Racquet(this, 340);
  public static final int WIDTH = 350;
  public static final int HEIGHT = 430;
  public int speed = 1;
  public int oneScore = 0;
  public int twoScore = 0;
  public byte playerScored = 0;
  public boolean ballStop = false;
  
  
  
  public Game() {
    addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
   }

   @Override
   public void keyReleased(KeyEvent e) {
     racquet1.xa = 0;
     racquet2.xa = 0;
   }

   @Override
   public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_A)
      racquet1.xa = -speed;
    if (e.getKeyCode() == KeyEvent.VK_D)
      racquet1.xa = speed;
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
      racquet2.xa = -speed;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      racquet2.xa = speed;
    if (e.getKeyCode() == KeyEvent.VK_V){
        if(racquet1.speedUp){
            if(ball.collision){
                racquet1.powerSpeedUp();
            }
        }
    }
    if (e.getKeyCode() == KeyEvent.VK_P){
        if(racquet2.speedUp){
            if(ball.collision){
                racquet2.powerSpeedUp();
            }
        }
    }
   }
  });
  setFocusable(true);
  Sound.BACK.loop();
 }
 
  private void move() throws InterruptedException {
    ball.move();
    racquet1.move();
    racquet2.move();
  }

 @Override
 public void paint(Graphics g) {
  super.paint(g);
  Graphics2D g2d = (Graphics2D) g;
  g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    RenderingHints.VALUE_ANTIALIAS_ON);
  //g2d.fillOval(x, y, 30, 30);
  g.setColor(Color.GREEN);
  ball.paint(g2d);
  g.setColor(Color.RED);
  racquet1.paint(g2d); 
  g.setColor(Color.magenta);
  racquet2.paint(g2d);
  
  g2d.setColor(Color.BLACK);
  g2d.setFont(new Font("Verdana", Font.BOLD, 20));
  g2d.drawString(String.valueOf(oneScore), 10, 30);
  g2d.drawString(String.valueOf(twoScore), 10, 330);
  
  g2d.setColor(Color.BLUE);
  g2d.setFont(new Font("Verdana", Font.BOLD, 8));
  g2d.drawString("Press A to move left--"
          + "Press D to move right--Press V for Power Up", 5, 10);
  
  g2d.setColor(Color.BLUE);
  g2d.setFont(new Font("Verdana", Font.BOLD, 8));
  g2d.drawString("Press <- to move left--"
          + "Press -> to move right--Press P for Power Up", 5, 370);
  
  if(oneScore == 3 || twoScore == 3){
    ball.xa = 0;
    ball.ya = 0;
    ballStop = true;
    }
 }


 
 public void gameOver() throws InterruptedException{
  Sound.BACK.stop();

    int champs = 0;
    int losers = 0;
    if(oneScore == 3){
        champs = 1;
    } if(twoScore == 3){
        champs = 2;
    } if (oneScore != 3){
        losers = 1;
    } if(twoScore != 3){
        losers = 2;
    }
    JOptionPane.showMessageDialog(this, "Player " + champs + " defeated Player " + losers,
                    "Game Over", JOptionPane.YES_NO_OPTION);
   
    if(JOptionPane.showConfirmDialog(this, "Want a REMATCH?", "Game Over", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION){
        System.exit(ABORT);
    }
    
    else{
        resetGame(); //I-reset ang game if mu rematch
        anotherRound();
    }
 }
  
 // reset everything if mu rematch
 public void resetGame(){
        ball.xa = 0;
        ball.ya = 0;
        oneScore = 0;
        twoScore = 0;
        ballStop = false;
    }
 public void anotherRound(){
     ball.speedUpBall = 1;
     ball.xa = 1;
     ball.ya = 1;
     speed = 1;
     ball.x = 150;
     racquet1.x = 120;
     racquet2.x = 120;
     
     if(playerScored == 1){
         oneScore = oneScore + 1;
         ball.y = racquet1.getBottomY();
         playerScored = 0;
     } else if(playerScored == 2){
         twoScore = twoScore + 1;
         ball.y = racquet2.getBottomY() - (ball.DIAMETER - 5);
         playerScored = 0;
     }
 }



 
 public static void main(String[] args) throws InterruptedException {
    JFrame frame = new JFrame("Simple Ping Pong");
    Game game = new Game();
    frame.add(game);
    frame.setSize(WIDTH, HEIGHT);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    while (true) {
     game.move();
     game.repaint();
     Thread.sleep(10);
    }
   }
  }