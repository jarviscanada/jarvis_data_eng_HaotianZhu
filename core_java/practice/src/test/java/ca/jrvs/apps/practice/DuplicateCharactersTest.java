package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DuplicateCharactersTest {

    @Test
    void solution() {
        String str ="A black cat";
        DuplicateCharacters duplicateCharacters = new DuplicateCharacters();
        Character[] arr = duplicateCharacters.solution(str);
        assertEquals(arr[0], 'a');
        assertEquals(arr[1], 'c');
    }
}