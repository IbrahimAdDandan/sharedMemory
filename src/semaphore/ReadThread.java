package semaphore;

import java.util.concurrent.Semaphore;
import sharedmemory.SharedData;


public class ReadThread extends Thread {

    private final Semaphore readSem;
    private final String name;

    public ReadThread(Semaphore readSem, String name) {
        super();
        this.readSem = readSem;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            this.readSem.acquire(1);
            String content = this.read();
            System.out.println(this.name + ": Content:  ===========\n" + content);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        } finally {
            System.out.println("Semaphore: " + this.name + ": Finish Reading...");
            this.readSem.release(1);
        }
    }

    private String read() {
        System.out.println("Semaphore: " + this.name + ": Reading from shared object...");
        return SharedData.read();
    }
}
