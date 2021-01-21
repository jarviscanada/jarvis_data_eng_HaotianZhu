package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveElementTest {

    @Test
    void solution() {
        RemoveElement removeElement = new RemoveElement();
        int[] arr = new int[]{3,2,2,3};
        int len = removeElement.solution(arr,3);
        assertEquals(2, len);
        assertEquals(arr[0], 2);
        assertEquals(arr[1], 2);

        arr = new int[]{1};
        len = removeElement.solution(arr,1);
        assertEquals(len, 0);
    }
}