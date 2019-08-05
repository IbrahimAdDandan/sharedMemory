package sharedmemory;

import java.awt.Component;
import java.io.File;
import semaphore.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import javax.swing.JFileChooser;

public class TestSemaphores {
    
    public static void main(String[] args) {
        SharedData.filePath = fileChoosing();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        Semaphore writeSem = new Semaphore(1);
        Semaphore readSem = new Semaphore(10);
        Thread t1 = new ReadThread(readSem, "t1");
        Thread t2 = new ReadThread(readSem, "t2");
        Thread t3 = new ReadThread(readSem, "t3");
        Thread t4 = new ReadThread(readSem, "t4");
        Thread t5 = new ReadThread(readSem, "t5");
        Thread w1 = new WriteThread(writeSem, readSem, "w1");
        Thread w2 = new WriteThread(writeSem, readSem, "w2");
        
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(w2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(w1);
        pool.execute(t5);
        
        pool.shutdown();
    }
    
    private static String fileChoosing() {
        Component frame = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (chooser.showOpenDialog(frame) == JFileChooser.OPEN_DIALOG) {
            File file = chooser.getSelectedFile();
            return file.getPath();
        }
        return null;
    }
}
