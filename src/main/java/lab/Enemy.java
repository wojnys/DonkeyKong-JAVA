package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Random;
import java.util.Set;

public class Enemy extends Human implements LadderCollisionable, BarrelCollisionable, PlayerCollisionable {
    private double speedX;
    private double speedY;
    private boolean moveRight = false;
    private boolean moveLeft = true;
    private boolean moveBottom = true;  // for going down by letter
    private boolean moveDownByLadder = false; // is just for goigindown by ladders
    private double gravity = 170;
    private boolean applyGravity = true;
    private boolean choseLadder;
    private int counter = 0;

    public Enemy(Game game, Point2D position, Point2D size, Point2D velocity, Image imagePath) {
        super(game, position, size, velocity, imagePath);
        this.speedX = this.velocity.getX();
        this.speedY = this.velocity.getY();
    }

    public void applyGravity(double deltaT) {
        if (moveBottom && this.applyGravity && !this.moveDownByLadder) {
            this.position = this.position.add(0, this.gravity * deltaT);
        }
    }

    public void choseRandomLadder() { // When number is 1 so enemy can go down by ladder
        if (counter <= 1) {
            Random rand = new Random();
            int num = rand.nextInt(0, 2);
            this.choseLadder = num == 1;
        }

        counter++;
    }

    public boolean wasLadderChosenByEnemy() {
        return this.choseLadder;
    }

    public void climbingAllowed() {
    }

    public void climbingStopped() {
    }

    @Override
    public Rectangle2D getBoundingBox() {
        if (this.moveLeft) {
            return new Rectangle2D(this.position.getX(), this.position.getY(), this.size.getX() / 2, this.size.getY()); // go down by ladder in center
        }
        if (this.moveRight) {
            return new Rectangle2D(this.position.getX() + (this.size.getX() / 2), this.position.getY(), this.size.getX(), this.size.getY()); // go down by ladder in center
        }
        return null;
    }

    public boolean intersectsWithLadder(Ladder ladder) {
        return getBoundingBox().intersects(ladder.getMiddleBoundingBox());
    }

    public boolean intersectsWithPlayer(Player player) {
        return getBoundingBox().intersects(player.getBoundingBox());
    }

    public void goingDownAllowed() {
        this.moveDownByLadder = true;
        this.velocity = new Point2D(0, this.speedY);
        counter = 0;
    }

    public void goingDownStopped() {
        this.moveDownByLadder = false;
        counter = 1;
    }

    public void enemyMovement() {
        if (!moveDownByLadder) {
            if (this.position.getX() + this.size.getX() >= this.game.getWidth()) {
                this.speedX *= (-1);
                this.moveRight = true;
                this.moveLeft = false;
            }
            if (this.position.getX() <= 0) {
                this.speedX *= (-1);
                this.moveLeft = true;
                this.moveRight = false;
            }
            this.velocity = new Point2D(this.speedX, 0);
        }
    }

    @Override
    public void update(double deltaT, Set<KeyCode> pressedKeys) {
        this.applyGravity(deltaT);
        this.moveBottom = true;
        for (Platform platform : game.getPlatforms()) {
            if (platform.intersects(this.bottomBoundingBox())) {
                this.moveBottom = false;
            }
        }
        // Handle enenmy movement
        this.enemyMovement();
        this.position = this.position.add(this.velocity.getX() * deltaT, this.velocity.getY() * deltaT);
    }

    public boolean intersectsWithBarrel(OilBarrel oilBarrel) {
        return this.getBoundingBox().intersects(oilBarrel.getBoundingBox());
    }
}
