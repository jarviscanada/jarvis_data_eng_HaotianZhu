package ca.jrvs.apps.grep;

import static org.junit.jupiter.api.Assertions.*;

class RegexExcImpTest {

    RegexExcImp regexExcObj = new RegexExcImp();

    @org.junit.jupiter.api.Test
    void matchJpeg() {
        assertTrue(regexExcObj.matchJpeg("a.jpeg"));
        assertTrue(regexExcObj.matchJpeg("a.jpg"));
        assertFalse(regexExcObj.matchJpeg("a.txt"));
        assertFalse(regexExcObj.matchJpeg("a.png"));
    }

    @org.junit.jupiter.api.Test
    void matchIp() {
        assertTrue(regexExcObj.matchIp("0.1.1.1"));
        assertTrue(regexExcObj.matchIp("850.12.01.1"));
        assertFalse(regexExcObj.matchIp("-1.1.1.1"));
        assertFalse(regexExcObj.matchIp("1.1.1.1.1"));
        assertFalse(regexExcObj.matchIp("11111.1.1.1.1"));
    }

    @org.junit.jupiter.api.Test
    void isEmptyLine() {
        assertTrue(regexExcObj.isEmptyLine("    "));
        assertTrue(regexExcObj.isEmptyLine("\n"));
        assertTrue(regexExcObj.isEmptyLine("\t\n"));
        assertFalse(regexExcObj.isEmptyLine("\t\n  12 \n"));
    }
}