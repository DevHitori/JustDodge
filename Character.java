import java.util.Random;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.applet.AudioClip;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.IOException;

import javax.sound.sampled.*;

public class Character {

	private static final int XSIZE = 30;
	private static final int YSIZE = 30;
	private static final int XSTEP = 5;
	private static final int YSTEP = 5;
	private static final int YPOS = 350;

	private JPanel panel;
	private Dimension dimension;
	private int x;
	private int y;

	Graphics2D g2;
	private Color backgroundColor;
	Clip hitBatSound = null;

	public Character (JPanel p) {
		panel = p;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		backgroundColor = panel.getBackground ();
		// loadClips();

		dimension = panel.getSize();
		Random random = new Random();
		x = dimension.width/2;
		y = dimension.height/2;;
	}

	public void draw () {
		g2.setColor(Color.WHITE);
		g2.fill(new Rectangle2D.Double (x, y, XSIZE, YSIZE));
	}

	public void erase () {
		g2.setColor(backgroundColor);
		g2.fill (new Rectangle2D.Double (x, y, XSIZE, YSIZE));
	}

	public Rectangle2D.Double getBoundingRectangle() {
		return new Rectangle2D.Double (x, y, XSIZE, YSIZE);
	}

	public void moveLeft () {
		if (!panel.isVisible ()) return;

		erase();

		x = x - XSTEP;

		if (x < 0) {					// hits left wall
			x = 0;
			playClip (1);
		}
                draw ();

	}

	public void moveRight () {

		if (!panel.isVisible ()) return;

		erase();

		x = x + XSTEP;

		if (x + XSIZE >= dimension.width) {
			x = dimension.width - XSIZE;
			playClip (1);
		}
                draw ();
	}

	public void moveUp () {

		if (!panel.isVisible ()) return;

		erase();

		y = y + YSTEP;

                if (y + YSIZE >= dimension.height) {
			y = dimension.height - YSIZE;
			playClip (1);
		}

                draw ();
	}

	public void moveDown () {
		if (!panel.isVisible ()) return;

		erase();

		y = y - YSTEP;


                if (y < 0 ) {
			y = 0;
			playClip (1);
		}
                draw ();

	}

	public void loadClips() {
        File audioFile = new File("./resources/sounds/softThud.wav");
        try {
            // Set up audio stream with respect to our audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            // Get the format of the file (wav, au ...)
            AudioFormat format = audioStream.getFormat();
            // Convert the file into a Clip
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            // Store the file in a Clip variable
            hitBatSound = (Clip) AudioSystem.getLine(info);
            // Open the audio stream
            hitBatSound.open(audioStream);

            // Keep track of whether the resources are opened or not
     //       opened = true;

        } catch (UnsupportedAudioFileException uafe) {
            // System.out.println("The specified audio file is not supported.");
            // uafe.printStackTrace();
        } catch (LineUnavailableException lue) {
            // System.out.println("Audio line for playing back is unavailable.");
            // lue.printStackTrace();
        } catch (IOException ioe) {
            // System.out.println("Error playing the audio file.");
            // ioe.printStackTrace();
        }

	}

	public void playClip (int index) {

		if (index == 1 && hitBatSound != null)
			hitBatSound.start();

	}

}
