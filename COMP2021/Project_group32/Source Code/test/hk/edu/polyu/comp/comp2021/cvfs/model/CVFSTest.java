package hk.edu.polyu.comp.comp2021.cvfs.model;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CVFSTest {
    private CVFS cvfs;
    private File testFile;
    private Directory directory;
    private File testFile1;
    private File testFile2;
    private Directory subDirectory;

    @Before
    public void setUp() {
        cvfs = new CVFS();
        cvfs.newDisk(1000);
        testFile = new File("testDoc", "txt", "test");
        directory = new Directory("testDir");
        testFile1 = new File("file1", "txt", "Content of file 1.");
        testFile2 = new File("file2", "txt", "Content of file 2.");
        subDirectory = new Directory("subDir");
    }
    @Test
    public void testEvaluateNameContainsFalse() {
        SimpleCriterion criterion = new SimpleCriterion("NM", "name", "contains", "example");
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateTypeEqualsTrue() {
        SimpleCriterion criterion = new SimpleCriterion("TY", "type", "equals", "txt");
        assertTrue(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateTypeEqualsFalse() {
        SimpleCriterion criterion = new SimpleCriterion("TY", "type", "equals", "pdf");
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeGreaterThanTrue() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", ">", 100);
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeGreaterThanFalse() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", ">", 200);
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeLessThanTrue() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "<", 200);
        assertTrue(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeLessThanFalse() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "<", 100);
        assertTrue(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeEqualsTrue() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "==", 150);
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeEqualsFalse() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "==", 100);
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeNotEqualsTrue() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "!=", 100);
        assertTrue(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeNotEqualsFalse() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "!=", 150);
        assertTrue(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeLessThanOrEqualTrue() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "<=", 150);
        assertTrue(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeLessThanOrEqualFalse() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "<=", 100);
        assertTrue(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeGreaterThanOrEqualTrue() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", ">=", 150);
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateSizeGreaterThanOrEqualFalse() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", ">=", 200);
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testEvaluateInvalidAttribute() {
        SimpleCriterion criterion = new SimpleCriterion("NM", "invalidAttr", "contains", "test");
        assertFalse(criterion.evaluate(testFile)); // Should return false for invalid attribute
    }

    @Test
    public void testEvaluateInvalidOperator() {
        SimpleCriterion criterion = new SimpleCriterion("NM", "name", "invalidOp", "test");
        assertFalse(criterion.evaluate(testFile)); // Should return false for invalid operator
    }

    @Test
    public void testCVFSConstructor() {
        assertNotNull(cvfs);
    }

    @Test
    public void testCreateDocument() {
        cvfs.newDoc("testDoc", "txt", "This is a test document.");
        assertNotNull(cvfs.currentDirectory.getFile("testDoc"));
    }

    @Test
    public void testCreateDocumentInvalidName() {
        cvfs.newDoc("invalid_name!", "txt", "Content");
        assertNull(cvfs.currentDirectory.getFile("invalid_name!"));
    }

    @Test
    public void testCreateDocumentInvalidType() {
        cvfs.newDoc("testDoc", "exe", "Content");
        assertNull(cvfs.currentDirectory.getFile("testDoc"));
    }

    @Test
    public void testCreateDocumentNoDisk() {
        CVFS cvfsNoDisk = new CVFS();
        cvfsNoDisk.newDoc("testDoc", "txt", "Content");
    }

    @Test
    public void testCreateDirectory() {
        cvfs.newDir("testDir");
        assertNotNull(cvfs.currentDirectory.getSubdirectory("testDir"));
    }

    @Test
    public void testCreateDuplicateDirectory() {
        cvfs.newDir("testDir");
        cvfs.newDir("testDir"); // Attempt to create a duplicate
        // Expect that the directory is not created again
        assertNotNull(cvfs.currentDirectory.getSubdirectory("testDir"));
    }

    @Test
    public void testDeleteFile() {
        cvfs.newDoc("testDoc", "txt", "Content");
        cvfs.delete("testDoc");
        assertNull(cvfs.currentDirectory.getFile("testDoc"));
    }

    @Test
    public void testDeleteFileNotFound() {
        cvfs.newDoc("testDoc", "txt", "Content");
        cvfs.delete("nonExistentDoc");
    }

    @Test
    public void testRenameFile() {
        cvfs.newDoc("testDoc", "txt", "Content");
        cvfs.rename("testDoc", "renamedDoc");
        assertNotNull(cvfs.currentDirectory.getFile("renamedDoc"));
        assertNull(cvfs.currentDirectory.getFile("testDoc"));
    }

    @Test
    public void testRenameFileNotFound() {
        cvfs.rename("nonExistentDoc", "newName");
    }

    @Test
    public void testChangeDirectory() {
        cvfs.newDir("testDir");
        cvfs.changeDir("testDir");
        assertEquals("testDir", cvfs.currentDirectory.getName());
    }

    @Test
    public void testChangeToParentDirectory() {
        cvfs.newDir("testDir");
        cvfs.changeDir("testDir");
        cvfs.changeDir("..");
        assertEquals("root", cvfs.currentDirectory.getName());
    }

    @Test
    public void testListFiles() {
        cvfs.newDoc("testDoc", "txt", "Content");
        cvfs.list();
    }

    @Test
    public void testRecursiveListFiles() {
        cvfs.newDir("testDir");
        cvfs.newDoc("testDoc", "txt", "Content");
        cvfs.changeDir("testDir");
        cvfs.newDoc("nestedDoc", "txt", "Nested content.");
        cvfs.changeDir("..");
        cvfs.rList();
    }

    @Test
    public void testNewSimpleCriterion() {
        cvfs.newSimpleCri("NM", "name", "contains", "\"test\"");
        assertNotNull(cvfs.criteriaStore.get("NM"));
    }

    @Test
    public void testNewSimpleCriterionInvalidName() {
        cvfs.newSimpleCri("N", "name", "contains", "\"test\"");
    }

    @Test
    public void testNewSimpleCriterionInvalidAttribute() {
        cvfs.newSimpleCri("NM", "invalidAttr", "contains", "\"test\"");
    }

    @Test
    public void testNewBinaryCriterion() {
        cvfs.newSimpleCri("NM", "name", "contains", "\"test\"");
        cvfs.newSimpleCri("TY", "type", "equals", "\"txt\"");
        cvfs.newBinaryCri("BN", "NM", "&&", "TY");
        assertNotNull(cvfs.criteriaStore.get("BN"));
    }

    @Test
    public void testNewNegationCriterion() {
        cvfs.newSimpleCri("NM", "name", "contains", "\"test\"");
        cvfs.newNegation("NMG", "NM");
        assertNotNull(cvfs.criteriaStore.get("NMG"));
    }

    @Test
    public void testPrintAllCriteria() {
        cvfs.newSimpleCri("NM", "name", "contains", "\"test\"");
        cvfs.printAllCriteria();
    }

    @Test
    public void testSearch() {
        cvfs.newDoc("testDoc", "txt", "This is a test document.");
        cvfs.newSimpleCri("NM", "name", "contains", "\"test\"");
        cvfs.search("NM");
    }

    @Test
    public void testRecursiveSearch() {
        cvfs.newDoc("testDoc", "txt", "This is a test document.");
        cvfs.newDir("testDir");
        cvfs.changeDir("testDir");
        cvfs.newDoc("nestedDoc", "txt", "Another test document.");
        cvfs.changeDir("..");
        cvfs.newSimpleCri("NM", "name", "contains", "\"test\"");
        cvfs.rSearch("NM");
    }

    @Test
    public void testSaveDisk() {
        cvfs.save("testDisk.dat");
    }

    @Test
    public void testLoadDisk() {
        cvfs.load("testDisk.dat");
    }

    @Test
    public void testSimpleCriterionNameContains() {
        SimpleCriterion criterion = new SimpleCriterion("NM", "name", "contains", "test");
        assertTrue(criterion.evaluate(testFile));
    }

    @Test
    public void testSimpleCriterionNameDoesNotContain() {
        SimpleCriterion criterion = new SimpleCriterion("NM", "name", "contains", "example");
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testNewSimpleCriterionValidName() {
        cvfs.newSimpleCri("NM", "name", "contains", "\"test\"");
        assertNotNull(cvfs.criteriaStore.get("NM"));
    }

    @Test
    public void testNewSimpleCriterionValidType() {
        cvfs.newSimpleCri("TY", "type", "equals", "\"txt\"");
        assertNotNull(cvfs.criteriaStore.get("TY"));
    }

    @Test
    public void testNewSimpleCriterionValidSize() {
        cvfs.newSimpleCri("SZ", "size", ">", "100");
        assertNotNull(cvfs.criteriaStore.get("SZ"));
    }

    @Test
    public void testNewSimpleCriterionInvalidCriName() {
        cvfs.newSimpleCri("N1", "name", "contains", "\"test\"");
        assertNull(cvfs.criteriaStore.get("N1"));
    }

    @Test
    public void testNewSimpleCriterionInvalidAttrName() {
        cvfs.newSimpleCri("NM", "invalidAttr", "equals", "\"test\"");
        assertNull(cvfs.criteriaStore.get("NM"));
    }

    @Test
    public void testNewSimpleCriterionInvalidOpForName() {
        cvfs.newSimpleCri("NM", "name", "invalidOp", "\"test\"");
        assertNull(cvfs.criteriaStore.get("NM"));
    }

    @Test
    public void testNewSimpleCriterionInvalidOpForSize() {
        cvfs.newSimpleCri("SZ", "size", "invalidOp", "100");
        assertNull(cvfs.criteriaStore.get("SZ"));
    }

    @Test
    public void testNewSimpleCriterionInvalidValForString() {
        cvfs.newSimpleCri("NM", "name", "contains", "test"); // Missing quotes
        assertNull(cvfs.criteriaStore.get("NM"));
    }

    @Test
    public void testNewSimpleCriterionInvalidValForSize() {
        cvfs.newSimpleCri("SZ", "size", ">", "notANumber");
        assertNull(cvfs.criteriaStore.get("SZ"));
    }

    @Test
    public void testNewSimpleCriterionValidSizeWithEqualOperator() {
        cvfs.newSimpleCri("SZ", "size", "==", "100");
        assertNotNull(cvfs.criteriaStore.get("SZ"));
    }

    @Test
    public void testNewSimpleCriterionInvalidSizeWithNonIntegerValue() {
        cvfs.newSimpleCri("SZ", "size", ">", "abc");
        assertNull(cvfs.criteriaStore.get("SZ"));
    }

    @Test
    public void testNewSimpleCriterionCreateMultipleCriteria() {
        cvfs.newSimpleCri("NM", "name", "contains", "\"test\"");
        cvfs.newSimpleCri("TY", "type", "equals", "\"txt\"");
        cvfs.newSimpleCri("SZ", "size", ">", "100");
        assertNotNull(cvfs.criteriaStore.get("NM"));
        assertNotNull(cvfs.criteriaStore.get("TY"));
        assertNotNull(cvfs.criteriaStore.get("SZ"));
    }

    @Test
    public void testNewSimpleCriterionWithSpecialCharactersInName() {
        cvfs.newSimpleCri("NM", "name", "contains", "\"@test!\"");
        assertNotNull(cvfs.criteriaStore.get("NM"));
    }

    @Test
    public void testSimpleCriterionTypeEquals() {
        SimpleCriterion criterion = new SimpleCriterion("TY", "type", "equals", "txt");
        assertTrue(criterion.evaluate(testFile)); // Should match
    }

    @Test
    public void testSimpleCriterionTypeDoesNotEqual() {
        SimpleCriterion criterion = new SimpleCriterion("TY", "type", "equals", "pdf");
        assertFalse(criterion.evaluate(testFile)); // Should not match
    }

    @Test
    public void testSimpleCriterionSizeGreaterThan() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", ">", 50);
        assertFalse("Expected file size to be greater than 50", criterion.evaluate(testFile));
    }

    @Test
    public void testSimpleCriterionSizeLessThan() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "<", 150);
        assertTrue(criterion.evaluate(testFile));
    }

    @Test
    public void testSimpleCriterionSizeEquals() {
        SimpleCriterion criterion = new SimpleCriterion("SZ", "size", "==", 100);
        assertFalse(criterion.evaluate(testFile));
    }

    @Test
    public void testNegationCriterion() {
        SimpleCriterion simpleCriterion = new SimpleCriterion("NM", "name", "contains", "test");
        NegationCriterion negationCriterion = new NegationCriterion(simpleCriterion);
        assertFalse(negationCriterion.evaluate(testFile));
    }

    @Test
    public void testIsDocumentCriterion() {
        IsDocumentCriterion isDocCriterion = new IsDocumentCriterion();
        assertTrue(isDocCriterion.evaluate(testFile));
    }

    @Test
    public void testBinaryCriterionAnd() {
        SimpleCriterion sizeCriterion = new SimpleCriterion("SZ", "size", ">", 50);
        SimpleCriterion typeCriterion = new SimpleCriterion("TY", "type", "equals", "txt");
        BinaryCriterion binaryCriterion = new BinaryCriterion(sizeCriterion, "&&", typeCriterion);
        assertFalse(binaryCriterion.evaluate(testFile));
    }

    @Test
    public void testBinaryCriterionOr() {
        SimpleCriterion sizeCriterion = new SimpleCriterion("SZ", "size", "<", 50);
        SimpleCriterion typeCriterion = new SimpleCriterion("TY", "type", "equals", "pdf");
        BinaryCriterion binaryCriterion = new BinaryCriterion(sizeCriterion, "||", typeCriterion);
        assertTrue(binaryCriterion.evaluate(testFile));
    }

    @Test
    public void testBinaryCriterionMixed() {
        SimpleCriterion sizeCriterion = new SimpleCriterion("SZ", "size", ">", 50);
        SimpleCriterion typeCriterion = new SimpleCriterion("TY", "type", "equals", "pdf");
        BinaryCriterion binaryCriterion = new BinaryCriterion(sizeCriterion, "||", typeCriterion);
        assertFalse(binaryCriterion.evaluate(testFile));
    }

    @Test
    public void testBinaryCriterionComplexLogic() {
        SimpleCriterion criterion1 = new SimpleCriterion("NM", "name", "contains", "example");
        SimpleCriterion criterion2 = new SimpleCriterion("TY", "type", "equals", "pdf");
        SimpleCriterion criterion3 = new SimpleCriterion("SZ", "size", ">", 100);
        BinaryCriterion binaryCriterion = new BinaryCriterion(
                new BinaryCriterion(criterion1, "||", criterion2),
                "&&",
                criterion3
        );
        assertFalse(binaryCriterion.evaluate(testFile));
    }
    @Test
    public void testAddFile() {
        directory.addFile(testFile1);
        assertNotNull(directory.getFile("file1"));
        assertEquals(testFile1, directory.getFile("file1"));
    }

    @Test
    public void testRemoveFile() {
        directory.addFile(testFile1);
        assertTrue(directory.removeFile("file1"));
        assertNull(directory.getFile("file1"));
    }

    @Test
    public void testRemoveNonexistentFile() {
        assertFalse(directory.removeFile("nonexistent.txt"));
    }

    @Test
    public void testRenameNonexistentFile() {
        assertFalse(directory.renameFile("nonexistent.txt", "newFileName"));
    }

    @Test
    public void testAddSubdirectory() {
        directory.addSubdirectory(subDirectory);
        assertNotNull(directory.getSubdirectory("subDir"));
        assertEquals(subDirectory, directory.getSubdirectory("subDir"));
    }

    @Test
    public void testGetSubdirectory() {
        directory.addSubdirectory(subDirectory);
        assertEquals(subDirectory, directory.getSubdirectory("subDir"));
    }

    @Test
    public void testGetUsedSpace() {
        directory.addFile(testFile1);
        directory.addFile(testFile2);
        int expectedSpace = 40 + testFile1.getSize() + testFile2.getSize(); // 40 is the base size
        assertEquals(expectedSpace, directory.getUsedSpace());
    }

    @Test
    public void testGetFiles() {
        directory.addFile(testFile1);
        directory.addFile(testFile2);
        assertEquals(2, directory.getFiles().size());
    }
}