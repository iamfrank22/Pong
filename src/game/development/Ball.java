package game.development;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball {
        public static final int DIAMETER = 30;
//        private static final int UPPER = 55;
//        private static final int LOWER = 345;
        public int x = 170 - DIAMETER;             //Initial positin sa ball
        public int y = 190 - DIAMETER;             //Initial postion sa ball
        public int xa = 1;              // speed sa ball
        public int ya = 1;              // speed sa ball
        private Game game;
        public int speedUpBall = 1;        //counts the no. of ball collisions, if it reaches to 4, speed will increase by 1
        public boolean collision = true;

        
        public Ball(Game game){
            this.game = game;
        }
        
        
        void move() throws InterruptedException{
            boolean changeDirection = true;
            if(x + xa < 0){
                xa = game.speed;
            }
            else if(x + xa > game.getWidth() - DIAMETER){
                xa = -game.speed;
            }else if((y + ya > game.getHeight() - DIAMETER) || (y + ya < 0)){ // if dili maigo sa ball ang racquet
                if(y + ya > game.getHeight() - DIAMETER){
                    game.playerScored = 1;
                    //score for Player 1
                }
                if(y + ya < 0){
                    game.playerScored = 2;      
                    //score for Player 2
                }
                
                Sound.GAMEOVER.play();
                Thread.sleep(1000);
                game.anotherRound();                //another set of game, after a player scored
                
            }
            else if(collision1() || collision2()){
                collision = true;
                if(collision1()){
                ya = game.speed;
                y = game.racquet1.getBottomY() + DIAMETER;
                
            } if(collision2()){
                collision = false;
                ya = -game.speed;
                y = game.racquet2.getBottomY() - DIAMETER;
            }
            speedUpBall++;
            
            if(speedUpBall == 4){
                speedUpBall = 0;
                game.speed++;
            }
            
            }else{
                changeDirection = false;
            }
            
            if(changeDirection)
                Sound.BALL.play();
            x = x + xa;
            y = y + ya;
            
            if(game.ballStop == true){
                game.gameOver();
            }
        }
            
        
        private boolean collision1() {
            return game.racquet1.getBounds().intersects(getBounds());
        }
        
        private boolean collision2() {
            return game.racquet2.getBounds().intersects(getBounds());
        }
        
        public void paint(Graphics2D g2d){
            g2d.fillOval(x, y, DIAMETER, DIAMETER);
        }
        
        public Rectangle getBounds(){
            return new Rectangle(x, y, DIAMETER, DIAMETER);
        }
}
