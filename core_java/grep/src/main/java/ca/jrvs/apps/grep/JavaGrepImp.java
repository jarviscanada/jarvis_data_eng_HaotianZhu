package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep{
    private File outputFile;
    private String rootPath;
    private String regExp;
    private Pattern pattern;
    final static Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);


    public void setOutputFile(String outputPath) {
        this.outputFile = new File(outputPath);
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public void setRegExp(String regExp) {
        this.regExp = regExp;
        this.pattern = Pattern.compile(this.regExp);
    }

    public JavaGrepImp(String regExp, String rootPath, String outputPath) {
        this.outputFile = new File(outputPath);
        this.rootPath = rootPath;
        this.regExp = regExp;
        this.pattern = Pattern.compile(regExp);
    }

    public void process() throws IOException {
        List<String> output = new ArrayList<>();
        List<File> files = listFiles(this.rootPath);
        for(File file: files){
            List<String> lines = this.readLines(file);
            for(String line: lines){
                if(this.containsPattern(line)){
                    output.add(line);
                }
            }
        }
        this.writeToFile(output);
    }



    public List<File> listFiles(String rootDir) throws IllegalArgumentException{
        File current = new File(rootDir);
        if(!current.exists()){
            throw new IllegalArgumentException(current.getAbsolutePath()+" does not exist");
        }
        if(!current.isDirectory()){
            throw new IllegalArgumentException(current.getAbsolutePath()+" is not a path to a isDirectory");
        }

        List<File> outputFiles = new ArrayList<>();
        File[] files = current.listFiles();

        if(files == null){
            return outputFiles;
        }

        for(File file: files){
            if(file.isFile()){
                outputFiles.add(file);
            }else{
                outputFiles.addAll(this.listFiles(file.getPath()));
            }
        }
        return outputFiles;
    }

    public List<String> readLines(File inputFile) throws IllegalArgumentException {

        List<String> lines = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String lineString = reader.readLine();
            while(lineString != null){
                lines.add(lineString);
                lineString = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
            throw new IllegalArgumentException("FileNotFoundException");
        } catch (IOException e) {
            logger.error("IOException", e);
        }

        return lines;
    }

    public Boolean containsPattern(String line) {
        Matcher matcher = this.pattern.matcher(line);
        return matcher.find();
    }

    public void writeToFile(List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputFile));
        for(String line:lines) {
            writer.write(line);
            writer.write("\n");
        }
        writer.flush();
    }
}
