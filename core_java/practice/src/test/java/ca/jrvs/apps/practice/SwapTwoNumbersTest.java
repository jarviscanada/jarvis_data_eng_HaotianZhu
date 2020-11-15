package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwapTwoNumbersTest {

    @Test
    void bitOperation() {
        int[] arr = new int[]{1,2};
        SwapTwoNumbers swapTwoNumbers = new SwapTwoNumbers();
        swapTwoNumbers.bitOperation(arr);
        assertEquals(arr[0], 2);
        assertEquals(arr[1], 1);
    }

    @Test
    void arithmetic() {
        int[] arr = new int[]{1,2};
        SwapTwoNumbers swapTwoNumbers = new SwapTwoNumbers();
        swapTwoNumbers.arithmetic(arr);
        assertEquals(arr[0], 2);
        assertEquals(arr[1], 1);
    }
}