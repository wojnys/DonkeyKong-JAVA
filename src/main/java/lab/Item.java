
package lab;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.Set;

public class Item implements DrawableUpdatable {
    private Point2D position;
    private Point2D size;
    private Image image;

    public Item(Point2D position, Point2D size, String imagePath) {
        this.position = position;
        this.size = size;
        this.image =  new Image(getClass().getResource(imagePath).toExternalForm());
    }

    public void draw(GraphicsContext gc) {
//        gc.setFill(color);
//        gc.fillRect(position.getX(), position.getY(), size.getX(), size.getY());
        gc.drawImage(this.image, position.getX(), position.getY(), this.size.getX(), this.size.getY());
    }
    public void update(double deltaT, Set<KeyCode> pressedKeys) {}
}
