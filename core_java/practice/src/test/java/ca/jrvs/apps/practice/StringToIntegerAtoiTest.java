package ca.jrvs.apps.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringToIntegerAtoiTest {

    StringToIntegerAtoi solution = new StringToIntegerAtoi();


    @Test
    void withBuildInParsing() {
        assertEquals(solution.withBuildInParsing("+32"), 32);
        assertEquals(solution.withBuildInParsing("32.3"), 32);
        assertEquals(solution.withBuildInParsing("-32"), -32);
        assertEquals(solution.withBuildInParsing("-32.3"), -32);
        assertEquals(solution.withBuildInParsing("+32dada"), 32);
    }

    @Test
    void withOutBuildInParsing() {
        assertEquals(solution.withOutBuildInParsing("+32"), 32);
        assertEquals(solution.withOutBuildInParsing("32.3"), 32);
        assertEquals(solution.withOutBuildInParsing("-32"), -32);
        assertEquals(solution.withOutBuildInParsing("-32.3"), -32);
        assertEquals(solution.withOutBuildInParsing("+32dada"), 32);
    }
}