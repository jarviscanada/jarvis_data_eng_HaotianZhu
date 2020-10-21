package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidPalindromeTest {

    @Test
    void twoPoints() {
        assertTrue(ValidPalindrome.twoPoints("abccba"));
        assertFalse(ValidPalindrome.twoPoints("abbbaa"));
    }

    @Test
    void recursive() {
        assertTrue(ValidPalindrome.recursive("abccba"));
        assertFalse(ValidPalindrome.recursive("abbbaa"));
    }
}