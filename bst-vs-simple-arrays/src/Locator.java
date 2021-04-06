import java.util.ArrayList;
import java.util.List;

/**
 * A class for managing application dependencies.
 * 
 * The idea is to separate dependency creation from usage.
 */
public abstract class Locator {
  /**
   * Returns the first instance of T it finds in the registered dependencies.n
   */
  public abstract Object get(Class<?> of);

  public abstract void registerSingleton(Object instance);

  public static final Locator instance = new LocatorImpl();

  abstract public void reset();

  private static class LocatorImpl extends Locator {
    final List<Object> dependencies = new ArrayList<>();

    @Override
    public void registerSingleton(Object instance) {
      dependencies.add(instance);
    }

    @Override
    public Object get(Class<?> of) {
      for (Object e : dependencies) {
        if (of.isInstance(e))
          return e;
      }

      return null;
    }

    @Override
    public void reset() {
      dependencies.clear();
    }

  }
}