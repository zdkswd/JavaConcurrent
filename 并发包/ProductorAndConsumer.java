package zdk.JUC;

/**
 * @author zdkswd
 * @date 2019/10/9 14:54
 */
public class ProductorAndConsumer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk();
        Productor productor = new Productor(clerk);
        Consumer consumer = new Consumer(clerk);
        new Thread(productor,"生产者1-").start();
        new Thread(productor,"生产者2-").start();
        new Thread(consumer,"消费者1-").start();
        new Thread(consumer,"消费者2-").start();


    }
}
class Clerk{
    private int product=0;

    //进货
    public synchronized void get(){
        while (product>=1){
            System.out.println("产品已满");
            try {
                this.wait();
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
        this.notifyAll();

    }
    //卖货
    public synchronized void sale(){
        //如果此处是if则可能存在虚拟唤醒问题。
        while (product<=0){
            System.out.println("缺货");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+ --product);
        this.notifyAll();
    }
}
class Productor implements Runnable{
    private Clerk clerk;

    public Productor(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.get();
        }
    }
}

class Consumer implements Runnable{
    private Clerk clerk;

    public Consumer(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            clerk.sale();
        }
    }
}