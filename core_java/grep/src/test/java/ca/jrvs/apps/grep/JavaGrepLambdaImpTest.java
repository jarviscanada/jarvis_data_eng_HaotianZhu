package ca.jrvs.apps.grep;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class JavaGrepLambdaImpTest {
    final static String PATH = "src/test/java/ca/jrvs/apps/grep";
    JavaGrepLambdaImp javaGrepImp = new JavaGrepLambdaImp("haotian", PATH+"/testFolder",PATH+"/testFolderResult/testOutput.txt");

    @Test
    void listFiles() {
        File f = new File(PATH);
        assertTrue(f.exists());
        assertTrue(f.isDirectory());

        List<File> files = javaGrepImp.listFiles(PATH+"/testFolder");
        List<String> subFilesName = files.stream().map(File::getName).collect(Collectors.toCollection(ArrayList::new));
        assertTrue(subFilesName.contains("test_a.txt"));
        assertTrue(subFilesName.contains("test_b.txt"));
    }

    @Test
    void readLines() {
        File f = new File(PATH+"/testFolder/test_a.txt");
        List<String> lines = javaGrepImp.readLines(f);
        assertTrue(lines.contains("this1"));
        assertTrue(lines.contains("this2"));
        assertTrue(lines.contains("this3"));
        assertTrue(lines.contains("this4"));
    }
}