
public class WordRecord {
  private String text;
  private int x;
  private int y;
  private int maxX;
  private int maxY;
  private boolean dropped;
  private boolean isFalling = false;
  private boolean solved = false;

  private int fallingSpeed;

  public static WordDictionary dict = new WordDictionary();

  WordRecord() {
    text = "";
    x = 0;
    y = 0;
    maxY = 300;
    dropped = false;
    fallingSpeed = (int) (Math.random() * 100);
  }

  WordRecord(String text) {
    this();
    this.text = text;
  }

  WordRecord(String text, int maxX, int maxY) {
    this(text);
    this.x = (int) (Math.random() * maxX);
    this.maxY = maxY;
  }

  // all getters and setters must be synchronized
  public synchronized void setY(int y) {
    if (y > maxY) {
      y = maxY;
      dropped = true;
    }
    this.y = y;
  }

  public synchronized void setX(int x) {
    this.x = x;
  }

  public synchronized void setWord(String text) {
    this.text = text;
  }

  public synchronized boolean isSolved() {
    return solved;
  }

  public synchronized boolean isFalling() {
    return this.isFalling;
  }

  public synchronized String getWord() {
    return text;
  }

  public synchronized int getX() {
    return x;
  }

  public synchronized int getY() {
    return y;
  }

  public synchronized int getSpeed() {
    return fallingSpeed;
  }

  public synchronized void setPos(int x, int y) {
    setY(y);
    setX(x);
  }

  public synchronized void resetPos() {
    setY(0);
  }

  public synchronized void resetWord() {
    resetPos();
    text = dict.getNewWord();
    dropped = false;
    isFalling = false;
    solved = false;
    x = (int) (Math.random() * maxX);
    fallingSpeed = (int) (Math.random() * 100);
  }

  public synchronized boolean markAsSolvedIfMatches(String typedText) {
    solved = typedText.equals(this.text);
    return solved;
  }

  public synchronized void beginDrop() {
    isFalling = true;
  }

  public synchronized void drop(int inc) {
    setY(y + inc);
  }

  public synchronized boolean dropped() {
    return dropped;
  }
}
