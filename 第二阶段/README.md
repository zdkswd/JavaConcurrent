# 第二阶段

# 1.单例设计模式与多线程

参见本人设计模式代码<https://github.com/zdkswd/designPattern/tree/master/designPattern/singleton>

在双重检查中使用关键字volatile以避免优化带来的重排可能导致的问题。

> private static volatile SingleTon06 instance;

效率会降低的。优雅的实现还是7，8。

# 2.WaitSet

所有对象都有一个wait set，用于存放调用该对象wait()的线程。即第一阶段图的右边的等待线程。

线程被notify之后，不一定立即得到执行。要获得锁。

线程从wait set中被唤醒的顺序不一定时FIFO。

线程在wait set出来后抢锁，抢到锁后，**会从wait()后继续执行。会有地址的记录**

# 3.volatile

## 3.1 volatile自身特性

理解volatile特性的一个好方法是把对volatile变量的单个读/写，看成是使用同一个锁对这
些单个读/写操作做了同步。

```java
class VolatileFeaturesExample {
    volatile long vl = 0L; // 使用volatile声明64位的long型变量
    public void set(long l) {
    	vl = l; // 单个volatile变量的写
    }
    public void getAndIncrement () {
    	vl++; // 复合（多个）volatile变量的读/写
    }
    public long get() {
    	return vl; // 单个volatile变量的读
    }
}
```

假设有多个线程分别调用上面程序的3个方法，这个程序在语义上和下面程序等价。

```java
class VolatileFeaturesExample {
    long vl = 0L; // 64位的long型普通变量
    public synchronized void set(long l) { // 对单个的普通变量的写用同一个锁同步
    	vl = l;
    }
    public void getAndIncrement () { // 普通方法调用
    	long temp = get(); // 调用已同步的读方法
    	temp += 1L; // 普通写操作
    	set(temp); // 调用已同步的写方法
    }
    public synchronized long get() { // 对单个的普通变量的读用同一个锁同步
    	return vl;
    }
}
```

tile变量自身具有下列特性。

1. **可见性**，对一个volatile变量的读，总是能看到（任意线程）对这个volatile变量最后的写
   入。
2. **原子性**：对任意单个volatile变量的读/写具有原子性，但类似于volatile++这种复合操作不
   具有原子性。

## 3.2 volatile对线程的内存可见性的影响

从JDK5开始，volatile变量的写-读可以实现线程之间的通信。

# 4. 多线程中的观察者模式

```
package zdk.javaConcurrent2.observer;
```

见代码。

# 5.单线程门

```
package zdk.javaConcurrent2.singleThread;
```

user使用资源者，gate临界资源

# 6.ReadWriteLock

读时读并行化

读时写不允许

写时写不允许

```
package zdk.javaConcurrent2.readWriteLock;
```

