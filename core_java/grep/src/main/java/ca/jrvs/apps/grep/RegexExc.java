package ca.jrvs.apps.grep;

public interface RegexExc {

    /**
     * return true if fileName's extension is jpg or jpeg
     * @param fileName
     * @return Boolean
     * */
    public Boolean matchJpeg(String fileName);

    /**
     * return true if ip is valid
     * IP address range is from 0.0.0.0 to 999.999.999.999
     * @param ip
     * @return Boolean
     * */
    public Boolean matchIp(String ip);


    /**
     * return true if line is empty including white space and tabs, etc
     * @param line
     * @return
     * */
    public Boolean isEmptyLine(String line);
}
