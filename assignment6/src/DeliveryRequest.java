public class DeliveryRequest {
  public final String nameOfPickUpNode;
  public final String nameOfDropOffNode;

  public DeliveryRequest(String nameOfPickUpNode, String nameOfDropOffNode) {
    this.nameOfPickUpNode = nameOfPickUpNode;
    this.nameOfDropOffNode = nameOfDropOffNode;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DeliveryRequest))
      return false;

    final DeliveryRequest other = (DeliveryRequest) o;
    return other.nameOfPickUpNode.equals(this.nameOfPickUpNode)
        && other.nameOfDropOffNode.equals(this.nameOfDropOffNode);
  }

  @Override
  public String toString() {
    return String.format("{nameOfPickUpNode: %s, nameOfDropOffNode: %s}", nameOfPickUpNode, nameOfDropOffNode);
  }
}
