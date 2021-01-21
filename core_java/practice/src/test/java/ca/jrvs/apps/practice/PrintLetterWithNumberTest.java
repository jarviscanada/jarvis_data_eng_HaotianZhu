package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintLetterWithNumberTest {

    @Test
    void solution() {
        PrintLetterWithNumber printLetterWithNumber = new PrintLetterWithNumber();
        assertEquals("a1b2c3e5e5A27",
                printLetterWithNumber.solution("abceeA"));
    }
}