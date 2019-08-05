package sharedmemory;

import java.awt.Component;
import java.io.File;
import locks.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.swing.JFileChooser;

public class TestLocks {

    public static void main(String[] args) {
        SharedData.filePath = fileChoosing();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();
        Thread t1 = new ReadThread(readLock, "t1");
        Thread t2 = new ReadThread(readLock, "t2");
        Thread t3 = new ReadThread(readLock, "t3");
        Thread t4 = new ReadThread(readLock, "t4");
        Thread t5 = new ReadThread(readLock, "t5");
        Thread w1 = new WriteThread(writeLock, "w1");
        Thread w2 = new WriteThread(writeLock, "w2");
        w1.start();
        t1.start();
        t2.start();
        t3.start();
        w2.start();
        t4.start();
        t5.start();
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
