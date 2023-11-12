package lab;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class Enemy extends Human implements LadderCollisionable, BarrelCollisionable {
    private  double speedX;
    private  double speedY;
    private boolean moveRight = true;
    private boolean moveLeft = true;
    private boolean moveBottom = true;  // for goiing down by letter
    private boolean moveUp = false;  // is just for climbing by ladders
    private boolean moveDown = false; // is just for goigindown by ladders
    private double gravity = 3;
    private boolean applyGravity = true;
    private boolean basicProperites = true;
    private boolean neccesseryMovenent = true;
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
    public void goingDownAllowed() {

    }
    public void goingDownStopped() { }

    public void enemyMovement() {
        if(moveRight && neccesseryMovenent && basicProperites) {
            if(this.position.getX() + this.size.getX() >= this.game.getWidth()) {
                this.speedX *= (-1);
            }
            if(this.position.getX() <= 0) {
                this.speedX *=(-1);
            }
            this.velocity = new Point2D(this.speedX, 0);
        }
    }

    @Override
    public void update(double deltaT, Set<KeyCode> pressedKeys) {
        this.applyGravity();
        this.moveBottom = true;
        for (Platform platform : game.getPlatforms()) {
            if (platform.intersects( this.rightBoundingBox())) {
                this.moveRight = false;
                System.out.println("right collision");
            }
            if (platform.intersects( this.leftBoundingBox())) {
                this.moveLeft = false;
                System.out.println("left collision");
            }
            if (platform.intersects( this.bottomBoundingBox())) {
                this.moveBottom = false;
            }
        }

        // Handle enenmy movement
        this.enemyMovement();
        this.position = this.position.add(this.velocity.getX() * deltaT, this.velocity.getY() * deltaT);
    }

    public boolean intersectsWithBarrrel(OilBarrel oilBarrel) {
        return this.getBoundingBox().intersects(oilBarrel.getBoundingBox());
    }

    public void updateEnemySkill() {
        System.out.println("enemy was update - he can go up");

    }
}
