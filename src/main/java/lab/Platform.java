
package lab;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Platform {
    private Point2D position;
    private Point2D size;
    private Color color;

    public Platform(Point2D position, Point2D size, Color color) {
        this.position = position;
        this.size = size;
        this.color = color;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(position.getX(), position.getY(), size.getX(), size.getY());
    }

    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }

    public boolean intersects(Rectangle2D other) {
        return getBoundingBox().intersects(other);
    }
}