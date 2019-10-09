package zdk.javaConcurrent2.guardedSuspension;

/**
 * @author zdkswd
 * @date 2019/10/8 20:24
 */
public class Request {
    final private String value;

    public Request(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
