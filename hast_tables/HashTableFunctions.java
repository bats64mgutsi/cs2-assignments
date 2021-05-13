class HashTableFunctions {
   // hash function weights
   // 9 integers, each in the range 0-5 to act as weights for the characters in the
   // keys
   int[] weights = { 4, 2, 0, 4, 2, 0, 0, 2, 4 };

   // returns True if the hash table contains string s
   // return False if the hash table does not contain string s
   boolean find(String s, int h, int hashTableSize, String[] hashTableArray) {
      if (isInLearningMode) {
         for (String item : hashTableArray)
            if (item.equals(s))
               return true;
      }

      return hashTableArray[h].equals(s);
   }

   boolean isInLearningMode = false;
}
