package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {
    final static Logger logger = LoggerFactory.getLogger(JavaGrepLambdaImp.class);

    public JavaGrepLambdaImp(String regExp, String rootPath, String outputPath) {
        super(regExp, rootPath, outputPath);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File current = new File(rootDir);

        if(!current.exists()){
            throw new IllegalArgumentException(current.getAbsolutePath()+" does not exist");
        }

        if(!current.isDirectory()){
            throw new IllegalArgumentException(current.getAbsolutePath()+" is not a path to a isDirectory");
        }

        try {
            Stream<Path> pathStream =  Files.walk(current.toPath());
            Stream<File> fileStream = pathStream.filter(Files::isRegularFile).map(Path::toFile);
            return fileStream.collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("IOException", e);
            throw new IllegalArgumentException("IOException");
        }
    }

    @Override
    public List<String> readLines(File inputFile) {
        try {
            Stream<String> stream = Files.lines(inputFile.toPath());
            return stream.collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
            throw new IllegalArgumentException("FileNotFoundException");
        } catch (IOException e) {
            logger.error("IOException", e);
            throw new IllegalArgumentException("IOException");
        }
    }
}
