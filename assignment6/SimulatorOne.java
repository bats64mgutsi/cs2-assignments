import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SimulatorOne {
  public static void main(String[] args) {
    final InputInformation inputInformation = InputInformation.readFromScanner(new Scanner(System.in));
    final Graph graph = new Graph();
    for (EdgeInformation edge : inputInformation.edges)
      graph.addEdge(edge.nameOfStartNode, edge.nameOfEndNode, edge.weight);

    for (DeliveryRequest request : inputInformation.deliveryRequests)
      processRequest(request, graph, inputInformation.namesOfDriverNodes);
  }

  private static void processRequest(DeliveryRequest request, Graph graph, List<String> driverNodeNames) {
    Map<String, FullDeliveryPath> driverNodeNamesToPossiblePathsMap = new HashMap<>();
    for (String driverNodeName : driverNodeNames) {
      final PathInformation pathFromDriverHomeToClient = computePathInformation(driverNodeName,
          request.nameOfPickUpNode, graph);
      final PathInformation pathFromClientToDropOff = computePathInformation(request.nameOfPickUpNode,
          request.nameOfDropOffNode, graph);
      final PathInformation pathFromDropOffToDriverHome = computePathInformation(request.nameOfDropOffNode,
          driverNodeName, graph);

      if (pathFromDriverHomeToClient == null || pathFromClientToDropOff == null
          || pathFromDropOffToDriverHome == null) {
        // This request is impossible for this driver.
        continue;
      }

      driverNodeNamesToPossiblePathsMap.put(driverNodeName, new FullDeliveryPath(driverNodeName,
          pathFromDriverHomeToClient, pathFromClientToDropOff, pathFromDropOffToDriverHome));
    }

    System.out.printf("client %s %s\n", request.nameOfPickUpNode, request.nameOfDropOffNode);

    if (!driverNodeNamesToPossiblePathsMap.isEmpty()) {
      final List<FullDeliveryPath> cheapestPaths = FullDeliveryPath
          .cheapestIn(new ArrayList<>(driverNodeNamesToPossiblePathsMap.values()));
      // Of the cheapest paths take the one from the driver with the lowest driver
      // node number/name
      FullDeliveryPath chosenPath = cheapestPaths.get(0);
      for (int iii = 0; iii < cheapestPaths.size(); iii++) {
        if (cheapestPaths.get(iii).driverNodeName.compareTo(chosenPath.driverNodeName) < 0)
          chosenPath = cheapestPaths.get(iii);
      }

      System.out.printf("truck %s\n", chosenPath.driverNodeName);
      printSinglePath(chosenPath.pathFromDriverHomeToClient);

      System.out.printf("pickup %s\n", request.nameOfPickUpNode);
      printSinglePath(chosenPath.pathFromClientToDropOff);

      System.out.printf("dropoff %s\n", request.nameOfDropOffNode);
      printSinglePath(chosenPath.pathFromDropOffToDriverHome);
    } else {
      System.out.println("cannot be helped");
    }
  }

  /**
   * @return null if the path is impossible
   */
  private static PathInformation computePathInformation(String startNodeName, String endNodeName, Graph graph) {
    graph.dijkstra(startNodeName, false);  

    Vertex destVertex = graph.vertexMap.get(endNodeName);

    if (destVertex == null)
      throw new NoSuchElementException("Destination vertex not found");
    else if (destVertex.dist == Graph.INFINITY)
      return null;

    final List<String> pathSequence = new ArrayList<>();
    pathSequenceMaker(destVertex, pathSequence);

    final double cost = destVertex.dist;
    boolean hasAlternativesOfTheSamePrice = false;
    graph.dijkstra(startNodeName, true);
    destVertex = graph.vertexMap.get(endNodeName);
    final List<String> pathSequenceForAlternative = new ArrayList<>();
    pathSequenceMaker(destVertex, pathSequenceForAlternative);
    hasAlternativesOfTheSamePrice = !pathSequence.toString().equals(pathSequenceForAlternative.toString());

    return new PathInformation(cost, pathSequence, hasAlternativesOfTheSamePrice);
  } 

  static void printSinglePath(PathInformation path) {
    if (path.hasAlternativesOfTheSamePrice)
      System.out.printf("multiple solutions cost %d\n", Math.round(path.cost));
    else
      System.out.println(path.getNamesOfNodesFromStartToEndAsOneString());
  }

  static void pathSequenceMaker(Vertex vertex, List<String> out) {
    if (vertex.prev != null)
      pathSequenceMaker(vertex.prev, out);

    out.add(vertex.name);
  }
}

class FullDeliveryPath implements Comparable<FullDeliveryPath> {
  public final String driverNodeName;
  public final PathInformation pathFromDriverHomeToClient;
  public final PathInformation pathFromClientToDropOff;
  public final PathInformation pathFromDropOffToDriverHome;

  FullDeliveryPath(String driverNodeName, PathInformation pathFromDriverHomeToClient,
      PathInformation pathFromClientToDropOff, PathInformation pathFromDropOffToDriverHome) {
    this.driverNodeName = driverNodeName;
    this.pathFromDriverHomeToClient = pathFromDriverHomeToClient;
    this.pathFromClientToDropOff = pathFromClientToDropOff;
    this.pathFromDropOffToDriverHome = pathFromDropOffToDriverHome;
  }

  public double totalCost() {
    return pathFromDriverHomeToClient.cost + pathFromClientToDropOff.cost + pathFromDropOffToDriverHome.cost;
  }

  public static List<FullDeliveryPath> cheapestIn(List<FullDeliveryPath> paths) {
    final List<FullDeliveryPath> out = new ArrayList<>(paths);
    final int oneOfTheCheapestPathsIndex = paths.indexOf(Collections.min(paths));

    out.removeIf(p -> p.totalCost() != paths.get(oneOfTheCheapestPathsIndex).totalCost());
    return out;
  }

  @Override
  public int compareTo(FullDeliveryPath o) {
    return Double.compare(this.totalCost(), o.totalCost());
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof FullDeliveryPath))
      return false;
    return compareTo((FullDeliveryPath) o) == 0;
  }
}

class PathInformation {
  public final double cost;
  public final List<String> namesOfNodesFromStartToEnd;
  public final boolean hasAlternativesOfTheSamePrice;

  public PathInformation(double cost, List<String> namesOfNodesFromStartToEnd, boolean hasAlternativesOfTheSamePrice) {
    this.cost = cost;
    this.namesOfNodesFromStartToEnd = namesOfNodesFromStartToEnd;
    this.hasAlternativesOfTheSamePrice = hasAlternativesOfTheSamePrice;
  }

  String getNamesOfNodesFromStartToEndAsOneString() {
    return String.join(" ", namesOfNodesFromStartToEnd);
  }
}
