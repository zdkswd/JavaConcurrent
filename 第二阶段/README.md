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

# 7.Immutable设计模式

不可变对象设计模式

1. 不可变对象一定是线程安全的。
2. 可变对象不一定是不安全的。

实现一个具备不可变性的类，将一个类所有的属性都设置成final的，并且只允许存在只读方法，这个类本身也是final的，也就是不允许继承。其中list成员变量要注意使用Collections.unmodifiableList(list)。

其中String，Long、Integer、Double是非常典型的不可变对象。**类和属性都是final的，所有方法均是只读的**。

如果具备不可变性的类，需要提供类似修改的功能，那就**创建一个新的不可变对象**，这是与可变对象的一个重要区别，可变对象往往是修改自己的属性。

## 利用享元模式避免创建重复对象

**利用享元模式可以减少创建对象的数量，从而减少内存占用。**Java语言里面Long、Integer、Short、Byte等这些基本数据类型的包装类都用到了享元模式。

享元模式本质上其实就是一个**对象池**，利用享元模式创建对象的逻辑也很简单：创建之前，首先去对象池里看看是不是存在；如果已经存在，就利用对象池里的对象；如果不存在，就会新创建一个对象，并且把这个新创建出来的对象放进对象池里。

例如Long这个类并没有照搬享元模式，Long内部维护了一个静态的对象池，仅缓存了[-128,127]之间的数字，这个对象池在JVM启动的时候就创建好了，而且这个对象池一直都不会变化，也就是说它是静态的。之所以采用这样的设计，是因为Long这个对象的状态共有 2的64方种，实在太多，不宜全部缓存，而[-128,127]之间的数字利用率最高。

“Integer 和 String 类型的对象不适合做锁”，其实基本上所有的基础类型的包装类都不适合做锁，因为它们内部用到了享元模式，这会导致看上去私有的锁，其实是共有的。

## 使用Immutability模式的注意事项

如果属性的类型是普通对象，那么这个普通对象的属性是可以被修改的。**在使用Immutability模式的时候一定要确认保持不变性的边界在哪里，是否要求属性对象也具备不可变性**。

一般这种模式不用同步，线程安全，所以光读取的化会比较快。

# 8.Future设计模式

同步变异步 

几个角色：

1. Future -> 代表的是未来的一个凭据
2. FutureTask -> 将调用逻辑进行隔离
3. FutureService ->桥接Future和FutureTask

# 9.Guarded Suspension设计模式

 clientThread 放请求到队列

serverThread 处理请求队列

