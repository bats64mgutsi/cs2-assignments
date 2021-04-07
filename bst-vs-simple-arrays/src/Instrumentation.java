import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import data_structures.BinarySearchTree;
import data_structures.SimpleArrayCollection;
import data_structures.SimpleCollection;

public class Instrumentation {
  private interface CollectionBuilder {
    SimpleCollection<Student> build();
  }

  private class CaseScenarios {
    final int bestCase;
    final int worstCase;
    final int averageCase;

    public CaseScenarios(int bestCase, int worstCase, int averageCase) {
      this.bestCase = bestCase;
      this.worstCase = worstCase;
      this.averageCase = averageCase;
    }
  }

  private class InstrumentationLogForGivenCardinality {
    final int cardinality;
    final CaseScenarios bstCaseScenarios;
    final CaseScenarios arrayCaseScenarios;

    InstrumentationLogForGivenCardinality(int cardinality, CaseScenarios bstCaseScenarios,
        CaseScenarios arrayCaseScenarios) {
      this.cardinality = cardinality;
      this.bstCaseScenarios = bstCaseScenarios;
      this.arrayCaseScenarios = arrayCaseScenarios;
    }
  }

  /**
   * Builds a new collection, loads the given items, then searches for
   * itemToSearchFor and returns the value of
   * getNumberOfKeyComparisonsWhenSearchingForItem for the collection after the
   * search.
   */
  private int computeOpCount(CollectionBuilder collectionBuilder, Student[] items, Student itemToSearchFor)
      throws Exception {
    SimpleCollection<Student> collection = collectionBuilder.build();
    collection.setDataSource(items);
    collection.firstItemMatching(itemToSearchFor);
    return collection.getNumberOfKeyComparisonsWhenSearchingForItem();
  }

  Student[] cachedStudents;

  private Student[] drawARandomSetOfStudentsWithCardinality(int n) throws Exception {
    if (cachedStudents == null) {
      cachedStudents = ((StudentDataReaderService) Locator.instance.get(StudentDataReaderService.class))
          .readAllStudents();
    }

    Student[] out = new Student[n];
    Random random = new Random();
    for (int iii = 0; iii < n; iii++) {
      out[iii] = cachedStudents[random.nextInt(5000)];
    }

    return out;
  }

  private CaseScenarios computeCaseScenariosForASetOfStudentsWithCardinality(int n, CollectionBuilder collectionBuilder)
      throws Exception {
    final Student[] set = drawARandomSetOfStudentsWithCardinality(n);
    final List<Integer> opCounts = new ArrayList<>(n);

    for (int iii = 0; iii < n; iii++) {
      int opCount = computeOpCount(collectionBuilder, set, set[iii]);
      opCounts.add(iii, opCount);
    }

    Integer worstCase = opCounts.stream().mapToInt(v -> v).max().orElseThrow(NoSuchMethodException::new);
    Integer bestCase = opCounts.stream().mapToInt(v -> v).min().orElseThrow(NoSuchMethodException::new);
    Integer averageCase = (int) Math.ceil(opCounts.stream().mapToInt(v -> v).sum() / n);

    return new CaseScenarios(bestCase, worstCase, averageCase);
  }

  private InstrumentationLogForGivenCardinality computeInstrumentationLogForGivenCardinality(int n) throws Exception {
    final CaseScenarios bstCaseScenarios = computeCaseScenariosForASetOfStudentsWithCardinality(n,
        () -> new BinarySearchTree<>());
    final CaseScenarios arrayCaseScenarios = computeCaseScenariosForASetOfStudentsWithCardinality(n,
        () -> new SimpleArrayCollection<>());

    return new InstrumentationLogForGivenCardinality(n, bstCaseScenarios, arrayCaseScenarios);
  }

  private void initLocatorForInstrumenting() {
    Locator.instance.registerSingleton(FileSystemService.makeInstance());
    Locator.instance.registerSingleton(StudentDataReaderService.makeInstance());
  }

  private void instrumentDataStructures() throws Exception {
    initLocatorForInstrumenting();

    List<InstrumentationLogForGivenCardinality> logs = new LinkedList<>();
    for (int n = 500; n <= 5000; n += 500) {
      final InstrumentationLogForGivenCardinality instrumentationLog = computeInstrumentationLogForGivenCardinality(n);
      logs.add(instrumentationLog);
    }

    System.out.println("BST Instrumentation Logs For Given Cardinality");
    System.out.println("-----------------------------------------------");
    System.out.println("n, worstCase, averageCase, bestCase");
    for (InstrumentationLogForGivenCardinality log : logs) {
      System.out.printf("%d, %d, %d, %d\n", log.cardinality, log.bstCaseScenarios.worstCase,
          log.bstCaseScenarios.averageCase, log.bstCaseScenarios.bestCase);
    }

    System.out.print("\n");
    System.out.println("Array Instrumentation Logs For Given Cardinality");
    System.out.println("-----------------------------------------------");
    System.out.println("n, worstCase, averageCase, bestCase");
    for (InstrumentationLogForGivenCardinality log : logs) {
      System.out.printf("%d, %d, %d, %d\n", log.cardinality, log.arrayCaseScenarios.worstCase,
          log.arrayCaseScenarios.averageCase, log.arrayCaseScenarios.bestCase);
    }
  }

  public static void main(String[] args) throws Exception {
    final Instrumentation instrumentation = new Instrumentation();
    instrumentation.instrumentDataStructures();
  }
}
