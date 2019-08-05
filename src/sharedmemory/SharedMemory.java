package sharedmemory;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import locks.ReadThread;
import locks.WriteThread;

public class SharedMemory {
//    static ExecutorService pool = Executors.newFixedThreadPool(10);
    
    public static void main(String[] args) {
        
        JFrame f = new JFrame("Mutual Exiclusion");
        f.setBounds(500, 100, 400, 400);
        JPanel panel = new JPanel();
        JButton locksB = new JButton("Using Locks");
        locksB.addActionListener(locksListener());
        panel.add(locksB);
        JButton semB = new JButton("Using Semaphores");
        semB.addActionListener(semaphoresListener());
        panel.add(semB);
        f.add(panel);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static ActionListener locksListener() {
        return ((ActionEvent ae) -> {
            SharedData.filePath = fileChoosing();
            ExecutorService pool = Executors.newFixedThreadPool(10);
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
            pool.execute(t1);
            pool.execute(t2);
            pool.execute(w2);
            pool.execute(t3);
            pool.execute(t4);
            pool.execute(w1);
            pool.execute(t5);

            pool.shutdown();
        });
    }

    private static ActionListener semaphoresListener() {
        return ((ActionEvent ae) -> {
            SharedData.filePath = fileChoosing();
            ExecutorService pool = Executors.newFixedThreadPool(10);
            Semaphore writeSem = new Semaphore(1);
            Semaphore readSem = new Semaphore(10);
            Thread t1 = new semaphore.ReadThread(readSem, "t1");
            Thread t2 = new semaphore.ReadThread(readSem, "t2");
            Thread t3 = new semaphore.ReadThread(readSem, "t3");
            Thread t4 = new semaphore.ReadThread(readSem, "t4");
            Thread t5 = new semaphore.ReadThread(readSem, "t5");
            Thread w1 = new semaphore.WriteThread(writeSem, readSem, "w1");
            Thread w2 = new semaphore.WriteThread(writeSem, readSem, "w2");

            pool.execute(t1);
            pool.execute(t2);
            pool.execute(w2);
            pool.execute(t3);
            pool.execute(t4);
            pool.execute(w1);
            pool.execute(t5);

            pool.shutdown();
        });
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
