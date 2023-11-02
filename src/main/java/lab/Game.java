package lab;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final double width;
    private final double height;
    private Set<KeyCode> pressedKeys;

    private List<DrawableUpdatable> objects;

    public Game(double width, double height, Scene scene, Set<KeyCode> pressedKeys) {
        this.width = width;
        this.height = height;

        objects = new ArrayList<>();
        objects.add(new Princess(this, new Point2D(this.getWidth() - 400, 140), new Point2D(60, 60), new Point2D(100, 60), "/lab/princess.gif"));
        objects.add(new Platform(new Point2D(0, 450), new Point2D(this.getWidth() - 100d, 30), Color.BROWN));
        objects.add(new Platform(new Point2D(100, 600), new Point2D(this.getWidth(), 30), Color.BROWN));
        objects.add(new Platform(new Point2D(0, 750), new Point2D(this.getWidth(), 30), Color.BROWN));
        //ladder
        objects.add(new Ladder(new Point2D(300, 600), new Point2D(35, 150), Color.BLACK));
        objects.add(new Ladder(new Point2D(450, 600), new Point2D(35, 150), Color.BLACK));
        //princess platform
        objects.add(new Platform(new Point2D(this.getWidth() - 400, 200), new Point2D(400, 40), Color.BROWN));
        //player
        objects.add(new Player(this, new Point2D(200, height-200), new Point2D(40, 60), new Point2D(100, 60), "/lab/mario-cropped.gif"));

        //added enemies
        objects.add(new Enemy(this, new Point2D(200, height-200), new Point2D(40, 60), new Point2D(100, 60), "/lab/enemy.png"));
    }


    public void draw(GraphicsContext gc) {
        for(DrawableUpdatable object: this.objects) {
            object.draw(gc);
        }
    }


    //pokud bude kolidovat dolni cast hrace se zebrikem a zaroven horni cast = muze lest nahoru
    //pokud bude kolidovat dolni cast se zebrikem, ale horni cast nebude kolidovat = muze lest dolu
    public void update(double deltaT, Set<KeyCode> pressedKeys) {
        for(DrawableUpdatable object: this.objects) {
            object.update(deltaT, pressedKeys);

            // mozna zkusit predelat na Collisioanble pri kolizi mezi hracem a zebrikem
            if(object instanceof LadderCollisionable) {
                boolean isIntersecting = false;
                for(DrawableUpdatable object2: objects) {
                    if(object2 instanceof Ladder) {
                        if(((LadderCollisionable) object).intersectsWithLadder(((Ladder) object2))) {
                            isIntersecting = true;
                            break;
                        }
                    }
                }
                if(isIntersecting) {
                    ((LadderCollisionable)object).climbingAllowed();
                }
                if(!isIntersecting) {
                    ((LadderCollisionable)object).climbingStopped();
                }
            }
        }
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public List<Platform> getPlatforms() {
        return objects.stream()
                .filter(object -> object instanceof Platform)
                .map(object -> (Platform) object)
                .collect(Collectors.toList());
    }
}
