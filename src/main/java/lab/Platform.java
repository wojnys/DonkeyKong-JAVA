
package lab;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Platform extends Item implements Collisionable {
    private Point2D position;
    private Point2D size;

    public Platform(Point2D position, Point2D size, Image imagePaht) {
        super(position, size, imagePaht);
        this.position = position;
        this.size = size;
    }
}