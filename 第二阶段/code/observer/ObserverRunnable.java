package zdk.javaConcurret2.observer;

/**
 * @author zdkswd
 * @date 2019/9/29 14:56
 */
public abstract class ObserverRunnable implements Runnable {
    protected final Observer observer;

    public ObserverRunnable(final Observer observer) {
        this.observer = observer;
    }

    protected void notifyChange(final RunnableEvent event) {
        observer.onEvent(event);
    }

    public enum RunnableState {
        RUNNING, ERROR, DONE;
    }
    public static class RunnableEvent{
        private final RunnableState state;
        private final Thread thread;
        private final Throwable throwable;

        public RunnableEvent(RunnableState state, Thread thread, Throwable throwable) {
            this.state = state;
            this.thread = thread;
            this.throwable = throwable;
        }

        public RunnableState getState() {
            return state;
        }

        public Thread getThread() {
            return thread;
        }

        public Throwable getThrowable() {
            return throwable;
        }
    }
}
