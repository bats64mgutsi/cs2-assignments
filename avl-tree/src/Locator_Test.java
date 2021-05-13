import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Locator Tests")
public class Locator_Test {

  @Test
  public void shouldReturnTheFirstInstanceOfTheGivenType() {
    Locator.instance.registerSingleton(Integer.valueOf(8));
    Locator.instance.registerSingleton(new String("b"));
    Locator.instance.registerSingleton((new String("c")));

    assertEquals("b", Locator.instance.get(String.class));
  }
}
