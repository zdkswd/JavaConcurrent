package zdk.javaConcurret2.observer;

/**
 * @author zdkswd
 * @date 2019/9/29 14:57
 */
public interface Observer {
    void onEvent(ObserverRunnable.RunnableEvent event);
}
