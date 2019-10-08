package zdk.javaConcurrent2.singleThread;

/**
 * @author zdkswd
 * @date 2019/9/30 9:20
 */
public class Gate {
    private int counter=0;
    private String name="nobody";
    private String address ="nowhere";

    public synchronized void pass(String name,String address){
        this.counter++;
        this.name=name;
        this.address=address;
        verify();
    }

    private void verify() {
        if ( name.charAt(0)!= address.charAt(0)) {
            System.out.println("not pass");
        }
    }
}
