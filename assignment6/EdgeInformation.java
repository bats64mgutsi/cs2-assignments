public class EdgeInformation {
  public final String nameOfStartNode;
  public final String nameOfEndNode;
  public final double weight;

  public EdgeInformation(String nameOfStartNode, String nameOfEndNode, double weight) {
    this.nameOfStartNode = nameOfStartNode;
    this.nameOfEndNode = nameOfEndNode;
    this.weight = weight;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof EdgeInformation))
      return false;

    final EdgeInformation other = (EdgeInformation) o;
    return other.nameOfStartNode.equals(this.nameOfStartNode) && other.nameOfEndNode.equals(this.nameOfEndNode)
        && other.weight == this.weight;
  }

  @Override
  public String toString() {
    return String.format("{nameOfStartNode: %s, nameOfEndNode: %s, weight: %f}", nameOfStartNode, nameOfEndNode, weight);
  }
}
