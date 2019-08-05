package locks;

import java.util.concurrent.locks.Lock;
import sharedmemory.SharedData;

public class ReadThread extends Thread{
    
    private final Lock readLock;
    private final String name;
    
    public ReadThread(Lock readLock, String name) {
        super();
        this.readLock = readLock;
        this.name = name;
    }
    
    @Override
    public void run() {
        try{
            this.readLock.lock();
            String content = this.read();
            System.out.println(this.name + ": Content: ===========\n" + content);
        } finally {
            System.out.println("Locks: " + this.name + ": Finish Reading...");
            this.readLock.unlock();
        }
    }
    
    private String read() {
        System.out.println("Locks: " + this.name + ": Reading from shared object...");
        return SharedData.read();
    }
}
