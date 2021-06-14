import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputInformation {
  public final List<EdgeInformation> edges;
  public final List<String> namesOfDriverNodes;
  public final List<DeliveryRequest> deliveryRequests;

  public InputInformation(List<EdgeInformation> edges, List<String> namesOfDriverNodes,
      List<DeliveryRequest> deliveryRequests) {
    this.edges = edges;
    this.namesOfDriverNodes = namesOfDriverNodes;
    this.deliveryRequests = deliveryRequests;
  }

  public static InputInformation readFromScanner(Scanner scanner) {

    final List<EdgeInformation> edges = new ArrayList<>();
    final List<String> namesOfDriverNodes = new ArrayList<>();
    final List<DeliveryRequest> deliveryRequests = new ArrayList<>();

    final int numberOfNodes = scanner.nextInt();
    scanner.nextLine();
    for (int iii = 0; iii < numberOfNodes; iii++)
      edges.addAll(makeEdgesForNode(scanner.nextLine()));

    final int numberOfDrivers = scanner.nextInt();
    scanner.nextLine();
    if (numberOfDrivers != 0) {
      final String allDriverNodeNames = scanner.nextLine();
      namesOfDriverNodes.addAll(getDriverNodeNamesFromLine(allDriverNodeNames));
    }

    final int numberOfDeliveryRequests = scanner.nextInt();
    if (numberOfDeliveryRequests != 0) {
      scanner.nextLine();
      final String allDeliveryRequests = scanner.nextLine();
      deliveryRequests.addAll(getDeliveryRequests(allDeliveryRequests));
    }

    return new InputInformation(edges, namesOfDriverNodes, deliveryRequests);
  }

  private static List<EdgeInformation> makeEdgesForNode(String nodeDataLine) {
    final String[] lineUnits = nodeDataLine.split(" ");
    if (lineUnits.length < 2) {
      // This node has no connections/edges
      return new ArrayList<>();
    }

    final String nameOfStartNode = lineUnits[0];
    final List<EdgeInformation> out = new ArrayList<>();
    for (int destinationIndex = 1; destinationIndex < lineUnits.length; destinationIndex += 2) {
      final String destination = lineUnits[destinationIndex];
      final double weight = Double.parseDouble(lineUnits[destinationIndex + 1]);

      out.add(new EdgeInformation(nameOfStartNode, destination, weight));
    }

    return out;
  }

  private static List<String> getDriverNodeNamesFromLine(String line) {
    final String[] lineUnits = line.split(" ");
    return new ArrayList<String>() {
      {
        for (String nodeName : lineUnits)
          add(nodeName);
      }
    };
  }

  private static List<DeliveryRequest> getDeliveryRequests(String deliveryRequestsLine) {
    final String[] lineUnits = deliveryRequestsLine.split(" ");
    return new ArrayList<DeliveryRequest>() {
      {
        for (int iii = 0; iii < lineUnits.length; iii += 2)
          add(new DeliveryRequest(lineUnits[iii], lineUnits[iii + 1]));
      }
    };
  }
}
