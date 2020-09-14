package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
    final static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IllegalAccessException {
        if(args.length!=3){
            throw new IllegalAccessException("Usage: JavaGrep [regex] [rootPath] [outFilePath]");
        }

        BasicConfigurator.configure();

        try{
            JavaGrepImp javaGrep = new JavaGrepImp(args[0], args[1], args[2]);
            javaGrep.process();
        }catch (Exception ex){
            logger.error(ex.getMessage(),ex);
        }

    }
}
