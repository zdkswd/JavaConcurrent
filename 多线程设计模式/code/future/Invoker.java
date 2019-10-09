package zdk.javaConcurrent2.future;

/**
 * @author zdkswd
 * @date 2019/10/8 15:02
 */
public class Invoker {
    public static void main(String[] args) throws InterruptedException {
//        String result=get();
//        System.out.println(result);
        FutureService futureService=new FutureService();
        futureService.submit(()->{
           try {
               System.out.println("loading 10s");
               Thread.sleep(10000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           return "success";
        },System.out::println);
        System.out.println("do other things");


    }

    private static String get() throws InterruptedException {
        Thread.sleep(10000);
        return "success";
    }
}
