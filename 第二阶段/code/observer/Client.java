package zdk.javaConcurret2.observer;

import java.util.Arrays;

/**
 * @author zdkswd
 * @date 2019/9/29 16:02
 */
public class Client {
    public static void main(String[] args) {
        MyOberver myOberver = new MyOberver();
        myOberver.concurrentQuery(Arrays.asList("1","2"));
    }
}
