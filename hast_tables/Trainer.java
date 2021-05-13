public class Trainer extends Thread {
    // We use a base 6 number system with digits 0 to 5.
    // This is because our weights array can only have values in range [0, 5]
    public static final int WEIGHTS_ARRAY_RADIX = 6;

    public static int parseBase6Integer(String value) {
        return Integer.parseInt(value, WEIGHTS_ARRAY_RADIX);
    }

    public static String integerToBase6StringWith9Digits(int value) {
        final String str = Integer.toString(value, WEIGHTS_ARRAY_RADIX);

        final int numberOfZerosToPrepend = 9 - str.length();
        final String zeros = (new String(new char[numberOfZerosToPrepend])).replace("\0", "0");

        return zeros + str;
    }

    public static int[] parseWeightsArrayFromInteger(int value) {
        final String valuesAsString = integerToBase6StringWith9Digits(value);
        final int[] out = new int[9];

        for (int iii = 0; iii < out.length; iii++)
            out[iii] = Integer.parseInt(String.valueOf(valuesAsString.charAt(iii)), WEIGHTS_ARRAY_RADIX);

        return out;
    }

    public static void printWeightsAndNewline(int[] values) {
        for (int value : values)
            System.out.printf("%d, ", value);

        System.out.println();
    }

    public static void main(String[] args) {
        TestHashTable.loadTrainingData();
        final int numberOfThreadsAvailable = Runtime.getRuntime().availableProcessors();
        final int maxIterations = (int) Math.pow(6, 9);
        final int weightsRangePerThread = (int) Math.ceil(maxIterations / numberOfThreadsAvailable);

        for (int iii = 0; iii < numberOfThreadsAvailable; iii++) {
            final int threadId = iii;
            final Thread thread = new Thread(new Runnable() {
                public void run() {
                    findWeightsInRange(threadId, threadId * weightsRangePerThread,
                            threadId * weightsRangePerThread + weightsRangePerThread);
                }
            });

            thread.start();
        }
    }

    public static void findWeightsInRange(int threadId, int inclusiveStart, int exlusiveEnd) {
        final HashTableFunctions hashTableFunctions = new HashTableFunctions();
        hashTableFunctions.isInLearningMode = true;

        int lowestCollisionCount = 1000;
        int highestCollisionCount = -1;

        for (int weightsAsInteger = inclusiveStart; weightsAsInteger < exlusiveEnd; weightsAsInteger++) {
            hashTableFunctions.weights = parseWeightsArrayFromInteger(weightsAsInteger);
            final int collisions = (new TestHashTable()).doEpochAndReturnNumberOfCollisions(-1, true,
                    new HashTable(hashTableFunctions));

            if (collisions < lowestCollisionCount) {
                System.out.printf("%d: Lowest collission count %d at %d\n", threadId, collisions, weightsAsInteger);
                lowestCollisionCount = collisions;
            }
            if (collisions > highestCollisionCount) {
                System.out.printf("%d: Highest collission count %d at %d\n", threadId, collisions, weightsAsInteger);
                highestCollisionCount = collisions;
            }

            if (collisions == 0) {
                System.out.printf("%d: Perfect weights at %d: ", threadId, weightsAsInteger);
                printWeightsAndNewline(hashTableFunctions.weights);
            }
        }
    }
}
