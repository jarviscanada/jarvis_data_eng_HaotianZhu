package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidParenthesesTest {

    @Test
    void check() {
        assertTrue(ValidParentheses.check(""));
        assertTrue(ValidParentheses.check("(())[]{{[]}}"));
        assertFalse(ValidParentheses.check("(())][{{[]}}"));
    }
}