package hk.edu.polyu.comp.comp2021.cvfs.model;
import java.util.*;

public class CVFS {
    private VirtualDisk currentDisk;
    Directory currentDirectory;
    final HashMap<String, Criterion> criteriaStore;

    public CVFS() {
        this.currentDirectory = new Directory("root");
        this.criteriaStore = new HashMap<>();
    }

    public void newDisk(int diskSize) {
        currentDisk = new VirtualDisk(diskSize);
        System.out.println("Created new disk with size: " + diskSize + " MB");
    }

    public void newDoc(String docName, String docType, String docContent) {
        if (currentDisk == null) {
            System.out.println("No disk is currently active.");
            return;
        }
        if (!isValidFileName(docName) || !(docType.equals("txt") || docType.equals("java") || docType.equals("html") || docType.equals("css"))) {
            System.out.println("Invalid file name or type.");
            return;
        }
        File newFile = new File(docName, docType, docContent);
        if (currentDirectory.getUsedSpace() + newFile.getSize() > currentDisk.getMaxSize()) {
            System.out.println("Not enough space on the disk.");
            return;
        }
        currentDirectory.addFile(newFile);
        currentDisk.addFile(newFile);
        System.out.println("Document created: " + docName);
    }

    public void newDir(String dirName) {
        if (currentDisk == null) {
            System.out.println("No disk is currently active.");
            return;
        }
        else if (currentDirectory.getSubdirectory(dirName) != null) {
            System.out.println("Directory already exists: " + dirName);
            return;
        }
        Directory newDir = new Directory(dirName);
        currentDirectory.addSubdirectory(newDir);
        currentDisk.addDirectory(newDir);
        System.out.println("Directory created: " + dirName);
    }

    public void delete(String fileName) {
        if (currentDisk == null) {
            System.out.println("No disk is currently active.");
            return;
        }
        if (currentDirectory.removeFile(fileName)) {
            currentDisk.removeFile(fileName);
            System.out.println("Deleted file: " + fileName);
        } else {
            System.out.println("File not found: " + fileName);
        }
    }

    public void rename(String oldFileName, String newFileName) {
        if (currentDisk == null) {
            System.out.println("No disk is currently active.");
            return;
        }
        if (currentDirectory.renameFile(oldFileName, newFileName)) {
            File renamedFile = currentDisk.getFile(oldFileName);
            if (renamedFile != null) {
                renamedFile.setName(newFileName);
                System.out.println("Renamed file from " + oldFileName + " to " + newFileName);
            }
        } else {
            System.out.println("File not found: " + oldFileName);
        }
    }

    public void changeDir(String dirName) {
        if (dirName.equals("..")) {
            if (currentDirectory.getName().equals("root")) {
                System.out.println("Already at the root directory.");
            } else {
                currentDirectory.setName("root");
            }
        } else {
            Directory newDir = currentDirectory.getSubdirectory(dirName);
            if (newDir != null) {
                currentDirectory = newDir;
            } else {
                System.out.println("Directory " + dirName + " does not exist.");
            }
        }
    }

    public void list() {
        if (currentDisk == null) {
            System.out.println("No disk is currently active.");
            return;
        }
        System.out.println("Files in directory: ");
        currentDirectory.listFiles();
    }

    public void rList() {
        if (currentDisk == null) {
            System.out.println("No disk is currently active.");
            return;
        }
        System.out.println("Recursive listing: ");
        currentDirectory.rListFiles("",0);
    }

    public void newSimpleCri(String criName, String attrName, String op, String val) {
        if (criName.length() != 2 || !criName.matches("[a-zA-Z]{2}")) {
            System.out.println("Error: criName must be exactly two English letters.");
            return;
        }
        if (!attrName.equals("name") && !attrName.equals("type") && !attrName.equals("size")) {
            System.out.println("Error: attrName must be 'name', 'type', or 'size'.");
            return;
        }

        if (attrName.equals("name") || attrName.equals("type")) {
            if (!op.equals("contains") && !op.equals("equals")) {
                System.out.println("Error: op must be 'contains' for name or 'equals' for type.");
                return;
            }
            if (!val.startsWith("\"") || !val.endsWith("\"")) {
                System.out.println("Error: val must be a string in double quotes.");
                return;
            }
            val = val.substring(1, val.length() - 1);
        } else {
            if (!(op.equals(">") || op.equals("<") || op.equals(">=") ||
                    op.equals("<=") || op.equals("==") || op.equals("!="))) {
                System.out.println("Error: op must be one of >, <, >=, <=, ==, or !=.");
                return;
            }
            try {
                int intValue = Integer.parseInt(val);
                SimpleCriterion criterion = new SimpleCriterion(criName, attrName, op, intValue);
                criteriaStore.put(criName, criterion);
                System.out.println("Simple criterion created: " + criName);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: For 'size', val must be an integer.");
                return;
            }
        }

        SimpleCriterion criterion = new SimpleCriterion(criName, attrName, op, val);
        criteriaStore.put(criName, criterion);
        System.out.println("Simple criterion created: " + criName);
    }

    public void newNegation(String criName1, String criName2) {
        Criterion criterion2 = criteriaStore.get(criName2);
        if (criterion2 == null) {
            System.out.println("Error: Criterion " + criName2 + " not found.");
            return;
        }
        NegationCriterion negation = new NegationCriterion(criterion2);
        criteriaStore.put(criName1, negation);
        System.out.println("Negation criterion created: " + criName1);
    }

    public void newBinaryCri(String criName1, String criName3, String logicOp, String criName4) {
        Criterion criterion3 = criteriaStore.get(criName3);
        Criterion criterion4 = criteriaStore.get(criName4);
        if (criterion3 == null || criterion4 == null) {
            System.out.println("Error: One or both criteria not found.");
            return;
        }
        BinaryCriterion binaryCriterion = new BinaryCriterion(criterion3, logicOp, criterion4);
        criteriaStore.put(criName1, binaryCriterion);
        System.out.println("Binary criterion created: " + criName1);
    }

    public void printAllCriteria() {
        System.out.println("Defined criteria:");
        for (String criName : criteriaStore.keySet()) {
            Criterion criterion = criteriaStore.get(criName);
            String attrName = "N/A";
            String op = "N/A";
            String val = "N/A";
            String logicOp = "N/A";
            boolean isDocument = criterion instanceof IsDocumentCriterion;
            if (criterion instanceof SimpleCriterion) {
                SimpleCriterion simpleCriterion = (SimpleCriterion) criterion;
                attrName = simpleCriterion.getAttrName();
                op = simpleCriterion.getOp();
                val = simpleCriterion.getVal().toString();
            }
            if (criterion instanceof BinaryCriterion) {
                logicOp = ((BinaryCriterion) criterion).getLogicOp();
            }
            System.out.printf("Name: %s, Attribute: %s, Operator: %s, Value: %s, Logic Operator: %s, Is Document: %b%n",
                    criName, attrName, op, val, logicOp, isDocument);
        }
    }

    public void search(String criName) {
        if (!criteriaStore.containsKey(criName)) {
            System.out.println("Criterion not found: " + criName);
            return;
        }

        Criterion criterion = criteriaStore.get(criName);
        List<File> matchedFiles = new ArrayList<>();

        for (File file : currentDirectory.getFiles().values()) {
            if (criterion.evaluate(file)) {
                matchedFiles.add(file);
            }
        }

        System.out.println("Found " + matchedFiles.size() + " files:");
        int totalSize = matchedFiles.stream().mapToInt(File::getSize).sum();
        for (File file : matchedFiles) {
            System.out.println("File: " + file.getName());
        }
        System.out.println("Total size: " + totalSize + " bytes");
    }

    public void rSearch(String criName) {
        if (!criteriaStore.containsKey(criName)) {
            System.out.println("Criterion not found: " + criName);
            return;
        }

        Criterion criterion = criteriaStore.get(criName);
        List<File> matchedFiles = new ArrayList<>();
        rSearchInDirectory(currentDirectory, criterion, matchedFiles);
        System.out.println("Found " + matchedFiles.size() + " files recursively:");
        int totalSize = matchedFiles.stream().mapToInt(File::getSize).sum();
        for (File file : matchedFiles) {
            System.out.println("File: " + file.getName());
        }
        System.out.println("Total size: " + totalSize + " bytes");
    }

    private void rSearchInDirectory(Directory directory, Criterion criterion, List<File> matchedFiles) {
        for (File file : directory.getFiles().values()) {
            if (criterion.evaluate(file)) {
                matchedFiles.add(file);
            }
        }
        for (Directory subDirectory : directory.getSubDirectories()) {
            rSearchInDirectory(subDirectory, criterion, matchedFiles);
        }
    }

    public void save(String path) {
        currentDisk.save(path);
        System.out.println("Disk saved to: " + path);
    }

    public void load(String path) {
        currentDisk.load(path);
        System.out.println("Disk loaded from: " + path);
    }

    private boolean isValidFileName(String name) {
        return name.matches("[a-zA-Z0-9]{1,10}");
    }
}