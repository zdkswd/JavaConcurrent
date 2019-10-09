package zdk.javaConcurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class SimpleThreadPool1 extends Thread {
    private int size;

    private int queueSize;

    private final static int DEFAULT_SIZE = 10;

    private static volatile int seq = 0;

    private static final int DEFAULT_TASK_QUEUE_SIZE = 2000;

    private final static String THREAD_PREFIX = "SimpleThreadPool1-";

    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group1");

    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();

    private final static List<WorkTask> THREAD_QUEUE = new ArrayList<>();

    private final DiscardPolicy discardPolicy;

    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("discard this task");
    };

    private volatile boolean destory = false;

    private int min;

    private int max;

    private int active;

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getActive() {
        return active;
    }

    @Override
    public void run() {
        while (!destory) {
            System.out.printf("min:%d,max:%d,current:%d,queueSize:%d\n", this.min, this.max, this.size, TASK_QUEUE.size());
            try {
                Thread.sleep(5000);
                if (TASK_QUEUE.size() > active && size < active) {
                    for (int i = size; i < active; i++) {
                        createWorkTask();
                    }
                    System.out.println("WorkTask increase");
                    size = active;
                } else if (TASK_QUEUE.size() > max && size < max) {
                    for (int i = size; i < max; i++) {
                        createWorkTask();
                    }
                    System.out.println("WorkTask num max");
                    size = max;
                }
                if (TASK_QUEUE.isEmpty() && size > active) {
                    System.out.println("WorkTask reduce");
                    synchronized (TASK_QUEUE) {
                        int release = size - active;
                        for (Iterator<WorkTask> it = THREAD_QUEUE.iterator(); it.hasNext(); ) {
                            if (release <= 0)
                                break;
                            WorkTask workTask = it.next();
                            if (workTask.taskState == TaskState.BLOCKED) {
                                workTask.close();
                                workTask.interrupt();
                                it.remove();
                                release--;
                            }

                        }
                        size = active;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class DiscardException extends Exception {
        public DiscardException(String message) {
            super(message);
        }
    }

    public interface DiscardPolicy {
        void discard() throws DiscardException;
    }

    public SimpleThreadPool1() {
        this(4, 8, 12, DEFAULT_TASK_QUEUE_SIZE, DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool1(int min, int active, int max, int queueSize, DiscardPolicy discardPolicy) {
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

    private void init() {
        for (int i = 0; i < min; i++) {
            createWorkTask();
        }
        size = min;
        this.start();
    }

    public boolean isDestory() {
        return destory;
    }

    private enum TaskState {
        FREE, RUNNING, BLOCKED, DEAD
    }

    public void createWorkTask() {
        WorkTask workTask = new WorkTask(GROUP, THREAD_PREFIX + (seq++));
        workTask.start();
        THREAD_QUEUE.add(workTask);
    }

    //线程池内部线程类
    private static class WorkTask extends Thread {
        private volatile TaskState taskState = TaskState.FREE;

        public WorkTask(ThreadGroup group, String name) {
            super(group, name);
        }

        public TaskState getTaskState() {
            return taskState;
        }

        public void close() {
            taskState = TaskState.DEAD;
        }

        @Override
        public void run() {
            OUTER:
            while (taskState != TaskState.DEAD) {
                Runnable runnable;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCKED;
                            //wait到MONITOR的队列当中
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Closed");
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();
                }
                if (runnable != null) {
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }
    }

    //对外提供接口
    //有读操作又有写操作，所以加锁
    public void submit(Runnable runnable) {
        if (destory)
            throw new IllegalStateException("thread pool has been destroyed");
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() >= queueSize) {
                try {
                    discardPolicy.discard();
                } catch (DiscardException e) {
                    e.printStackTrace();
                }
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }

    public int getSize() {
        return size;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void shutDown() throws InterruptedException {
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(50);
        }
        int val = THREAD_QUEUE.size();
        while (val > 0) {
            for (WorkTask task : THREAD_QUEUE) {
                if (task.taskState == TaskState.BLOCKED) {
                    task.close();
                    task.interrupt();
                    val--;
                } else {
                    Thread.sleep(10);
                }
            }
        }
        destory = true;
        System.out.println("thread pool shutdown");
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleThreadPool1 threadPool = new SimpleThreadPool1();
        IntStream.rangeClosed(0, 40)
                .forEach(i -> threadPool.submit(() -> {
                    System.out.println(i);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {

                    }
                }));
        Thread.sleep(10_000);
        threadPool.shutDown();
        threadPool.submit(() -> System.out.println("123"));
    }
}
