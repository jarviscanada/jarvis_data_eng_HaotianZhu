package ca.jrvs.apps.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueueUsingStacksTest {

    @Test
    void test(){
        QueueUsingStacks<Integer> queueUsingStacks = new QueueUsingStacks<Integer>();
        queueUsingStacks.getQueue().add(1);
        queueUsingStacks.getQueue().add(2);
        queueUsingStacks.getQueue().add(3);

        assertEquals(queueUsingStacks.getQueue().remove(),1);
        queueUsingStacks.getQueue().add(4);
        queueUsingStacks.getQueue().add(5);
        assertEquals(queueUsingStacks.getQueue().remove(),2);
        assertEquals(queueUsingStacks.getQueue().remove(),3);
        assertEquals(queueUsingStacks.getQueue().remove(),4);
        assertEquals(queueUsingStacks.getQueue().remove(),5);
    }

}