package lab;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Kong extends Human {
    static private Point2D KongPosition;
    public Kong(Game game, Point2D position, Point2D size, Point2D velocity, Image imagePath) {
        super(game, position, size, velocity, imagePath);
        KongPosition = position;
    }

    public static Point2D getKongPosition() {
        return KongPosition;
    }
}
