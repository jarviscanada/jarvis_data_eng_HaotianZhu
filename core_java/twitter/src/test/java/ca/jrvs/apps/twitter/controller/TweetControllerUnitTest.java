package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.modules.Tweet;
import ca.jrvs.apps.twitter.service.TweetService;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class TweetControllerUnitTest {
    @Mock
    TweetService service;

    @InjectMocks
    TweetController controller;


    @Test
    public void postTweet() throws JsonProcessingException {
        String text = "new text";
        String lat = "this is not Long format";
        String lon = "this is not Long format";
        String[] args = new String[4];
        args[0] = "get";
        args[1] = text;
        args[2] = lat;
        args[3] = lon;

        String[] args2 = new String[4];
        args2[0] = "get";
        args2[1] = text;
        args2[2] = "1";
        args2[3] = "1";



        when(service.postTweet(notNull())).thenReturn(new Tweet("good", 1f, 1f));
        assertEquals(controller.postTweet(args2).getText(), "good");
        try{
            controller.postTweet(args);
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }


    @Test
    public void getTweet() throws JsonProcessingException {
        String id = "1253339120228659205";
        String idStr = "new text";
        String[] args = new String[2];
        args[0] = "get";
        args[1] = id;
        String[] args2 = new String[3];
        args2[0] = "get";
        args2[1] = idStr;
        when(service.getTweet(notNull())).thenReturn(new Tweet("good", 1f, 1f));

        assertEquals(controller.getTweet(args).getText(), "good");
        try{
            controller.getTweet(args2);
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }
    }

    @Test
    public void deleteTweet() {

        String id = "1253339120228659205";
        String idStr = "old text";
        String[] args = new String[2];
        args[0] = "get";
        args[1] = id;
        String[] args2 = new String[3];
        args2[0] = "get";
        args2[1] = idStr;

        when(service.deleteTweet(notNull())).thenReturn(new Tweet("good", 1f, 1f));

        assertEquals(controller.deleteTweet(args).getText(), "good");

        try{
            controller.deleteTweet(args2);
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

    }
}