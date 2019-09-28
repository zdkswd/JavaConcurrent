package zdk.javaConcurrent;

/**
 * 作者：zdk
 * 描述：1.4 Thread API
 * 时间: 2019/9/4 20:03
 */
public class ThreadAPI {
    public static void main(String[] args) throws Exception{
        /**
         * 作者：zdk
         * 描述：3 守护线程
         * 时间: 2019/9/5 20:04
        */
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.setDaemon(true);
        t1.start();

        /**
         * 作者：zdk
         * 描述：1.4.2 join()
         * 时间: 2019/9/5 20:05
        */
        Thread t2=new Thread(()-> {
            try {
                System.out.println("Thread t2");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3=new Thread(()-> {
            try {
                System.out.println("Thread t3");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();
        t3.start();
        t2.join();
        t3.join();
        System.out.println(Thread.currentThread().getName());

        Thread t4=new Thread(()-> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("收到中断信号");
                e.printStackTrace();
            }
        });
        t4.start();
        System.out.println("发出中断信号");
        t4.interrupt();

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("关闭之前处理一些事情");
        }));
    }
}
