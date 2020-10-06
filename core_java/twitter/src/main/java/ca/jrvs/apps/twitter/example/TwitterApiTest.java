package ca.jrvs.apps.twitter.example;

import ca.jrvs.apps.twitter.helper.TwitterHttpHelper;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class TwitterApiTest {
    private static String CONSUMER_KEY = System.getenv("consumerKey");
    private static String CONSUMER_SECRET = System.getenv("consumerSecret");
    private static String ACCESS_TOKEN = System.getenv("accessToken");
    private static String TOKEN_SECRET = System.getenv("tokenSecret");

    public static void main(String[] args) throws Exception {
        TwitterHttpHelper helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
        String status = "today is a good day";
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        String uri = "https://api.twitter.com/1.1/statuses/update.json?status=" +
                percentEscaper.escape(status);
        HttpEntity entity = helper.httpPost(URI.create(uri)).getEntity();
        //send the request
        System.out.println(EntityUtils.toString(entity));
    }



}
