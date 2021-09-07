
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements EventLoop.EventLoopListener {
  private WordRecord[] allWords;
  private int maxY;

  WordPanel(int maxY) {
    this.maxY = maxY;
  }

  public void setWords(WordRecord[] allWords) {
    this.allWords = allWords;
  }

  public void paintComponent(Graphics g) {
    int width = getWidth();
    int height = getHeight();
    g.clearRect(0, 0, width, height);
    g.setColor(Color.red);
    g.fillRect(0, maxY - 10, width, height);

    g.setColor(Color.black);
    g.setFont(new Font("Helvetica", Font.PLAIN, 26));

    for (WordRecord fallingWord : getFallingWords()) {
      g.drawString(fallingWord.getWord(), fallingWord.getX(), fallingWord.getY() + 20);
    }
  }

  @Override
  public void onDraw() {
    repaint();
  }

  @Override
  public void onAnimate(long deltaMillis) {
    final WordRecord[] fallingWords = getFallingWords();
    for (WordRecord wordRecord : fallingWords) {
      final double increment = wordRecord.getSpeed() * 0.0002 * deltaMillis;
      wordRecord.drop((int) increment);
    }

    computeWordStates(fallingWords);
  }

  private WordRecord[] getFallingWords() {
    return Arrays.stream(allWords).filter(word -> word.isFalling()).toArray(WordRecord[]::new);
  }

  /**
   * From the falling words, this method determines if a word has been captured or has been dropped
   * (i.e missed).
   * 
   * If a word has been dropped, the nr of words missed score is incremented nd the word state is
   * reset. More words will be added to ensure the nr of words showing equals
   * WordApp.maxWordsOnScreen.
   */
  private void computeWordStates(WordRecord[] fallingWords) {
    for (WordRecord fallingWord : fallingWords) {
      synchronized (fallingWord) {
        if (fallingWord.dropped()) {
          Score.currentScore.missedWord();
          fallingWord.resetWord();
        }

        // TODO(Batandwa): Resolve captured words
      }
    }

    dropMoreWordsIfPossible(fallingWords.length);
  }

  private void dropMoreWordsIfPossible(int numberOfWordsAlreadyFalling) {
    int numberOfWordsToDrop = WordApp.maxWordsOnScreen - numberOfWordsAlreadyFalling;
    while (numberOfWordsToDrop > 0) {
      sampleNextWordToBeDropped().beginDrop();
      numberOfWordsToDrop--;
    }
  }

  private WordRecord sampleNextWordToBeDropped() {
    WordRecord[] nonFallingWords =
        Arrays.stream(allWords).filter(word -> !word.isFalling()).toArray(WordRecord[]::new);
    return nonFallingWords[(int) (Math.random() * nonFallingWords.length)];
  }
}

