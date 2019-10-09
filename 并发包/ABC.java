package zdk.JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zdkswd
 * @date 2019/10/9 16:01
 */
public class ABC {
    public static void main(String[] args) {
        AlternateDemo alternateDemo = new AlternateDemo();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                alternateDemo.loopA();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                alternateDemo.loopB();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                alternateDemo.loopC();
            }
        }).start();
    }
}
class AlternateDemo{
    private int number=1;//当前正在执行线程标记

    private Lock lock=new ReentrantLock();
    private Condition condition1=lock.newCondition();
    private Condition condition2=lock.newCondition();
    private Condition condition3=lock.newCondition();

    public void loopA(){
        lock.lock();
        try {
            while (number!=1){
                condition1.await();
            }
            System.out.println("A");

            //唤醒2
            number=2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void loopB(){
        lock.lock();
        try {
            while (number!=2){
                condition2.await();
            }

            System.out.println("B");

            //唤醒
            number=3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void loopC(){
        lock.lock();
        try {
            while (number!=3){
                condition3.await();
            }
            System.out.println("C");

            //唤醒1
            number=1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}