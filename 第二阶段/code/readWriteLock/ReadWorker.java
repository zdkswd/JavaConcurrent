package zdk.javaConcurrent2.readWriteLock;

/**
 * @author zdkswd
 * @date 2019/9/30 21:40
 */
public class ReadWorker extends Thread{
    private final SharedData data;


    public ReadWorker(SharedData data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while (true){
             char[] readBuf=data.read();
             System.out.println(Thread.currentThread().getName()+" read "+String.valueOf(readBuf));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
