
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




//
//import javafx.geometry.Point2D;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.input.KeyCode;
//import javafx.scene.paint.Color;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class Platforms {
//
//    private Color color;
//    private Point2D size;
//    private Point2D position;
//    private String[][] map = {
//            {"111111100000111111"},
//            {"111111100000111111"}
//    };
//    private List<Rectangle2D> platform;
//
//    public Platforms(Color color, Point2D size) {
//        this.color = color;
//        this.size = size;
//    }
//
//    public void draw(GraphicsContext gc) {
//        for (int i = 0; i < map.length; i++) {
//            String line = Arrays.toString(map[i]);
//            for (int j = 0; j < line.length(); j++) {
//                switch (line.charAt(j)) {
//                    case '0':
//                        break;
//                    case '1':
//                        gc.setFill(this.color);
//                        gc.fillRect(j * 60, j * 60, this.size.getX(), this.size.getY());
//                        this.platform.add(new Rectangle2D(j * 60, j * 60, this.size.getX(), this.size.getY()));
//                }
//            }
//        }
//    }
//
//    public Rectangle2D getBoundingBox(Platforms platform) {
////        return new Rectangle2D(platform.get, platform.)
//    }
//
//    public void update(double deltaT) {
//
//    }
//
//
//}

//
//        gc.setFill(Color.BROWN);
//    brownWalls = new Rectangle2D[] {
//        new Rectangle2D(100, 200, 60, 60),  // Example wall 1
//                new Rectangle2D(200, 200, 60, 60),  // Example wall 2
//        // Add more walls as needed
//    };
//        for(int i =0;i<map.length;i++) {
//        String line = Arrays.toString(map[i]);
//        for(int j=0;j<line.length(); j++) {
//            switch(line.charAt(j)) {
//                case '0':
//                    break;
//                case '1':
//                    gc.setFill(Color.BROWN);
//                    gc.fillRect(j*60, j*60, 60,60);
//                    brownWalls[i] = new Rectangle2D(j*60, j*60, 60,60);
//            }
//        }
//    }
//
//                for (int i = 0; i < brownWalls.length; i++) {
//        if (this.player.getBoundingBox().intersects(brownWalls[i])) {
//            System.out.print("add");
//            // Handle collision with brown wall
//            // For example, you can stop the player's movement in the wall's direction:
//            if (pressedKeys.contains(KeyCode.D)) {
//                System.out.println("D is out");
//            } else if (pressedKeys.contains(KeyCode.A)) {
////                    this.player.stopMovingLeft();
//            }
//            // You can also add a collision response to adjust the player's position.
//        }
//    }

