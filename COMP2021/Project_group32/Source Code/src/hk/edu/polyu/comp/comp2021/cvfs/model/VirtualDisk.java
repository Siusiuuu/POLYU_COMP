package hk.edu.polyu.comp.comp2021.cvfs.model;
import java.io.*;
import java.util.*;

public class VirtualDisk {
    private final int maxSize;
    private int currentSize;
    private final HashMap<String, File> files;
    private final HashMap<String, Directory> directories;
    private Directory rootDirectory;

    public VirtualDisk(int maxSize) {
        this.maxSize = maxSize * 1024 * 1024;
        this.rootDirectory = new Directory("root");
        this.currentSize = 0;
        this.files = new HashMap<>();
        this.directories = new HashMap<>();
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void addFile(File file) {
        files.put(file.getName(), file);
        currentSize += file.getSize();
    }

    public void removeFile(String fileName) {
        File file = files.remove(fileName);
        if (file != null) {
            currentSize -= file.getSize();
        }
    }

    public void save(String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(rootDirectory);
        } catch (IOException e) {
            System.err.println("Error saving virtual disk: " + e.getMessage());
        }
    }

    public void load(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            rootDirectory = (Directory) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading virtual disk: " + e.getMessage());
        }
    }


    public void addDirectory(Directory directory) {
        directories.put(directory.getName(), directory);
    }

    public File getFile(String fileName) {
        return files.get(fileName);
    }
}