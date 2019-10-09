package zdk.JUC;

/**
 * @author zdkswd
 * @date 2019/10/9 12:12
 */
//使用代码来模拟过程
public class CASSimulater {
    public static void main(String[] args) {
        CASSimulater casSimulater = new CASSimulater();
        int excepted = casSimulater.get();
        casSimulater.compareAndSet(excepted,1);
    }

    private int value;

    //获取内存中的值
    public synchronized int get(){
        return value;
    }
    //比较
    public synchronized int compareAndSwap(int exceptedValue,int newValue){
        int oldValue=value;
        if (oldValue==exceptedValue){
            this.value=newValue;
        }
        return oldValue;
    }
    //设置
    public synchronized boolean compareAndSet(int exceptedValue,int newValue){
        return exceptedValue==compareAndSwap(exceptedValue,newValue);
    }

}
