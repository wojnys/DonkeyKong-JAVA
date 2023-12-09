package lab;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final double width;
    private final double height;
    private boolean restartGameAllowed = false;
//    private Set<KeyCode> pressedKeys;

    private List<DrawableUpdatable> objects;
    private List<DrawableUpdatable> allHammer;
    private LoadJsonData loadJsonData = null;

    private GameListener gameListener = new EmptyGameListener();
    private final List<DrawableUpdatable> found = new ArrayList<DrawableUpdatable>();

    Image enemyImage = new Image(getClass().getResourceAsStream("/lab/enemy-1.gif"));

    public Game(double width, double height, Set<KeyCode> pressedKeys, int LEVEL_NUMBER) throws IOException {
        objects = new ArrayList<>();

        //load objects for current LEVEL FROM JSON FILE
        this.loadJsonData = new LoadJsonData(this);
        objects = this.loadJsonData.loadLevel(LEVEL_NUMBER);

        allHammer = new ArrayList<>(objects); // hammers

        this.width = width;
        this.height = height;

        this.gameListener.stateChanged(0, 3);
    }


    public void draw(GraphicsContext gc) throws IOException {
        for (DrawableUpdatable object : this.objects) {
            object.draw(gc);
        }
    }

    public void update(double deltaT, Set<KeyCode> pressedKeys) throws IOException {
        boolean winGame = false;
        for (DrawableUpdatable object : this.objects) {
            object.update(deltaT, pressedKeys);

            // get player object
            Player player = (Player) objects.stream()
                    .filter(obj -> obj instanceof Player)
                    .findFirst()
                    .orElse(null);

            // Check for Player collisions with Princess and with Enemy
            if (object instanceof PlayerCollisionable playerCollisionalObject) {
                boolean isIntersecting = objects.stream()
                        .anyMatch(obj2 -> ((PlayerCollisionable) object).intersectsWithPlayer(player));

                if (isIntersecting) {
                    if (playerCollisionalObject instanceof Enemy enemy) {
                        assert player != null;
                        if (player.getHammer()) {
                            found.add(enemy);
                            System.out.println("Player killed the Enemy");
                            player.setScore(300);
                            this.gameListener.stateChanged(player.getScore(), player.getHp());
                        } else {
                            System.out.println("Collision WITH ENEMY");
                            player.setScore(-100);
                            this.gameListener.stateChanged(player.getScore(), player.getHp() - 1);
                            restartGameAllowed = true;
                        }
                    }
                    if (playerCollisionalObject instanceof Princess) {
                        System.out.println("Collision WITH PRINECSS");
                        this.gameListener.stateChanged(player.getScore(), player.getHp() - 1);
                        winGame = true;
                    }
                    if (playerCollisionalObject instanceof Hammer hammer) {
                        System.out.println("COLLISION WITH HAMMER");
                        assert player != null;
                        Duration hammerVisibilityDuration = Duration.seconds(7);
                        // Create a Timeline for the jump animation
                        Timeline hammerVisibilityTimeline = new Timeline(
                                new KeyFrame(Duration.ZERO, e -> {
                                    player.setHammer(true);
                                    hammer.hammerWasTakenByPLayer(player);
                                }),
                                new KeyFrame(hammerVisibilityDuration, e -> {
                                    // This code executes after setted time,
                                    player.setHammer(false);
                                    found.add(hammer);
                                })
                        );
                        hammerVisibilityTimeline.setCycleCount(1); // Play the animation once
                        hammerVisibilityTimeline.play();
                    }
                }
            }

            // Check collision between Barrel and Enemy
            if (object instanceof BarrelCollisionable barrelCollisionalObject) {
                boolean isIntersecting = objects.stream()
                        .filter(obj -> obj instanceof OilBarrel)
                        .anyMatch(obj2 -> (barrelCollisionalObject).intersectsWithBarrel((OilBarrel) obj2));
                if (isIntersecting) {
                    this.found.add(object);  // V prubehu iterace nelze smazat object - proto musim objekt ulozit do ArraListu a na konci iteraci vsehcny objekty, ktere jsou v tomto ArraListu smazat
                }
            }

            // Check collision between Player, Enemy  and  Ladder
            if (object instanceof LadderCollisionable) {
                boolean isIntersecting = objects.stream()
                        .filter(obj -> obj instanceof Ladder)
                        .anyMatch(obj2 -> ((LadderCollisionable) object).intersectsWithLadder((Ladder) obj2));

                if (isIntersecting) {
                    Ladder currentLadder = (Ladder) objects.stream()
                            .filter(obj -> obj instanceof Ladder)
                            .filter(obj2 -> ((LadderCollisionable) object).intersectsWithLadder((Ladder) obj2))
                            .findFirst()
                            .orElse(null);

                    if (currentLadder != null) {
                        // Check player collision with ladder
                        if (object instanceof Player playerObject && !playerObject.getHammer()) {
                            if (((Player) object).getSize().getY() + ((Player) object).getPosition().getY() >= currentLadder.getPosition().getY()) {
                                ((LadderCollisionable) object).climbingAllowed();
                            }
                            if (((Player) object).getSize().getY() + ((Player) object).getPosition().getY() <= currentLadder.getPosition().getY() + currentLadder.getSize().getY()) {
                                ((LadderCollisionable) object).goingDownAllowed();
                            } else {
                                playerObject.goingDownStopped();
                            }
                            // Check enemy collision with ladder
                        } else if (object instanceof Enemy enemy) {
                            enemy.choseRandomLadder();
                            if (((Enemy) object).getPosition().getY() + ((Enemy) object).getSize().getY() <= currentLadder.getPosition().getY() + currentLadder.getSize().getY()) {
                                if (enemy.wasLadderChosenByEnemy()) {
                                    ((LadderCollisionable) object).goingDownAllowed();
                                }
                            } else {
                                ((LadderCollisionable) object).goingDownStopped();
                            }
                        }
                    }
                } else {
                    if (object instanceof Player playerObject) {
                        playerObject.climbingStopped();
                        playerObject.goingDownStopped();
                    } else if (object instanceof Enemy enemyObject) {
                        enemyObject.goingDownStopped();
                    }
                }
            }
        }

        if (restartGameAllowed) {
            this.restartGame();
        }
        if (winGame) {
            this.gameListener.gameWin();
        }
        objects.removeAll(found);
        found.clear();
    }

    public void spawnEnemies() {
        Random rand = new Random();
        int randomValue = rand.nextBoolean() ? 1 : -1;
        objects.add(new Enemy(this, new Point2D(Kong.getKongPosition().getX(), Kong.getKongPosition().getY()), new Point2D(25, 25), new Point2D(160 * randomValue, 60), enemyImage));
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

    public void restartGame() throws IOException {
        List<DrawableUpdatable> found = new ArrayList<>();
        for (DrawableUpdatable object : this.objects) {
            if (object instanceof Enemy) {
                found.add(object);
            }
            if (object instanceof Player player) {
                if (player.getHp() == 1) {
                    this.gameListener.gameOver();
                }
                player.restart();
            }
        }
        objects.removeAll(found);  // Remove all founded objects
        this.restartGameAllowed = false;

    }

    public void setGameListener(GameListener listener) {
        this.gameListener = listener;
    }
}
