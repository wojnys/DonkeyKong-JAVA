package lab;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Set;

public interface DrawableUpdatable {
    void draw(GraphicsContext gc);
    void update(double deltaT, Set<KeyCode> pressedKeys);
}
