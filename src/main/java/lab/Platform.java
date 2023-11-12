
package lab;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Platform extends Item implements Collisionable {
    private Point2D position;
    private Point2D size;

    public Platform(Point2D position, Point2D size, String imagePaht) {
        super(position, size, imagePaht);
        this.position = position;
        this.size = size;
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }

    public Rectangle2D rightBoundingBox() {
        return new Rectangle2D(
                this.position.getX() + this.size.getX() / 2,  // Adjust the X coordinate to the middle of the player
                this.position.getY(),
                this.size.getX() / 2,  // Use half of the player's width
                this.size.getY() -  5 // because of collision with ground
        );
    }

    public Rectangle2D leftBoundingBox() {
        return new Rectangle2D(
                this.position.getX(),  // X coordinate remains the same as the player's left side
                this.position.getY(),
                this.size.getX() / 2,  // Use half of the player's width
                this.size.getY() - 5  // because of collision with ground
        );
    }
    public Rectangle2D bottomBoundingBox() {
        return new Rectangle2D(
                this.position.getX(),
                this.position.getY() + size.getY() -  this.size.getY(),
                this.size.getX(),
                this.size.getY()
        );
    }

    public boolean intersects(Rectangle2D other) {
        return getBoundingBox().intersects(other);
    }
//    public void update(double deltaT, Set<KeyCode> pressedKeys) {}
}