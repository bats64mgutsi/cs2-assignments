import java.util.Arrays;

/**
 * This class returns the media value of a sub array of the given array.
 * 
 * The start and end indices of the sub array in the given array are given by
 * start (inclusive) and end (exclusive).
 * 
 * This is a basic implementation for the problem at hand, and therefore assumes
 * that the length of the sub array to find the media of is an odd integer. In
 * other words, end - start should be an even number (end is exclusive).
 */
public class MedianComputer {
  public static double computeMedian(double[] data, int start, int end){
    final double[] subArray = Arrays.copyOfRange(data, start, end);
    Arrays.sort(subArray);
    return subArray[Integer.valueOf((end-start)/2)];    
  }
}