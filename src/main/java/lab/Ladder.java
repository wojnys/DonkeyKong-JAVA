package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Ladder extends Item  {
    private Point2D position;
    private Point2D size;

    public Ladder(Point2D position, Point2D size, Image image) {
        super(position, size, image);
        this.position = position;
        this.size = size;
    }
    public Point2D getSize() {
        return this.size;
    }
    public Point2D getPosition() {
        return this.position;
    }
}
