package src;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * WordApp is split into two threads: The UI/Main Thread, and the Animation Thread.
 * 
 * The Animation Thread is responsible for animating the words on screen. It has to listen for three
 * events from the UI Thread: onStart, onStop, OnData, and onExit events.
 * 
 * To achieve this listening the Animation Thread is further split into two parts: The EventLoop,
 * and The EventLoopListeners.
 * 
 * The EventLoop is the loop that polls for events from the UI Thread in every iteration. It then
 * decodes these events events and passes them to the EventLoopListeners if necessary.
 * 
 * The event loop keeps an internal state of whether the animation thread is paused or not.
 * Initially it sets this to paused, in which case the event listeners will not be notified of any
 * onAnimate calls.
 * 
 * At a rate of 30Hz the event loop notifies the event loop listeners to draw.
 */
public abstract class EventLoop extends Thread {
  static public enum EventType {
    START, STOP, EXIT, DATA,
  }

  static public class Event {
    public final EventType type;
    public final String data;

    public Event(EventType type, String data) {
      this.type = type;
      this.data = data;
    }

    public boolean is(EventType type) {
      return this.type == type;
    }
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

    /**
     * Called when the event loop has received data.
     */
    public void onData(String data);

    /**
     * Called when the UI Thread has requested that the animation be paused and all animation state
     * be reset.
     */
    public void onStop();

    /**
     * Called when the UI Thread has requested that animations begin.
     */
    public void onStart();
  }


  static private class EventLoopImpl extends EventLoop {
    protected boolean isPaused = true;
    protected ConcurrentLinkedQueue<Event> eventQueue = new ConcurrentLinkedQueue<>();
    protected LinkedList<EventLoopListener> listeners = new LinkedList<>();

    protected TimeService timeService = new TimeService();

    @Override
    public void dispatch(EventType event) {
      eventQueue.add(new Event(event, null));
    }

    @Override
    public void dispatchData(String data) {
      eventQueue.add(new Event(EventType.DATA, data));
    }

    @Override
    public void run() {
      while (true) {
        final Event nextEventToProcess = eventQueue.poll();
        if (nextEventToProcess != null) {
          if (nextEventToProcess.is(EventType.START)) {
            // Reset deltaTime timer to avoid jumping animations.
            timeService.reset();
            listeners.forEach(listener -> listener.onStart());
          } else if (nextEventToProcess.is(EventType.STOP)) {
            listeners.forEach(listener -> listener.onStop());
          } else if (nextEventToProcess.is(EventType.DATA)) {
            listeners.forEach(listener -> listener.onData(nextEventToProcess.data));
          } else if (nextEventToProcess.is(EventType.EXIT)) {
            break;
          }

          isPaused = nextEventToProcess.is(EventType.STOP);
        }

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


  public abstract void dispatch(EventType eventType);

  public abstract void dispatchData(String data);

  public abstract void addListener(EventLoopListener listener);

  public static EventLoop makeInstance() {
    return new EventLoopImpl();
  }
}
