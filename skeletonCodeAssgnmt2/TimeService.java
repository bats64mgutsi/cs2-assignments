public class TimeService {
  private long previousMillis = System.currentTimeMillis();

  /**
   * Returns the time, in milliseconds, that have elapsed since the previous call to this method.
   */
  public long deltaMillis() {
    final long current = System.currentTimeMillis();
    final long out = current - previousMillis;
    previousMillis = current;

    return out;
  }

  /**
   * Resets the previous time to the current time.
   */
  public void reset() {
    previousMillis = System.currentTimeMillis();
  }
}
