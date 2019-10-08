package zdk.javaConcurrent2.readWriteLock;

/**
 * @author zdkswd
 * @date 2019/9/30 9:56
 */
public class ReadWriteLock {
    private int readingReaders=0;
    private int waitingReaders=0;
    private int writingWriters=0;
    private int waitingWriters=0;
    private boolean preferWriter=true;

    public ReadWriteLock() {
        this(true);
    }

    public ReadWriteLock(boolean preferWriter) {
        this.preferWriter = preferWriter;
    }

    public synchronized void readLock() throws InterruptedException {
        this.waitingReaders++;
        try {
            while (writingWriters>0||(preferWriter&&waitingWriters>0)){
                this.wait();
            }
            this.readingReaders++;
        }finally {
            this.waitingReaders--;
        }

    }
    public synchronized void readUnlock(){
        this.readingReaders--;
        this.notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        this.waitingWriters++;
        try {
            while (this.writingWriters>0||this.readingReaders>0){
                this.wait();
            }
            this.writingWriters++;
        }finally {
            this.waitingWriters--;
        }
    }
    public synchronized void writeUnlock(){
        this.writingWriters--;
        this.notifyAll();
    }
}
