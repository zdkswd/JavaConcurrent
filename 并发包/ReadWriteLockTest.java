package zdk.JUC;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zdkswd
 * @date 2019/10/9 17:31
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        ReadWriteLockDemo lockDemo = new ReadWriteLockDemo();
        new Thread(()->{
            lockDemo.set(1000);
        }).start();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                lockDemo.get();
            }).start();
        }
    }
}

class ReadWriteLockDemo{
    private int number=0;

    private ReadWriteLock lock=new ReentrantReadWriteLock();

    //读
    public void get(){
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+":"+number);
        }finally {
            lock.readLock().unlock();
        }
    }

    //写
    public void set(int number){
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"写");
            this.number=number;
        }finally {
            lock.writeLock().unlock();
        }

    }
}