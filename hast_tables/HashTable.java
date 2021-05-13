class HashTable {
   final HashTableFunctions hashTableFunctions;

   // hash table size
   int hashTableSize = 37;

   // array to store items
   String[] hashTableArray;
   // for counting the number of additional comparisons due to collisions
   int collisions;

   // constructor
   public HashTable(HashTableFunctions hashTableFunctions) {
      this.hashTableFunctions = hashTableFunctions;
      hashTableArray = new String[hashTableSize];
      for (int i = 0; i < hashTableSize; i++)
         hashTableArray[i] = "";
      collisions = 0;
   }

   // return number of additional collision comparisons
   public int getCollisions() {
      return collisions;
   }

   // hash function based on external weights
   public int hash(String s) {
      int val = 0;
      for (int i = 0; i < 9; i++)
         val += hashTableFunctions.weights[i] * s.charAt(i);
      return val % hashTableSize;
   }

   // inserts string s into the hash table
   public void insert(String s) {
      int h = hash(s);
      while (!hashTableArray[h].equals("")) {
         h = (h + 1) % hashTableSize;
         collisions++;
      }
      hashTableArray[h] = s;
   }

   // stub to point to external function
   boolean find(String s) {
      return hashTableFunctions.find(s, hash(s), hashTableSize, hashTableArray);
   }
}