package zdk.javaConcurrent;

import java.time.ZonedDateTime;
/**
 * 作者：zdk
 * 描述：知识点6 cmd命令
 * jps 查看所有java进程
 * jstack 进程号 查看所有的线程
 * 时间: 2019/8/6 18:29
*/
public class ThreadExp {
    public static void main(String[] args) {

        //newThread();
        //stopThread();
        interruptThread();
        //suspendAndResumeThread();

    }
    /**
     * 作者：zdk
     * 描述：知识点1.1 新建线程
     * start() run()
     * 时间: 2019/8/5 22:18
     */
    private static void newThread(){
        Thread thread1=new Thread();//此时target为null
        thread1.start();//开启一个线程,start方法是在一个新的操作系统线程调用run方法
        Thread thread2=new Thread();
        thread2.run();//不能开启线程，在调用run的当前线程去执行操作。


        /**
         * 作者：zdk
         * 描述：知识点1.2 正确建立线程的两种方式
         * 时间: 2019/8/6 16:18
        */
        //1 此种方法为匿名内部类，可重写父类的run方法
        Thread newThread1=new Thread(){
            @Override
            public void run() {
                System.out.println("newThread-1");
            }
        };
        newThread1.start();
        //2 传入Runnable接口的实现,此法也是匿名内部类
        Thread newThread2=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("newThread-2");
            }
        });
        newThread2.start();
    }

    /**
     * 作者：zdk
     * 描述：知识点3  线程的终止
     * Thread.stop方法不推荐使用，太过暴力
     * 线程直接结束不知道执行到哪个语句，释放掉所有锁
     * 时间: 2019/8/6 16:26
    */
    static private void stopThread(){
        while (true){
            System.out.println("一直一直运行");
        }

    }

    /**
     * 作者：zdk
     * 描述：知识点1.4.3 线程中断
     * 直接interrupt不会有反应的
     * interrupt()实际上只是给线程设置一个中断标志，线程仍会继续运行。
     * 如果正在运行wait()，sleep()，join()这三个方法阻塞了线程，那么将会使得线程抛出InterruptedException异常，
     * 这是一个中断阻塞的过程。如果是其它的正在运行的状态，
     * 那么将不会有任何影响，也不会中断线程，或者抛出异常，只会会打上一个中断线程的标志，是否中断线程，将由程序控制。
     * 时间: 2019/8/6 16:34
    */
    private static void interruptThread(){
        Thread thread1=new Thread(){
            @Override
            public void run() {
                while (true){
                    System.out.println("thread一直执行");
                }
            }
        };
        thread1.start();
        thread1.interrupt();

        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                //优雅的方式中断线程
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("thread2 break");
                        //break while循环
                        break;
                    }
                    System.out.println("Thread2 is here");
                }
            }
        });
        thread2.start();
        thread2.interrupt();

        Thread thread3=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if (Thread.currentThread().isInterrupted()){
                        break;
                    }
                    try{
                        Thread.sleep(2000);
                    }catch (InterruptedException e){
                        //别人进行中断时会抛出异常
                        //抛出异常后会进行中断标志位的清除
                        //所以要在此处设置中断状态
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        thread3.start();

    }

    /**
     * 作者：zdk
     * 描述：知识点5 suspend和resume
     * suspend()不会释放锁
     * suspend和resume不推荐使用
     * 如果某个线程resume发生在suspend之前，则就类似于该线程冻结了
     * 时间: 2019/8/6 18:22
    */
    private static void suspendAndResumeThread(){
    }
    /**
     * 作者：zdk
     * 描述：知识点7 join() yield()
     * yield()给其他线程机会与自己竞争时间片，非常罕见使用，可能会处于debug的目的使用
     * join()等待线程结束
     * join的本质是wait
     * 线程执行完毕后，系统会调用notifyAll() 不要再Thread实例上使用wait()和notify()方法，因为这些方法是系统调用的
     * 可能会得不到想要的结果
     * 时间: 2019/8/6 18:38
    */

    /**
     * 作者：zdk
     * 描述：知识点8 守护线程 Daemon
     * 守护线程在后台默默地完成一些系统性的服务，比如垃圾回收线程，JIT线程
     * 当一个Java应用中只有守护线程时，Java虚拟机就会自然退出
     * 时间: 2019/8/6 18:50
    */

    /**
     * 作者：zdk
     * 描述：知识点9 线程优先级
     * thread.setPriority
     * 拥有更高的概率抢占系统资源
     * 时间: 2019/8/6 18:54
    */
    /**
     * 作者：zdk
     * 描述：知识点10 基本的线程同步操作
     * synchronized 1.指定加锁对象 2.直接作用与实例方法 3.直接作用于静态方法 由虚拟机内部实现
     * Object.wait() Object.notify() 用于synchronized(object)语句里面
     * wait()会释放当前线程object的所有权并等待其他线程的notify通知(随机唤醒一个) notifyAll(唤醒全部)
     *
     * 时间: 2019/8/6 18:56
    */
}
