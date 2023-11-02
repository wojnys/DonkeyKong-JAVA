package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.Set;

public abstract class Human implements DrawableUpdatable, Collisionable {
    protected final Game game;
    protected Point2D position;
    protected Point2D size;
    protected Point2D velocity;
    protected Image image;

    public Human(Game game, Point2D position, Point2D size, Point2D velocity, String imagePath) {
        this.game = game;
        this.position = position;
        this.size = size;
        this.velocity = velocity;
        this.image = new Image(getClass().getResource(imagePath).toExternalForm());
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(this.image, position.getX(), position.getY(), this.size.getX(), this.size.getY());
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(this.position.getX(), this.position.getY(), this.size.getX(), this.size.getY());
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
    public Rectangle2D topBoundingBox() {
        return new Rectangle2D(
                this.position.getX(),
                this.position.getY(),
                this.size.getX(),
                this.size.getY() - this.size.getY() + 2
        );
    }

    public boolean intersects(Rectangle2D other) {
        return getBoundingBox().intersects(other);
    }

    public void update(double deltaT, Set<KeyCode> pressedKeys) {
    }
}

