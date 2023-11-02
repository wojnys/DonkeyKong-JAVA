
package lab;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.Set;

public class Item implements DrawableUpdatable {
    private Point2D position;
    private Point2D size;
    private Color color;

    public Item(Point2D position, Point2D size, Color color) {
        this.position = position;
        this.size = size;
        this.color = color;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(position.getX(), position.getY(), size.getX(), size.getY());
    }
    public void update(double deltaT, Set<KeyCode> pressedKeys) {}
}
