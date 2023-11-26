package lab;

import java.io.IOException;

public class EmptyGameListener implements GameListener {

    @Override
    public void stateChanged(int score, int hp) {
    }

    @Override
    public void gameOver() throws IOException{
    }

    @Override
    public void gameWin() throws IOException {

    }

}
