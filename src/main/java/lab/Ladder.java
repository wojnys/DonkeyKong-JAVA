package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;

public class Ladder extends Item implements Collisionable {
    private Point2D position;
    private Point2D size;
    private Color color;

    public Ladder(Point2D position, Point2D size, Color color) {
        super(position, size, color);
        this.position = position;
        this.size = size;
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }

    public Rectangle2D getMiddleBoundingBox() {
        return new Rectangle2D(position.getX() + (size.getX() / 2), position.getY(), size.getX() - size.getX(), size.getY());
    }

    public Rectangle2D rightBoundingBox() {
        return new Rectangle2D(
                this.position.getX() + this.size.getX() / 2,  // Adjust the X coordinate to the middle of the player
                this.position.getY(),
                this.size.getX() / 2,  // Use half of the player's width
                this.size.getY() - 5 // because of collision with ground
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
                this.position.getY() + size.getY() - this.size.getY(),
                this.size.getX(),
                this.size.getY()
        );
    }

    public boolean intersects(Rectangle2D other) {
        return getBoundingBox().intersects(other);
    }
}
