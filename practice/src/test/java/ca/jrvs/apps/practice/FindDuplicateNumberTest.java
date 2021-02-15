package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FindDuplicateNumberTest {

    @Test
    void solution() {
        int[] nums = new int[]{1,3,4,2,2};
        FindDuplicateNumber findDuplicateNumber = new FindDuplicateNumber();
        assertEquals(2, findDuplicateNumber.solution(nums));

        int[] nums2 = new int[]{2,5,9,6,9,3,8,9,7,1};
        assertEquals(9, findDuplicateNumber.solution(nums2));

    }
}