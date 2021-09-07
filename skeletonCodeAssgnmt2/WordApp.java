
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.Scanner;
import java.util.concurrent.*;
// model is separate from the view.

public class WordApp {
  // shared variables
  static int maxWordsOnScreen = 4;
  static int totalWordsToFall;

  static int frameX = 1000;
  static int frameY = 600;
  static int yLimit = 480;

  static WordPanel wordPanel;
  static EventLoop animationLoop = EventLoop.makeInstance();

  public static void setupGUI(int frameX, int frameY, int yLimit) {
    // Frame init and dimensions
    JFrame frame = new JFrame("WordGame");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(frameX, frameY);
    JPanel g = new JPanel();
    g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS));
    g.setSize(frameX, frameY);

    wordPanel = new WordPanel(yLimit);
    wordPanel.setSize(frameX, yLimit + 100);
    g.add(wordPanel);

    JPanel txt = new JPanel();
    txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS));
    JLabel caught = new JLabel("Caught: " + Score.currentScore.getCaught() + "    ");
    JLabel missed = new JLabel("Missed:" + Score.currentScore.getMissed() + "    ");
    JLabel scr = new JLabel("Score:" + Score.currentScore.getScore() + "    ");

    Score.currentScore.addListener(score -> {
      caught.setText("Caught: " + score.getCaught() + "    ");
      missed.setText("Missed:" + score.getMissed() + "    ");
      scr.setText("Score:" + score.getScore() + "    ");
    });

    txt.add(caught);
    txt.add(missed);
    txt.add(scr);

    final JTextField textEntry = new JTextField("", 20);
    textEntry.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        String text = textEntry.getText();
        animationLoop.dispatchData(text);
        textEntry.setText("");
        textEntry.requestFocus();
      }
    });

    txt.add(textEntry);
    txt.setMaximumSize(txt.getPreferredSize());
    g.add(txt);

    JPanel b = new JPanel();
    b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));
    JButton startB = new JButton("Start");;

    // add the listener to the jbutton to handle the "pressed" event
    startB.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        animationLoop.dispatch(EventLoop.EventType.START);
        textEntry.requestFocus();
      }
    });

    JButton endB = new JButton("End");
    endB.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        animationLoop.dispatch(EventLoop.EventType.STOP);
      }
    });

    JButton quitBtn = new JButton("Quit");
    quitBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        animationLoop.dispatch(EventLoop.EventType.EXIT);
        frame.dispose();
      }
    });

    b.add(startB);
    b.add(endB);
    b.add(quitBtn);

    g.add(b);

    frame.setLocationRelativeTo(null); // Center window on screen.
    frame.add(g); // add contents to window
    frame.setContentPane(g);
    // frame.pack(); // don't do this - packs it into small space
    frame.setVisible(true);
  }

  public static String[] getDictFromFile(String filename) {
    String[] dictStr = null;
    try {
      Scanner dictReader = new Scanner(new FileInputStream(filename));
      int dictLength = dictReader.nextInt();
      // System.out.println("read '" + dictLength+"'");

      dictStr = new String[dictLength];
      for (int i = 0; i < dictLength; i++) {
        dictStr[i] = new String(dictReader.next());
        // System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
      }
      dictReader.close();
    } catch (IOException e) {
      System.err.println("Problem reading file " + filename + " default dictionary will be used");
    }
    return dictStr;
  }

  public static void main(String[] args) {
    totalWordsToFall = Integer.parseInt(args[0]);
    maxWordsOnScreen = Integer.parseInt(args[1]);

    assert (totalWordsToFall >= maxWordsOnScreen);

    String[] tmpDict = getDictFromFile(args[2]);
    if (tmpDict != null)
      WordRecord.dict = new WordDictionary(tmpDict);

    setupGUI(frameX, frameY, yLimit);

    int x_inc = (int) frameX / maxWordsOnScreen;

    final WordRecord[] words = new WordRecord[WordRecord.dict.getWordCount()];
    for (int i = 0; i < WordRecord.dict.getWordCount(); i++) {
      final String word = WordRecord.dict.getNewWord();
      words[i] = new WordRecord(word, frameX - word.length() * 8, yLimit);
    }

    wordPanel.setWords(words);
    animationLoop.addListener(wordPanel);
    animationLoop.start();
  }
}
