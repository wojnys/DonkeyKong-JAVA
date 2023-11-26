package lab;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Hammer extends Item implements PlayerCollisionable {
    private Image hammerImageGif = new Image(getClass().getResourceAsStream("/lab/hammer-gif.gif"));

    public Hammer(Point2D position, Point2D size, Image imagePath) {
        super(position, size, imagePath);
        this.position = position;
    }

    public boolean intersectsWithPlayer(Player player) {
        return this.getBoundingBox().intersects(player.getBoundingBox());
    }

    public void hammerWasTakenByPLayer(Player pLayer) {
        this.image = hammerImageGif;
        this.position = new Point2D(pLayer.getPosition().getX() + pLayer.getSize().getX() / 4d, pLayer.getPosition().getY() - this.size.getY() / 2);
    }

}
