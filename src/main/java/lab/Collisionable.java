package lab;

import javafx.geometry.Rectangle2D;

public interface Collisionable {
    Rectangle2D getBoundingBox();

    Rectangle2D rightBoundingBox();

    Rectangle2D leftBoundingBox();

    Rectangle2D bottomBoundingBox();

    boolean intersects(Rectangle2D other);

}
