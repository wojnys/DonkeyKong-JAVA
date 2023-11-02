package lab;

import javafx.geometry.Rectangle2D;

public interface LadderCollisionable {

     void climbingAllowed();
     void climbingStopped();

     boolean intersectsWithLadder(Ladder ladder);
     boolean intersectsWithLadderForGoingDown(Ladder ladder);

      void goingDownAllowed();
      void goingDownStopped();
}
