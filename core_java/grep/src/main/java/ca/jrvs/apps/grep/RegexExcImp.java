package ca.jrvs.apps.grep;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc {
    private Pattern jpegPattern = Pattern.compile(".*\\.JPE?G", Pattern.CASE_INSENSITIVE);
    private Pattern ipPattern = Pattern.compile("^\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}$", Pattern.CASE_INSENSITIVE);
    //https://stackoverflow.com/questions/3012788/how-to-check-if-a-line-is-blank-using-regex#:~:text=The%20most%20portable%20regex%20would,match%20a%20non%2Dwhitespace%20string.
    private Pattern isEmptyLinePattern = Pattern.compile("/((\\r\\n|\\n|\\r)$)|(^(\\r\\n|\\n|\\r))|^\\s*$");

    public Boolean matchJpeg(String fileName) {
        Matcher matcher = jpegPattern.matcher(fileName);
        return matcher.find();
    }

    public Boolean matchIp(String ip) {
        Matcher matcher = ipPattern.matcher(ip);
        return matcher.find();
    }

    public Boolean isEmptyLine(String line) {
        Matcher matcher = isEmptyLinePattern.matcher(line);
        return matcher.find();
    }
}
