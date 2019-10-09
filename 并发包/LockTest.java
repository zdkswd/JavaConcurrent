package zdk.JUC;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zdkswd
 * @date 2019/10/9 14:15
 */
public class LockTest {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(ticket,"一号窗口").start();
        new Thread(ticket,"二号窗口").start();
        new Thread(ticket,"三号窗口").start();
    }
}
class Ticket implements Runnable{
    private int tick=100;

    private Lock lock=new ReentrantLock();

    @Override
    public void run() {
        while (true){
            lock.lock();
            try {
                if (tick>0){
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("tick = " + tick--);
                }
            }finally {
                //---------!!!--------必做
                lock.unlock();
            }
        }
    }
}
