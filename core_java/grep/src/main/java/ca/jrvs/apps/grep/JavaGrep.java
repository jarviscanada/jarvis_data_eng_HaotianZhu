package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {
    /**
     * Top level search workflow
     * throw IOException
     * */
    void process() throws IOException;


    /**
     * Traversal a given directory and return all files
     * @param rootDir input directory
     * @return file under the rootDir
     * */
    List<File> listFiles(String rootDir);


    /**
     * read a file and return all lines
     * @param inputFile input file
     * @return lines
     * @throws IllegalArgumentException if inputFile is not a File
     * */
    List<String> readLines(File inputFile);

    /**
     * check if the line contains pattern
     * @param line A string
     * */
    Boolean containsPattern(String line);

    /**
     * write liens to file
     * @param lines array of line
     * */
    void writeToFile(List<String> lines) throws IOException;
}
