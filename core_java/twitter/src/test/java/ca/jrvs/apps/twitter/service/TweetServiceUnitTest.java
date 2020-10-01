package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.modules.Tweet;
import ca.jrvs.apps.twitter.dao.TweetDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TweetServiceUnitTest {

    @Mock
    TweetDao dao;

    @InjectMocks
    TweetService service;


    @Test
    public void postTweet() throws IOException {
        when(dao.create(notNull())).thenReturn(new Tweet("output", 1f, 1f));
        Tweet tweet = service.postTweet(new Tweet("TEST", 50f, 0f));
        assertEquals(tweet.getText(), "output");


    }


    @Test
    public void getTweet() throws IOException {
        when(dao.getById(notNull())).thenReturn(new Tweet("output", 1f, 1f));
        Tweet tweet = service.getTweet(1L);
        assertEquals(tweet.getText(), "output");
        try{
            service.getTweet(-1L);
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

    }

    @Test
    public void deleteTweet() throws IOException {
        when(dao.deleteById(notNull())).thenReturn(new Tweet("output", 1f, 1f));
        Tweet tweet = service.deleteTweet(1L);
        assertEquals(tweet.getText(), "output");

        try{
            service.getTweet(-1L);
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        }

    }
}