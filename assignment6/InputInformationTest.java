import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class InputInformationTest {

  private Scanner makeScanner(String text){
    final InputStream inStream = new ByteArrayInputStream(text.getBytes());
    return new Scanner(inStream);
  }

  @Test
  public void shouldReturnNoEdgesWhenTheNumberOfNodesIsZero(){
    final InputInformation instance = InputInformation.readFromScanner(makeScanner("0\n0\n0"));
    assertThat(instance.edges, is(empty()));
  }

  @Test
  public void shouldReturnThe2EdgesWhenTheNumberOfNodesIs1AndThatNodeConnectsToTwoOtherNodes(){
    final InputInformation instance = InputInformation.readFromScanner(makeScanner("1\n0 2 34 5 76\n0\n0"));
    assertThat(instance.edges, hasSize(2));
    assertEquals(instance.edges.get(0).toString(), (new EdgeInformation("0", "2", 34.0).toString()));
    assertEquals(instance.edges.get(1).toString(), (new EdgeInformation("0", "5", 76.0).toString()));
  }

  @Test
  public void shouldReturnNoEdgesWhenTheNumberOfNodesIs1AndThatNodeDoesNotConnectToAnyOtherNodes(){
    final InputInformation instance = InputInformation.readFromScanner(makeScanner("1\n0\n0\n0"));
    assertThat(instance.edges, is(empty()));
  }

  @Test
  public void shouldReturnNoDriverNodeNamesWhenTheNumberOfUberHaulDriversIsZero(){
    final InputInformation instance = InputInformation.readFromScanner(makeScanner("0\n0\n0"));
    assertThat(instance.namesOfDriverNodes, is(empty()));
  }

  @Test
  public void shouldReturnThe2DriverNodeNamesWhenTheNumberOfUberHaulDriversIs2(){
    final InputInformation instance = InputInformation.readFromScanner(makeScanner("0\n2\n5 4\n0"));
    assertThat(instance.namesOfDriverNodes, contains("5", "4"));
  }

  @Test
  public void shouldReturnNoDeliveryRequestsWhenTheNumberOfDeliveryRequestsIsZero(){
    final InputInformation instance = InputInformation.readFromScanner(makeScanner("0\n0\n0"));
    assertThat(instance.deliveryRequests, is(empty()));
  }

  @Test
  public void shouldReturnThe2DeliveryRequestsWhenTheNumberOfDeliveryRequestsIs2(){
    final InputInformation instance = InputInformation.readFromScanner(makeScanner("0\n0\n2\n1 5 7 4"));
    assertThat(instance.deliveryRequests, hasSize(2));
    assertEquals(instance.deliveryRequests.get(0).toString(), (new DeliveryRequest("1", "5")).toString());
    assertEquals(instance.deliveryRequests.get(1).toString(), (new DeliveryRequest("7", "4")).toString());
  }
}
