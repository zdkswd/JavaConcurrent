package zdk.javaConcurrent2.threadLocal;

/**
 * @author zdkswd
 * @date 2019/10/9 9:33
 */
public class Try {
    private static ThreadLocal threadLocal=new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return "alax";
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            threadLocal.set("1");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            threadLocal.set("2");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
        });
        t2.start();
        t1.join();
        t2.join();
        System.out.println(threadLocal.get());
    }
}
