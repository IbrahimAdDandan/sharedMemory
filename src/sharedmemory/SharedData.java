package sharedmemory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SharedData {
    
    public static String filePath;
    
    public static String read() {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 
        return null;
    }
    
    public static void write(String threadName) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            fw.write("This line added by: " + threadName + "\n");
            fw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
