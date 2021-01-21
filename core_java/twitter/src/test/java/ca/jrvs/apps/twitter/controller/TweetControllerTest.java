package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TweetDao;
import ca.jrvs.apps.twitter.helper.HttpHelper;
import ca.jrvs.apps.twitter.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.modules.Tweet;
import ca.jrvs.apps.twitter.service.TweetService;
import ca.jrvs.apps.twitter.utils.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class TweetControllerTest {
    TweetController controller;
    @BeforeEach
    public void setUp() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        HttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        TweetDao dao = new TweetDao(helper);
        TweetService service = new TweetService(dao);
        this.controller = new TweetController(service);
    }

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

        assertThrows(IllegalArgumentException.class, ()->controller.postTweet(args));
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

        assertNotNull(controller.getTweet(args).getText());
        assertThrows(IllegalArgumentException.class, ()->controller.getTweet(args2));
    }

    @Test
    public void deleteTweet() {

        String idStr = "new text";

        String[] args2 = new String[3];
        args2[0] = "get";
        args2[1] = idStr;

        assertThrows(IllegalArgumentException.class, ()->controller.deleteTweet(args2));

    }
}