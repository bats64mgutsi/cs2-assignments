import java.util.*;
import java.nio.file.*;

class TestHashTable {
   public TestHashTable() {
   }

   static List<String> trainingData = null;

   public static void loadTrainingData() {
      try {
         trainingData = Files.readAllLines(Paths.get("names36.txt"));
      } catch (Exception e) {
         System.out.println(e.toString());
      }
   }

   int doEpochAndReturnNumberOfCollisions(int threshold, boolean isInLearningMode, HashTable h) {
      int hit1 = 0, miss1 = 0, hit2 = 0, miss2 = 0;
      List<String> unseenData = null;

      try {
         if (!isInLearningMode)
            unseenData = Files.readAllLines(Paths.get("namesmiss.txt"));
      } catch (Exception e) {
         System.out.println(e.toString());
      }

      for (int i = 0; i < trainingData.size(); i++) {
         h.insert(trainingData.get(i));
      }

      if (!isInLearningMode) {
         for (int i = 0; i < trainingData.size(); i++) {
            if (h.find(trainingData.get(i)))
               hit1++;
            else
               miss1++;
         }

         for (int i = 0; i < unseenData.size(); i++) {
            if (h.find(unseenData.get(i)))
               hit2++;
            else
               miss2++;
         }
      }

      if (!isInLearningMode) {
         System.out.println("Collision Threshold: " + threshold);
         if (h.getCollisions() <= threshold)
            System.out.println("Collisions Threshold Met!");
         else
            System.out.println("Collisions Threshold Not Met! Collisions = " + h.getCollisions());
         System.out.println("Seen data [Hit:Miss]: " + hit1 + ":" + miss1);
         System.out.println("Unseen data [Hit:Miss]: " + hit2 + ":" + miss2);
      }

      return h.getCollisions();
   }

   public static void main(String[] args) {
      loadTrainingData();
      (new TestHashTable()).doEpochAndReturnNumberOfCollisions(Integer.valueOf(args[0]), false,
            new HashTable(new HashTableFunctions()));
   }
}
