import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * WordApp is split into two threads: The UI/Main Thread, and the Animation Thread.
 * 
 * The Animation Thread is responsible for animating the words on screen. It has to listen for three
 * events from the UI Thread: onResume, onPause, and onExit events.
 * 
 * To achieve this listening the Animation Thread is furtther split into two parts: The EventLoop,
 * and The EventLoopListeners.
 * 
 * The EventLoop is the loop that polls for events from the UI Thread in every iteration. It then
 * decodes these events as onResume, onPause, and onExit events and passes them to the
 * EventLoopListeners.
 * 
 * The event loop keeps an internal state of whether the animation thread is paused or not.
 * Initially it sets this to paused, in which case the event listeners will not be notified of any
 * onAnimate calls.
 * 
 * At a rate of 30Hz the event loop notifies the event loop listeners to draw.
 */
public abstract class EventLoop extends Thread {
  static public enum Event {
    RESUME, PAUSE, EXIT
  }

  static public interface EventLoopListener {
    /**
     * Called when the listener should draw its UI.
     * 
     * Animations should not be done here. This method should only draw the current state of the
     * listener.
     * 
     * This method is called at 30Hz.
     */
    public void onDraw();

    /**
     * Steps the animation function for the listener.
     * 
     * This function should not draw, it should only animate the UI state.
     * 
     * This function is called immediately after the onDraw method if the animation is not paused.
     * Otherwise only onDraw is called.
     */
    public void onAnimate(long deltaMillis);
  }


  static private class EventLoopImpl extends EventLoop {
    protected boolean isPaused = true;
    protected ConcurrentLinkedQueue<Event> eventQueue = new ConcurrentLinkedQueue<>();
    protected LinkedList<EventLoopListener> listeners = new LinkedList<>();

    protected TimeService timeService = new TimeService();

    @Override
    public void dispatch(Event event) {
      eventQueue.add(event);
    }

    @Override
    public void run() {
      while (true) {
        final Event nextEventToProcess = eventQueue.poll();
        if (nextEventToProcess != null) {
          if (nextEventToProcess == Event.RESUME) {
            // Reset deltaTime timer to avoid jumping animations.
            timeService.reset();
          }

          isPaused = nextEventToProcess == Event.PAUSE;
          if (nextEventToProcess == Event.EXIT)
            break;
        }


        if (!isPaused)
          drawAndAnimateListeners();
      }
    }

    public void drawAndAnimateListeners() {
      listeners.forEach(listener -> {
        listener.onDraw();

        if (!isPaused)
          listener.onAnimate(timeService.deltaMillis());
      });

      try {
        // Approximately 1/30 th of a second
        Thread.sleep(33);
      } catch (Exception e) {
        System.err.print(e);
      }
    }

    @Override
    public void addListener(EventLoopListener listener) {
      listeners.add(listener);
    }
  }


  public abstract void dispatch(Event event);

  public abstract void addListener(EventLoopListener listener);

  public static EventLoop makeInstance() {
    return new EventLoopImpl();
  }
}
