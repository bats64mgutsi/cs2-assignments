import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class MedianComputerTest {
  @Test
  public void shouldReturnTheDataItemWhenTheSubArrayHasOneElement() {
    final double ret = MedianComputer.computeMedian(new double[] {4, 2, 3}, 1, 2);
    assertEquals(2.0, ret);
  }

  @Test
  public void shouldReturnCorrectMedianWhenSubArrayIsTheDataArrayItself() {
    final double ret = MedianComputer.computeMedian(new double[] {4, 2, 3}, 0, 3);
    assertEquals(3.0, ret);
  }

  @Test
  public void shouldReturnCorrectMedianWhenSubAArrayIsNotTheDataArray() {
    final double ret = MedianComputer.computeMedian(new double[] {4, 4, 7, 2, 9}, 1, 4);
    assertEquals(4.0, ret);
  }
}
