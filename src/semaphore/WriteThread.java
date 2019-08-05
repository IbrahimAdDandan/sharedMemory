package semaphore;

import java.util.concurrent.Semaphore;
import sharedmemory.SharedData;

public class WriteThread extends Thread {

    private final Semaphore writeSem;
    private final Semaphore readSem;
    private final String name;

    public WriteThread(Semaphore writeSem, Semaphore readSem, String name) {
        super();
        this.writeSem = writeSem;
        this.readSem = readSem;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            this.writeSem.acquire(1);
            this.readSem.acquire(10);
            this.write();
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        } finally {
            System.out.println(this.name + ": Finish Writing...");
            this.writeSem.release(1);
            this.readSem.release(10);
        }
    }

    private void write() {
        System.out.println(this.name + ": Write to shared object...");
        SharedData.write("Semaphore " + this.name);
    }
}
