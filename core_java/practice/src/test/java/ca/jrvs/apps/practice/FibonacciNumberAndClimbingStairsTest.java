package ca.jrvs.apps.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciNumberAndClimbingStairsTest {

    FibonacciNumberAndClimbingStairs solver;
    Integer n;

    @BeforeEach
    void setUp(){
        solver = new FibonacciNumberAndClimbingStairs();
        n = 30;
    }

    @Test
    void fibonacciNumber() {
        assertEquals(solver.fibonacciNumber(n), 832040);
    }

    @Test
    void climbingStairs() {
        assertEquals(solver.climbingStairs(n), 1346269);
    }
}