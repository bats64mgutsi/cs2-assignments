package src;
import java.util.LinkedList;

public class Score {
  private int missedWords;
  private int caughtWords;
  private int gameScore;

  private LinkedList<OnScoreChangedListener> scoreChangedListeners = new LinkedList<>();

  Score() {
    missedWords = 0;
    caughtWords = 0;
    gameScore = 0;
  }

  public void addListener(OnScoreChangedListener onScoreChangedListener) {
    scoreChangedListeners.add(onScoreChangedListener);
  }

  public synchronized int getMissed() {
    return missedWords;
  }

  public synchronized int getCaught() {
    return caughtWords;
  }

  public synchronized int getTotal() {
    return (missedWords + caughtWords);
  }

  public synchronized int getScore() {
    return gameScore;
  }

  public synchronized void missedWord() {
    missedWords++;
    notifyListeners();
  }

  public synchronized void caughtWord(int length) {
    caughtWords++;
    gameScore += length;
    notifyListeners();
  }

  public synchronized void resetScore() {
    caughtWords = 0;
    missedWords = 0;
    gameScore = 0;
    notifyListeners();
  }

  private void notifyListeners() {
    scoreChangedListeners.forEach(listener -> listener.onChange(this));
  }

  public static final Score currentScore = new Score();

  public static interface OnScoreChangedListener {
    public void onChange(Score score);
  }
}
