package zdk.javaConcurrent2.singleThread;

/**
 * @author zdkswd
 * @date 2019/9/30 9:26
 */
public class User extends Thread{
    private final String myName;

    private final String myAddress;

    private final Gate gate;

    public User(String myName, String myAddress, Gate gate) {
        this.myName = myName;
        this.myAddress = myAddress;
        this.gate = gate;
    }

    /**
     * 作者：zdk
     * 描述：使用临界资源
     * 时间: 2019/9/30 9:43
    */
    @Override
    public void run() {
        System.out.println("begin");
        while (true){
            this.gate.pass(this.myName,this.myAddress);
        }
    }

}
