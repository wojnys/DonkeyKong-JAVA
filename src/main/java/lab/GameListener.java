package lab;

import java.io.IOException;

public interface GameListener {
    public void stateChanged(int score);

    public void gameOver() throws IOException;

    public void gameStart() throws IOException;
}
