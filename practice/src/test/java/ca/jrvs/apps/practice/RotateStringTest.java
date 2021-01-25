package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotateStringTest {

    @Test
    void check() {
        assertTrue(RotateString.check("", ""));
        assertFalse(RotateString.check("abcde", "abcedf"));
        assertFalse(RotateString.check("abcde", "abced"));
        assertTrue(RotateString.check("abcde", "cdeab"));
    }
}