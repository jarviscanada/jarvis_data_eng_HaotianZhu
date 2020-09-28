package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.helper.TwitterHttpHelper;
import com.google.gdata.util.common.base.PercentEscaper;
import junit.framework.TestCase;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.IOException;
import java.net.URI;



@RunWith(SpringJUnit4ClassRunner.class)
public class TwitterHttpHelperTest extends TestCase {
    TwitterHttpHelper helper;


    @Before
    public void testSetUp(){
        System.out.println("what");
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");
        this.helper = new TwitterHttpHelper(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, TOKEN_SECRET);
    }

    @Test
    public void testHttpPost() throws IOException {
        String status = "today is a good day";
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        String uri = "https://api.twitter.com/1.1/statuses/update.json?status=" +
                percentEscaper.escape(status);
        HttpEntity entity = this.helper.httpPost(URI.create(uri)).getEntity();
        //send the request
        System.out.println(EntityUtils.toString(entity));
    }

    @Test
    public void testHttpGet() {

    }
}