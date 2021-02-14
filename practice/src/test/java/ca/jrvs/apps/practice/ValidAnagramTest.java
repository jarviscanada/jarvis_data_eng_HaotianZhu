package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidAnagramTest {

    @Test
    void checkWithSort() {
        assertTrue(ValidAnagram.checkWithSort("restful" , "fluster"));
        assertFalse(ValidAnagram.checkWithSort("restful" , "fluste"));
    }

    @Test
    void check() {
        assertTrue(ValidAnagram.check("restful" , "fluster"));
        assertFalse(ValidAnagram.check("restful" , "fluste"));
    }
}