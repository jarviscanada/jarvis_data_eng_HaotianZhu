package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.modules.Tweet;
import ca.jrvs.apps.twitter.dao.TweetDao;
import ca.jrvs.apps.twitter.helper.HttpHelper;
import ca.jrvs.apps.twitter.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.utils.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TweetServiceTest {

    private TweetService service;

    @BeforeEach
    public void setUp() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        this.service = new TweetService( new TweetDao(helper));
    }

    @Test
    public void postTweet() throws JsonProcessingException {
        assertThrows(IllegalArgumentException.class, ()->this.service.postTweet(new Tweet("text", 0f,181f)));
        assertThrows(IllegalArgumentException.class, ()->this.service.postTweet(new Tweet("text", 91f,0f)));
    }


    @Test
    public void getTweet() throws JsonProcessingException {
        Long id = 1253339120228659205L;
        Tweet tweet = this.service.getTweet(id);
        System.out.println(JsonParser.toJson(tweet,true, true));
        assertThrows(IllegalArgumentException.class, ()->this.service.getTweet(-1253339120228659205L));
    }

    @Test
    public void deleteTweet() {

        String text = "hi";
        Float lat = 1f;
        Float lon = -1f;
        Tweet postTweet = new Tweet(text, lat, lon);
        Tweet newTweet = this.service.postTweet(postTweet);
        Long id = newTweet.getId();

        Tweet tweet = this.service.deleteTweet(id);

        assertEquals(tweet.getCoordinate().getLatitude(), lat);
        assertEquals(tweet.getCoordinate().getLongitude(), lon);

    }

}