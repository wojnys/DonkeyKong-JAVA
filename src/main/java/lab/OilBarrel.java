package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

public class OilBarrel extends Item implements Collisionable {
    public OilBarrel(Point2D position, Point2D size, Image imagePath) {
        super(position, size, imagePath);
        this.position = position;
        this.size = size;
    }
}
