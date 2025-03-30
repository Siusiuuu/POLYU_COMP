package hk.edu.polyu.comp.comp2021.cvfs.model;
import java.util.*;

public class Directory {
    private String name;
    private final HashMap<String, File> files;
    private final HashMap<String, Directory> subdirectories;

    public Directory(String name) {
        this.name = name;
        this.files = new HashMap<>();
        this.subdirectories = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String NewName) {
        this.name = NewName;
    }

    public void addFile(File file) {
        files.put(file.getName(), file);
    }

    public boolean removeFile(String fileName) {
        return files.remove(fileName) != null;
    }

    public boolean renameFile(String oldFileName, String newFileName) {
        File file = files.remove(oldFileName);
        if (file != null) {
            file.setName(newFileName);
            files.put(newFileName, file);
            return true;
        }
        return false;
    }

    public void addSubdirectory(Directory directory) {
        subdirectories.put(directory.getName(), directory);
    }

    public Directory getSubdirectory(String dirName) {
        return subdirectories.get(dirName);
    }

    public HashMap<String, File> getFiles() {
        return files;
    }

    public void listFiles() {
        if (files.isEmpty()) {
            System.out.println("No files in this directory.");
            return;
        }
        for (File file : files.values()) {
            System.out.println("File: " + file.getName() + " (Type: " + file.getType() + ", Size: " + file.getSize() + " bytes)");
        }
    }

    public void rListFiles(String indent, int level) {
        for (File file : files.values()) {
            System.out.println(indent + "File: " + file.getName() + " (Type: " + file.getType() + ", Size: " + file.getSize() + " bytes)");
        }
        for (Directory dir : subdirectories.values()) {
            System.out.println(indent + "Directory: " + dir.getName());
            dir.rListFiles(indent + "    ", level + 1);
        }
    }

    public List<Directory> getSubDirectories() {
        return new ArrayList<>(subdirectories.values());
    }

    public int getUsedSpace() {
        int totalSize = 40;
        for (File file : files.values()) {
            totalSize += file.getSize();
        }
        return totalSize;
    }

    public Object getFile(String Doc) {
        return files.get(Doc);
    }
}