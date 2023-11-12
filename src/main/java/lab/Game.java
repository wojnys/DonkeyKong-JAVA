package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final double width;
    private final double height;
    private Set<KeyCode> pressedKeys;

    private List<DrawableUpdatable> objects;

    private GameListener gameListener = new EmptyGameListener();

    private Image enemyImage = new Image(getClass().getResourceAsStream("/lab/circleGif.gif"));

    public Game(double width, double height, Set<KeyCode> pressedKeys) throws IOException {
        this.width = width;
        this.height = height;

        objects = new ArrayList<>();
        objects.add(new Princess(this, new Point2D(this.getWidth() - 400, 0), new Point2D(60, 60), new Point2D(100, 60), "/lab/princess.gif"));
        objects.add(new Platform(new Point2D(0, 195), new Point2D(this.getWidth() - 100, 30), "/lab/platform.png"));
        objects.add(new Platform(new Point2D(100, 330), new Point2D(this.getWidth() - 100, 30), "/lab/platform.png"));
        objects.add(new Platform(new Point2D(0, 465), new Point2D(this.getWidth() - 100, 30), "/lab/platform.png"));
        objects.add(new Platform(new Point2D(0, 600), new Point2D(this.getWidth(), 30), "/lab/platform.png"));
        //ladder
        objects.add(new Ladder(new Point2D(200, 330), new Point2D(20, 135), "/lab/ladder.png"));
        objects.add(new Ladder(new Point2D(900, 465), new Point2D(20, 135), "/lab/ladder.png"));
        objects.add(new Ladder(new Point2D(800, 195), new Point2D(20, 135), "/lab/ladder.png"));
        //princess platform
        objects.add(new Platform(new Point2D(this.getWidth() - 400, 50), new Point2D(400, 40), "/lab/platform.png"));
        //player
        objects.add(new Player(this, new Point2D(0, height-50), new Point2D(33, 45), new Point2D(100, 60), "/lab/mario-cropped.gif"));
        // load barel which change enemy desing and movement
        objects.add(new OilBarrel(new Point2D(0, this.getHeight() - 60 - 30),new Point2D(50, 60), "/lab/oilBarel.png"));

    }


    public void draw(GraphicsContext gc) throws IOException {
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
            if(object instanceof BarrelCollisionable) {
                for(DrawableUpdatable object2: objects) {
                    if(object2 instanceof OilBarrel ) {
                        if(((BarrelCollisionable) object).intersectsWithBarrrel(((OilBarrel) object2))) {
                            ((BarrelCollisionable) object).updateEnemySkill();
                        }
                    }
                }
            }

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

    public void spawnEnemies() {
        objects.add(new Enemy(this, new Point2D(200, 0), new Point2D(30, 30), new Point2D(100, 60), "/lab/circlePngEnemy.png"));
        System.out.println("new object was created");

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

    public void setGameListener(GameListener listener) {
        this.gameListener = listener;
    }
}
