package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Set;

public class DrawingThread extends AnimationTimer {
	private static final int FPS = 100;
	private final Canvas canvas;
	private final GraphicsContext gc;
	private Game game;
	private long lasttime = -1;
	private final Set<KeyCode> pressedKeys;

	public DrawingThread(Canvas canvas, Set<KeyCode> pressedKeys, Game game) throws IOException {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.game = game;
		this.pressedKeys = pressedKeys;
		
	}

	/**
	  * Draws objects into the canvas. Put you code here. 
	 */
	@Override
	public void handle(long now) {
		double deltaT = (now - lasttime) / 1e9;
		if (deltaT >= 1./FPS) {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
			gc.setFill(Color.BLACK);
			try {
				this.game.draw(gc);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (lasttime > 0) {
				//time are in nanoseconds and method simulate expects seconds
				try {
					this.game.update(deltaT, pressedKeys);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			this.lasttime = now;
		}
	}

}
