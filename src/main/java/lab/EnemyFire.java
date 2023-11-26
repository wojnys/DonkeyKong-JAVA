package lab;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class EnemyFire extends Enemy {
    private double speedX;

    public EnemyFire(Game game, Point2D position, Point2D size, Point2D velocity, Image imagePath) {
        super(game,position, size, velocity, imagePath);
        this.speedX = this.velocity.getX();
    }
    @Override
    public void enemyMovement() {
            if(this.position.getX() + this.size.getX() >= this.game.getWidth()) {
                this.speedX *= (-1);
            }
            if(this.position.getX() <= 0) {
                this.speedX *=(-1);
            }
            this.velocity = new Point2D(this.speedX, 0);
        }
}
