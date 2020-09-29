package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.Modules.Tweet;
import ca.jrvs.apps.twitter.helper.HttpHelper;
import ca.jrvs.apps.twitter.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.utils.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TweetDaoTest {

    private TweetDao dao;

    @Before
    public void setUp() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        this.dao = new TweetDao(helper);
    }

    // @Test
    // public void create() throws Exception{
    //     String text = "? " + System.currentTimeMillis();
    //     Float lat = 1f;
    //     Float lon = -1f;
    //     Tweet postTweet = new Tweet(text, lat, lon);
    //     Tweet tweet = this.dao.create(postTweet);
    //
    //     assertEquals(tweet.getText(), postTweet.getText());
    //     assertEquals(tweet.getCoordinate().getCoordinates().size(), postTweet.getCoordinate().getCoordinates().size());
    // }

    @Test
    public void getById() throws JsonProcessingException {

        Long id = 1253339120228659205L;
        Tweet tweet = this.dao.getById(id.toString());
        System.out.println(JsonParser.toJson(tweet,true, true));
    }

    @Test
    public void deleteById() {


        String text = "hi";
        Float lat = 1f;
        Float lon = -1f;
        Tweet postTweet = new Tweet(text, lat, lon);
        Tweet newTweet = this.dao.create(postTweet);
        Long id = newTweet.getId();

        Tweet tweet = this.dao.deleteById(id.toString());

        assertEquals(tweet.getCoordinate().getLatitude(), lat);
        assertEquals(tweet.getCoordinate().getLongitude(), lon);
    }
}