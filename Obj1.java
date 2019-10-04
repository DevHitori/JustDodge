import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import java.util.Random;
import java.applet.AudioClip;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.IOException;

import javax.sound.sampled.*;

public class Obj1 {

	private static final int XSIZE = 40;
	private static final int YSIZE = 40;
	private static final int DY = 10;

	private JPanel panel;
	private Character character;
	private Random random;
	private int x;
	private int y;
        private int GScore=0;
        private String direction;
        private int NumtoSkip;
        private int[] arrX = {0,42,84,126,168,210,252,294,336,378};
        private int[] arrY = {0,42,84,126,168,210,252,294,336,378,420,462,504,546,588};

	Clip hitBatSound;
	Clip fallOffSound;

	Graphics2D g2;
	private Color backgroundColor;
	private Dimension dimension;

	public Obj1 (JPanel p, Character b) {
		panel = p;
		character = b;
		Graphics g = panel.getGraphics ();
		g2 = (Graphics2D) g;
		dimension = panel.getSize();
		backgroundColor = panel.getBackground ();

		random = new Random();
		newWave();

		loadClips();
	}

	public void newWave () {
                String[] directions = {"up","down","left","right"};
                Random rand = new Random();
                int randomNumber = rand.nextInt(directions.length);
                direction=directions[randomNumber];
                System.out.println(direction);

                Random rand2 = new Random();


                if(direction=="down"){
                    x = random.nextInt(dimension.width - XSIZE);
                    y = 0;
                    int randomNumber2 = rand2.nextInt(arrX.length-1);
                    NumtoSkip = randomNumber2;
                }

                if(direction=="up"){
                    x = random.nextInt(dimension.width - XSIZE);
                    y = dimension.height- XSIZE;
                    int randomNumber2 = rand2.nextInt(arrX.length-1);
                    NumtoSkip = randomNumber2;
                }

                if(direction=="right"){
                    y = random.nextInt(dimension.height - YSIZE);
                    x = 0;
                    int randomNumber2 = rand2.nextInt(arrY.length-1);
                    NumtoSkip = randomNumber2;
                }

                if(direction=="left"){
                    y = random.nextInt(dimension.height - YSIZE);
                    x = dimension.width- YSIZE;
                    int randomNumber2 = rand2.nextInt(arrY.length-1);
                    NumtoSkip = randomNumber2;
                }

	}


        public void draw () {
            g2.setColor (Color.ORANGE);
		 if(direction=="down" || direction=="up"){
                   for(int i=0;i<=9;i++){
                    if(NumtoSkip!=i)
                        g2.fill (new Ellipse2D.Double (arrX[i], y, XSIZE, YSIZE));
                   }
                }
                if(direction=="right" || direction=="left" ){
                   for(int i=0;i<=14;i++){
                    if(NumtoSkip!=i)
                        g2.fill (new Ellipse2D.Double (x, arrY[i], XSIZE, YSIZE));
                   }
                }

                g2.setColor (Color.WHITE);
                String score = "Score:" + GScore;
                g2.drawString(score, 20, 20);
	}

	public void erase () {
             g2.setColor (backgroundColor);
             if(direction=="down" || direction=="up"){
                   for(int i=0;i<=9;i++){
                    if(NumtoSkip!=i)
                        g2.fill (new Ellipse2D.Double (arrX[i], y, XSIZE, YSIZE));
                   }
                }
                if(direction=="right" || direction=="left"){
                    for(int i=0;i<=14;i++){
                    if(NumtoSkip!=i)
                        g2.fill (new Ellipse2D.Double (x, arrY[i], XSIZE, YSIZE));
                   }
                }

		g2.setColor (backgroundColor);
		g2.fill (new Ellipse2D.Double (x, y, XSIZE, YSIZE));
                g2.setColor (Color.BLACK);
                String score = "Score:" + GScore;
                g2.drawString(score, 20, 20);
	}


	public boolean batHitsBall () {


		Rectangle2D.Double rectChar = character.getBoundingRectangle();

                for(int i=0;i<=9;i++){
                     if(direction=="left" || direction=="right"){
                        if(NumtoSkip!=i){
                         Rectangle2D.Double rectBall = new Rectangle2D.Double (x, arrY[i], XSIZE, YSIZE);
                         if (rectBall.intersects(rectChar)){
                              return true;
                            }
                        }
                     }

                     if(direction=="up" || direction=="down"){
                         if(NumtoSkip!=i){
                            Rectangle2D.Double rectBall = new Rectangle2D.Double (arrX[i], y, XSIZE, YSIZE);
                            if (rectBall.intersects(rectChar)){
                             return true;
                            }
                         }
                     }

                }


                return false;
	}

	public boolean isOffScreen () {
		if (y + YSIZE > dimension.height || y + YSIZE < 0 || x + XSIZE > dimension.width || x + XSIZE < 0)
			return true;
		else
			return false;
	}


	public void move () {

		if (!panel.isVisible ()) return;

		erase();

                if(direction=="down"){
                    y = y + DY;
                }
                if(direction=="up"){
                    y = y - DY;
                }
                if(direction=="left"){
                    x= x - DY;
                }
                if(direction=="right"){
                    x = x + DY;
                }



		boolean hitBat = batHitsBall();
		if (hitBat || isOffScreen()) {
			if (hitBat) {
                                GScore=-1;
				playClip (1);
                                System.out.println("Failed");

			}
			else {
				playClip (2);
			}

			try {
				Thread.sleep (1000);
			}
			catch (InterruptedException e) {};

			newWave();
                        GScore++;
		}
	}

	public void loadClips() {
        File audioFile1 = new File("./resources/sounds/mediumThud.wav");
        File audioFile2 = new File("./resources/sounds/mediumThud.wav");
        try {
            AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(audioFile1);
            AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(audioFile2);
            AudioFormat format1 = audioStream1.getFormat();
            AudioFormat format2 = audioStream2.getFormat();
            DataLine.Info info1 = new DataLine.Info(Clip.class, format1);
            DataLine.Info info2 = new DataLine.Info(Clip.class, format2);
            hitBatSound = (Clip) AudioSystem.getLine(info1);
            fallOffSound = (Clip) AudioSystem.getLine(info2);
            hitBatSound.open(audioStream1);
			fallOffSound.open(audioStream2);


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

		else
		if (index == 2 && fallOffSound != null)
			fallOffSound.start();

	}

}
