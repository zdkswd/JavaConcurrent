package zdk.javaConcurrent2.singleThread;

/**
 * @author zdkswd
 * @date 2019/9/30 9:41
 */
public class Client {
    public static void main(String[] args) {
        Gate gate = new Gate();
        User buser = new User("buser", "bj", gate);
        User cuser = new User("cuser", "cs", gate);
        User duser = new User("duser", "dl", gate);
        buser.start();
        cuser.start();
        duser.start();
    }
}
