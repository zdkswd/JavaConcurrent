package zdk.JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zdkswd
 * @date 2019/10/9 14:54
 */
public class ProductorAndConsumerLock {
    public static void main(String[] args) {
        ClerkLock clerk = new ClerkLock();
        ProductorLock productor = new ProductorLock(clerk);
        ConsumerLock consumer = new ConsumerLock(clerk);
        new Thread(productor,"生产者1-").start();
        new Thread(productor,"生产者2-").start();
        new Thread(consumer,"消费者1-").start();
        new Thread(consumer,"消费者2-").start();
    }
}

class ClerkLock{
    private int product=0;

    private Lock lock=new ReentrantLock();
    private Condition condition=lock.newCondition();
    //进货
    public  void get(){
        lock.unlock();
        try {
            while (product>=1){
                System.out.println("产品已满");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ ++product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
    //卖货
    public void sale(){
        try {
            //如果此处是if则可能存在虚拟唤醒问题。
            while (product<=0){
                System.out.println("缺货");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+ --product);
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}

class ProductorLock implements Runnable{
    private ClerkLock clerk;

    public ProductorLock(ClerkLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.get();
        }
    }
}

class ConsumerLock implements Runnable{
    private ClerkLock clerk;

    public ConsumerLock(ClerkLock clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}