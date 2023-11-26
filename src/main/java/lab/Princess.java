package lab;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Princess extends Human implements Collisionable, PlayerCollisionable{
    public Princess(Game game, Point2D position, Point2D size, Point2D velocity, Image imagePath) {
        super(game,position,size,velocity,imagePath);
    }
    public boolean intersectsWithPlayer(Player player) {
        if(this.getBoundingBox().intersects(player.getBoundingBox())) {
            player.setScore(5000);
        }
        return this.getBoundingBox().intersects(player.getBoundingBox());
    }
}
