import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable, KeyListener{

	private Character character = null;
	private Obj1 obj1 = null;

	AudioClip playSound = null;

	private Thread gameThread;
	boolean isRunning;

	public GamePanel () {

		setBackground(Color.BLACK);
		addKeyListener(this);			// respond to key events
		setFocusable(true);
    		requestFocus();    			// the GamePanel now has focus, so receives key events

		loadClips ();

		gameThread = null;
		isRunning = false;

	}

	// implementation of Runnable interface

	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
				gameUpdate();
				gameRender();
				Thread.sleep (200);	// increase value of sleep time to slow down ball
			}
		}
		catch(InterruptedException e) {}
	}

	// implementation of KeyListener interface

	public void keyPressed (KeyEvent e) {
		if (character == null)
			return;

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT) {
			character.moveLeft();
		}
		else
		if (keyCode == KeyEvent.VK_RIGHT) {
			character.moveRight();
		}
		else
		if (keyCode == KeyEvent.VK_UP) {
			character.moveUp();
		}
		else
		if (keyCode == KeyEvent.VK_DOWN) {
			character.moveDown();
		}
	}

	public void keyReleased (KeyEvent e) {

	}

	public void keyTyped (KeyEvent e) {
	}

	public void loadClips() {

		try {
			playSound = Applet.newAudioClip (
					getClass().getResource("resources/sounds/Megalovania.wav"));

		}
		catch (Exception e) {
			System.out.println ("Error loading sound file: " + e);
		}

	}

	public void playClip (int index) {

		if (index == 1 && playSound != null)
			playSound.play();

	}

	public void gameUpdate () {
		obj1.move();
	}

	public void gameRender () {				// draw the game objects
		obj1.draw();
		character.draw();
	}


	public void startMusic(){
		playSound.loop();
	}

	public void startGame() {				// initialise and start the game thread
		requestFocus();
		if (gameThread == null) {
			isRunning = true;
			character = new Character (this);
			obj1 = new Obj1 (this, character);
			playSound.stop();
			playSound.loop();
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	public void endGame() {					// end the game thread

		if (isRunning) {
			isRunning = false;
			playSound.stop();
		}
	}
}
