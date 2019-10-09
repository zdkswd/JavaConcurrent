package zdk.javaConcurrent;


import java.util.HashMap;

/**
 * @author zdkswd
 * @date 2019/9/27 21:00
 */
public class DebugTry {
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name","zdk");
        hashMap.put("age","22");

        Object object = new Object();
        String name = hashMap.get("name");
        System.out.println("name = " + name);
    }
}
