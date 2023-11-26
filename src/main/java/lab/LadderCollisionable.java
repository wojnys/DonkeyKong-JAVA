package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

public interface LadderCollisionable {

     void climbingAllowed();
     void climbingStopped();

     boolean intersectsWithLadder(Ladder ladder);

      void goingDownAllowed();
      void goingDownStopped();

      Point2D getPosition();
      Point2D getSize();
}
