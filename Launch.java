import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Image;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class Launch implements ActionListener{

  private GamePanel gamePanel = new GamePanel ();
  JFrame f = new JFrame("Just Dodge");
  JMenuItem jmiStart = new JMenuItem("Start Game");




  Launch() {



    f.setSize(220, 200);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    //GameMenuBar

    JMenuBar jmb = new JMenuBar();

    JMenu jmFile = new JMenu("Game");
    // JMenuItem jmiStart = new JMenuItem("Start Game");
    JMenuItem jmiExit = new JMenuItem("Exit");
    jmFile.add(jmiStart);
    jmFile.add(jmiExit);
    jmb.add(jmFile);

    JMenu jmOptions = new JMenu("Options");
    JMenu a = new JMenu("A");
    JMenuItem b = new JMenuItem("B");
    JMenuItem c = new JMenuItem("C");
    JMenuItem d = new JMenuItem("D");
    a.add(b);
    a.add(c);
    a.add(d);
    jmOptions.add(a);

    JMenu e = new JMenu("E");
    e.add(new JMenuItem("F"));
    e.add(new JMenuItem("G"));
    jmOptions.add(e);

    jmb.add(jmOptions);

    JMenu jmHelp = new JMenu("Help");
    JMenuItem jmiAbout = new JMenuItem("About");
    jmHelp.add(jmiAbout);
    jmb.add(jmHelp);

    jmiStart.addActionListener(this);
    jmiExit.addActionListener(this);
    b.addActionListener(this);
    c.addActionListener(this);
    d.addActionListener(this);
    jmiAbout.addActionListener(this);

    f.setJMenuBar(jmb);

    // GamePanel

    JPanel mScreen = new JPanel();
    mScreen.setBackground(Color.BLACK);

    f.add(mScreen, "Center");

    ImageIcon bg = new ImageIcon("resources/images/Just Dodge.png");

    Image image = bg.getImage();
    Image newimg = image.getScaledInstance(420, 600,  java.awt.Image.SCALE_SMOOTH);
    bg = new ImageIcon(newimg);


    int width = bg.getIconWidth();
    int height = bg.getIconHeight();

    mScreen.add(new JLabel(bg));
    f.add(mScreen);
    f.pack();

    //show screen
    f.setVisible(true);
    gamePanel.startMusic();
  }



  public void actionPerformed(ActionEvent ae) {
    String comStr = ae.getActionCommand();

    if (comStr == "Start Game"){
      jmiStart.setText("Stop Game");
      f.getContentPane().removeAll();
      f.setVisible(false);
      f.add(gamePanel);
      gamePanel.requestFocus();
      gamePanel.setFocusable (true);
      gamePanel.requestFocus();
      f.setVisible(true);
      gamePanel.startGame();
    }

    if (comStr == "Stop Game"){
      jmiStart.setText("Start Game");
      gamePanel.endGame();
      new Launch();
    }

  }



  public static void main(String args[]) {
    new Launch();
  }

}
