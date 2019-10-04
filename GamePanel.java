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
		addKeyListener(this);
		setFocusable(true);
    		requestFocus();
		loadClips ();

		gameThread = null;
		isRunning = false;

	}



	public void run () {
		try {
			isRunning = true;
			while (isRunning) {
                                character.draw();
				gameUpdate();
				gameRender();
				Thread.sleep(100);
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
		if (keyCode == KeyEvent.VK_DOWN) {
			character.moveUp();
		}
		else
		if (keyCode == KeyEvent.VK_UP) {
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
					getClass().getResource("./resources/sounds/Megalovania.wav"));

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
	}


	public void startMusic(){
		playSound.loop();
	}

	public void startGame() {				// initialise and start the game thread
		requestFocus();



		if (gameThread == null) {
			isRunning = true;
			System.out.println("JTEST");
			character = new Character (this);
			System.out.println("JTEST");
			obj1 = new Obj1 (this, character);
			System.out.println("JTEST");
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
