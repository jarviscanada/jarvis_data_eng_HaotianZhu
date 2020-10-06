package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.modules.Tweet;
import ca.jrvs.apps.twitter.helper.HttpHelper;
import ca.jrvs.apps.twitter.utils.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TweetDaoUnitTest {

    String response = "{\n" +
            "    \"created_at\": \"Tue Sep 29 19:26:27 +0000 2020\",\n" +
            "    \"id\": 1311024561807220736,\n" +
            "    \"id_str\": \"1311024561807220736\",\n" +
            "    \"text\": \"hi\",\n" +
            "    \"truncated\": false,\n" +
            "    \"entities\": {\n" +
            "        \"hashtags\": [],\n" +
            "        \"symbols\": [],\n" +
            "        \"user_mentions\": [],\n" +
            "        \"urls\": []\n" +
            "    },\n" +
            "    \"source\": \"\",\n" +
            "    \"in_reply_to_status_id\": null,\n" +
            "    \"in_reply_to_status_id_str\": null,\n" +
            "    \"in_reply_to_user_id\": null,\n" +
            "    \"in_reply_to_user_id_str\": null,\n" +
            "    \"in_reply_to_screen_name\": null,\n" +
            "    \"user\": {\n" +
            "        \"id\": 4748950818,\n" +
            "        \"id_str\": \"4748950818\",\n" +
            "        \"name\": \"HAOTIAN\",\n" +
            "        \"screen_name\": \"htz777\",\n" +
            "        \"location\": \"\",\n" +
            "        \"description\": \"\",\n" +
            "        \"url\": null,\n" +
            "        \"entities\": {\n" +
            "            \"description\": {\n" +
            "                \"urls\": []\n" +
            "            }\n" +
            "        },\n" +
            "        \"protected\": false,\n" +
            "        \"followers_count\": 3,\n" +
            "        \"friends_count\": 11,\n" +
            "        \"listed_count\": 0,\n" +
            "        \"created_at\": \"Tue Jan 12 16:40:50 +0000 2016\",\n" +
            "        \"favourites_count\": 47,\n" +
            "        \"utc_offset\": null,\n" +
            "        \"time_zone\": null,\n" +
            "        \"geo_enabled\": true,\n" +
            "        \"verified\": false,\n" +
            "        \"statuses_count\": 11,\n" +
            "        \"lang\": null,\n" +
            "        \"contributors_enabled\": false,\n" +
            "        \"is_translator\": false,\n" +
            "        \"is_translation_enabled\": false,\n" +
            "        \"profile_background_color\": \"F5F8FA\",\n" +
            "        \"profile_background_image_url\": null,\n" +
            "        \"profile_background_image_url_https\": null,\n" +
            "        \"profile_background_tile\": false,\n" +
            "        \"profile_image_url\": \"http://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png\",\n" +
            "        \"profile_image_url_https\": \"https://abs.twimg.com/sticky/default_profile_images/default_profile_normal.png\",\n" +
            "        \"profile_link_color\": \"1DA1F2\",\n" +
            "        \"profile_sidebar_border_color\": \"C0DEED\",\n" +
            "        \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
            "        \"profile_text_color\": \"333333\",\n" +
            "        \"profile_use_background_image\": true,\n" +
            "        \"has_extended_profile\": false,\n" +
            "        \"default_profile\": true,\n" +
            "        \"default_profile_image\": true,\n" +
            "        \"following\": false,\n" +
            "        \"follow_request_sent\": false,\n" +
            "        \"notifications\": false,\n" +
            "        \"translator_type\": \"none\"\n" +
            "    },\n" +
            "    \"geo\": {\n" +
            "        \"type\": \"Point\",\n" +
            "        \"coordinates\": [\n" +
            "            1,\n" +
            "            -1\n" +
            "        ]\n" +
            "    },\n" +
            "    \"coordinates\": {\n" +
            "        \"type\": \"Point\",\n" +
            "        \"coordinates\": [\n" +
            "            -1,\n" +
            "            1\n" +
            "        ]\n" +
            "    },\n" +
            "    \"place\": null,\n" +
            "    \"contributors\": null,\n" +
            "    \"is_quote_status\": false,\n" +
            "    \"retweet_count\": 0,\n" +
            "    \"favorite_count\": 0,\n" +
            "    \"favorited\": false,\n" +
            "    \"retweeted\": false,\n" +
            "    \"lang\": \"und\"\n" +
            "}";
    @Mock
    HttpHelper mockerHelper;

    @InjectMocks
    TweetDao dao;


    @Test
    public void create() throws IOException {
        when(mockerHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            dao.create(new Tweet("hi", 1f, -1f));
            fail();
        } catch (RuntimeException e){
            assertTrue(true);
        }

        TweetDao spyDao = Mockito.spy(dao);
        Tweet expectTweet = JsonParser.toObjectFromJson(response,Tweet.class);

        doReturn(expectTweet).when(spyDao).create(any());
        Tweet tweet = spyDao.create(any());
        assertNotNull(tweet);
        assertNotNull(tweet.getText());

    }

    @Test
    public void getById() throws IOException {
        when(mockerHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            dao.getById("1311024561807220736");
            fail();
        } catch (RuntimeException e){
            assertTrue(true);
        }

        TweetDao spyDao = Mockito.spy(dao);
        Tweet expectTweet = JsonParser.toObjectFromJson(response,Tweet.class);

        doReturn(expectTweet).when(spyDao).getById(any());
        Tweet tweet = spyDao.getById(any());
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }

    @Test
    public void deleteById() throws IOException {

        when(mockerHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            dao.deleteById("1311024561807220736");
            fail();
        } catch (RuntimeException e){
            assertTrue(true);
        }

        TweetDao spyDao = Mockito.spy(dao);
        Tweet expectTweet = JsonParser.toObjectFromJson(response,Tweet.class);

        doReturn(expectTweet).when(spyDao).deleteById(any());
        Tweet tweet = spyDao.deleteById(any());
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }
}