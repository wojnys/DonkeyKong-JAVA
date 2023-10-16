package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class DrawingThread extends AnimationTimer {
	private static final int FPS = 100;
	private final Canvas canvas;
	private final GraphicsContext gc;
	private final Game game;
	private long lasttime = -1;
	private final Set<KeyCode> pressedKeys;

	public DrawingThread(Canvas canvas, Set<KeyCode> pressedKeys) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.game = new Game(this.canvas.getWidth(), this.canvas.getHeight(), this.canvas.getScene(), pressedKeys);
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
			this.game.draw(gc);
			if (lasttime > 0) {
				//time are in nanoseconds and method simulate expects seconds
				this.game.update(deltaT, pressedKeys);
			}
			this.lasttime = now;
		}
	}

}
