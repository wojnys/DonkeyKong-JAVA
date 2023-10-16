package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ground {
    private Point2D position;
    private Point2D size;

    public Ground(Point2D position, Point2D size) {
        this.position = position;
        this.size = size;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(this.position.getX(), this.position.getY(), this.size.getX(), this.position.getY());
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(this.position.getX(), this.position.getY(), this.size.getX(), this.size.getY());
    }
}
