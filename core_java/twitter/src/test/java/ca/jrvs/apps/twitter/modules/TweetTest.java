package ca.jrvs.apps.twitter.modules;
import ca.jrvs.apps.twitter.utils.JsonParser;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TweetTest {
    final String jsonBody = "{\n" +
            "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n" +
            "   \"id\":1097607853932564480,\n" +
            "   \"id_str\":\"1097607853932564480\",\n" +
            "   \"text\":\"test with loc223\",\n" +
            "   \"entities\":{\n" +
            "      \"hashtags\":[    {\n" +
            "      \"indices\": [\n" +
            "        33,\n" +
            "        38\n" +
            "      ],\n" +
            "      \"text\": \"nodejs\"\n" +
            "    },     {\n" +
            "      \"indices\": [\n" +
            "        32,\n" +
            "        38\n" +
            "      ],\n" +
            "      \"text\": \"nodejs2\"\n" +
            "    }],      \n" +
            "      \"user_mentions\":[]  \n" +
            "   },\n" +
            "   \"coordinates\":null,    \n" +
            "   \"retweet_count\":0,\n" +
            "   \"favorite_count\":0,\n" +
            "   \"favorited\":false,\n" +
            "   \"retweeted\":false\n" +
            "}";


    @Test
    public void testStringToObject() throws IOException {
        Tweet tweet = JsonParser.toObjectFromJson(jsonBody, Tweet.class);
        int index33 = tweet.getEntities().getHashTags().get(0).indices.get(0);
        int index32 = tweet.getEntities().getHashTags().get(1).indices.get(0);
        assertEquals(index33, 33);
        assertEquals(index32, 32);
    }

    @Test
    public void testObjectToString() throws IOException {
        Tweet tweet = JsonParser.toObjectFromJson(jsonBody, Tweet.class);
        String jsonBody = JsonParser.toJson(tweet, true, true);
        Tweet tweet2 = JsonParser.toObjectFromJson(jsonBody, Tweet.class);
        assertEquals(tweet.getEntities().getHashTags().get(0).indices.get(0),
                tweet2.getEntities().getHashTags().get(0).indices.get(0));
        assertEquals(tweet.getEntities().getHashTags().get(1).indices.get(0),
                tweet2.getEntities().getHashTags().get(1).indices.get(0));
    }
}