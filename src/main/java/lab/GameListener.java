package lab;

import java.io.IOException;

public interface GameListener {
    public void stateChanged(int score, int hp);

    public void gameOver() throws IOException;

    public void gameWin() throws IOException;
}
