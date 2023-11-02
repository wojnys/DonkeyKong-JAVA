package lab;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class Enemy extends Human implements LadderCollisionable {
    private final double speedX;
    private final double speedY;
    private boolean moveRight = true;
    private boolean moveLeft = true;
    private boolean moveBottom = true;  // for goiing down by letter
    private boolean moveUp = false;  // is just for climbing by ladders
    private boolean moveDown = false; // is just for goigindown by ladders
    private double gravity = 3;
    private boolean applyGravity = true;
    public Enemy(Game game, Point2D position, Point2D size, Point2D velocity, String imagePath) {
        super(game, position, size, velocity, imagePath);
        this.speedX = this.velocity.getX();
        this.speedY = this.velocity.getY();
    }

    public void applyGravity() {
        if (moveBottom && this.applyGravity) {
            this.position = this.position.add(0, this.gravity);
        }
    }

    public void climbingAllowed() {
    }

    public void climbingStopped() {
    }

   public boolean intersectsWithLadder(Ladder ladder){
        return false;
   }
    public boolean intersectsWithLadderForGoingDown(Ladder ladder) {
        return false;
    }
    public void goingDownAllowed() {  }
    public void goingDownStopped() { }

    public void enemyMovement() {
        if(moveRight) {
            this.velocity = new Point2D(Math.abs(this.speedX), 0);
        }
        if(moveLeft) {
            this.velocity = new Point2D(Math.abs(this.speedX) * (-1), 0);
        }
    }

    @Override
    public void update(double deltaT, Set<KeyCode> pressedKeys) {
        for (Platform platform : game.getPlatforms()) {
            if (platform.intersects( this.rightBoundingBox())) {
                this.moveRight = false;
            }
            if (platform.intersects( this.leftBoundingBox())) {
                this.moveLeft = false;
            }
            if (platform.intersects( this.bottomBoundingBox())) {
                this.moveBottom = false;
            }
        }
        if(!moveBottom ) { //pouze pokud nekoliduje s dolni casti platformy
            this.applyGravity();
        }

        // Handle enenmy movement
        this.enemyMovement();
        this.position = this.position.add(this.velocity.getX() * deltaT, this.velocity.getY() * deltaT);

    }
}
