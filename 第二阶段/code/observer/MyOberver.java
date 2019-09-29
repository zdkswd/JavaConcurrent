package zdk.javaConcurret2.observer;

import java.util.List;

/**
 * @author zdkswd
 * @date 2019/9/29 15:33
 */
public class MyOberver implements Observer {
    private final Object LOCK = new Object();

    public void concurrentQuery(List<String> ids){
        if (ids == null||ids.isEmpty()) {
            return;
        }
        ids.stream().forEach(id ->new Thread(new ObserverRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING,Thread.currentThread(),null));
                    System.out.println("query id "+id);
                    Thread.sleep(1_000);
                    notifyChange(new RunnableEvent(RunnableState.DONE,Thread.currentThread(),null));
                }catch (Exception e){
                    notifyChange(new RunnableEvent(RunnableState.ERROR,Thread.currentThread(),e));
                }
            }
        },id).start());
    }

    @Override
    public void onEvent(ObserverRunnable.RunnableEvent event) {
        synchronized (LOCK){
            System.out.println("event.getThread().getName() = " + event.getThread().getName());
            System.out.println("event.getState() = " + event.getState());
        }
    }
}
