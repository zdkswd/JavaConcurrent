package zdk.JUC;

import javax.sql.rowset.serial.SQLOutputImpl;

/**
 * @author zdkswd
 * @date 2019/10/9 13:02
 */
/**
 * 作者：zdk
 * 描述：该程序的功能为使用闭锁等待五个线程顺序执行完毕
 * 时间: 2019/10/9 13:42
*/
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        final java.util.concurrent.CountDownLatch latch=new java.util.concurrent.CountDownLatch(5);
        LatchDemo latchDemo = new LatchDemo(latch);
        long startTime=System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(latchDemo).start();
        }
        //-----await------
        latch.await();
        long endTime=System.currentTimeMillis();
        System.out.println("耗时"+(endTime-startTime));
    }
}
class LatchDemo implements Runnable{
    private java.util.concurrent.CountDownLatch latch;

    public LatchDemo(java.util.concurrent.CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 50; i++) {
                    if (i % 3 == 0) {
                        System.out.println("i = " + i);
                    }
                }
            } finally {
                //这步必须执行
                System.out.println("------------------------");
                latch.countDown();
            }
        }
    }
}
