package zdk.javaConcurrent2.guardedSuspension;

import java.util.LinkedList;

/**
 * @author zdkswd
 * @date 2019/10/8 20:24
 */
public class RequestQueue {
    private final LinkedList<Request> queue=new LinkedList<>();

    public Request getRequest(){
        synchronized (queue){
            while (queue.size()<=0){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    return null;
                }
            }
            return queue.removeFirst();
        }
    }

    public void putRequest(Request request){
        synchronized (queue){
            queue.addLast(request);
            queue.notifyAll();
        }
    }
}
