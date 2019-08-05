package locks;

import java.util.concurrent.locks.Lock;
import sharedmemory.SharedData;

public class WriteThread extends Thread {

    private final Lock writeLock;
    private final String name;

    public WriteThread(Lock writeLock, String name) {
        super();
        this.writeLock = writeLock;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            this.writeLock.lock();
            this.write();
        } finally {
            System.out.println(this.name + ": Finish Writing...");
            this.writeLock.unlock();
        }
    }
    
    private void write() {
        System.out.println(this.name + ": Write to shared object...");
        SharedData.write("Locks " + this.name);
    }
}
