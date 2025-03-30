package hk.edu.polyu.comp.comp2021.cvfs.controller;
import hk.edu.polyu.comp.comp2021.cvfs.model.CVFS;

public class CVFSController {
    private final CVFS cvfs;

    public CVFSController(){
        cvfs = new CVFS();
    }

    public void handleCommand(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {
            case "newDisk":
                if (parts.length == 2) {
                    int size = Integer.parseInt(parts[1]);
                    cvfs.newDisk(size);
                } else {
                    System.out.println("Usage: newDisk <size>");
                }
                break;
            case "newDoc":
                if (parts.length == 4) {
                    cvfs.newDoc(parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("Usage: newDoc <name> <type> <content>");
                }
                break;
            case "newDir":
                if (parts.length == 2) {
                    cvfs.newDir(parts[1]);
                } else {
                    System.out.println("Usage: newDir <name>");
                }
                break;
            case "delete":
                if (parts.length == 2) {
                    cvfs.delete(parts[1]);
                } else {
                    System.out.println("Usage: delete <name>");
                }
                break;
            case "rename":
                if (parts.length == 3) {
                    cvfs.rename(parts[1], parts[2]);
                } else {
                    System.out.println("Usage: rename <oldName> <newName>");
                }
                break;
            case "changeDir":
                if (parts.length == 2) {
                    cvfs.changeDir(parts[1]);
                } else {
                    System.out.println("Usage: changeDir <name>");
                }
                break;
            case "list":
                cvfs.list();
                break;
            case "rList":
                cvfs.rList();
                break;
            case "newSimpleCri":
                if (parts.length == 5) {
                    String criName = parts[1];
                    String attrName = parts[2];
                    String operation = parts[3];
                    String value = parts[4];
                    cvfs.newSimpleCri(criName, attrName, operation, value);
                } else {
                    System.out.println("Usage: newSimpleCri <criName> <attrName> <operation> <value>");
                }
                break;
            case "newNegation":
                if (parts.length == 3) {
                    String criName1 = parts[1];
                    String criName2 = parts[2];
                    cvfs.newNegation(criName1, criName2);
                }
                else {
                    System.out.println("Usage: newNegation <newCriName> <existingCriName>");
                }
                break;
            case "newBinaryCri":
                if (parts.length == 5) {
                    cvfs.newBinaryCri(parts[1], parts[2], parts[3], parts[4]);
                }
                else {
                    System.out.println("Usage: newBinaryCri <newCriName> <criName3> <logicOp> <criName4>");
                }
                break;
            case "printAllCriteria":
                cvfs.printAllCriteria();
                break;
            case "search":
                if (parts.length == 2) {
                    cvfs.search(parts[1]);
                }
                else {
                    System.out.println("Usage: search <criName>");
                }
                break;
            case "rSearch":
                if (parts.length == 2) {
                    cvfs.rSearch(parts[1]);
                }
                else {
                    System.out.println("Usage: rSearch <criName>");
                }
                break;
            case "save":
                if (parts.length == 2) {
                    String path = parts[1];
                    cvfs.save(path);
                } else {
                    System.out.println("Usage: save <path>");
                }
                break;
            case "load":
                if (parts.length == 2) {
                    String path = parts[1];
                    cvfs.load(path);
                } else {
                    System.out.println("Usage: load <path>");
                }
                break;
            case "saveCriteria":
                System.out.println("Usage: saveCriteria <filename>");
                break;
            case "loadCriteria":
                System.out.println("Usage: loadCriteria <filename>");
                break;
            case "undo":
                System.out.println("undo");
                break;
            case "redo":
                System.out.println("redo");
                break;
            default:
                System.out.println("Unknown command: " + parts[0]);
        }
    }
}