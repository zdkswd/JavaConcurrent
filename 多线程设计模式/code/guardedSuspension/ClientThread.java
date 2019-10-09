package zdk.javaConcurrent2.guardedSuspension;

import java.util.Random;

/**
 * @author zdkswd
 * @date 2019/10/8 20:33
 */
public class ClientThread extends Thread{
    private final RequestQueue queue;

    private final String sendValue;

    private final Random random;

    public ClientThread(RequestQueue queue, String sendValue) {
        this.queue = queue;
        this.sendValue = sendValue;
        random=new Random(System.currentTimeMillis());
    }


    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("sendValue = " + sendValue);
            queue.putRequest(new Request(sendValue));
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
