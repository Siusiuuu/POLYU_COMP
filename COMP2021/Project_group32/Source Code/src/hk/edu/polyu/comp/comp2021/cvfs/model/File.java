package hk.edu.polyu.comp.comp2021.cvfs.model;

public class File {
    private String name;
    private final String type;
    private final String content;

    public File(String name, String type, String content) {
        this.name = name;
        this.type = type;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return 40 + content.length() * 2;
    }

    public boolean isDocument() {
        return type.equals("txt") || type.equals("java") || type.equals("html") || type.equals("css");
    }
}