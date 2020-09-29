package ca.jrvs.apps.twitter.dao;
import ca.jrvs.apps.twitter.Modules.Tweet;
import ca.jrvs.apps.twitter.helper.HttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gdata.util.common.base.PercentEscaper;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;

public class TweetDao implements CrdDao<Tweet> {
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = API_BASE_URI+"/1.1/statuses/update.json";
    private static final String SHOW_PATH = API_BASE_URI+"/1.1/statuses/show.json";
    private static final String DELETE_PATH = API_BASE_URI+"/1.1/statuses/destroy";;

    private static final String QUERY_SYMBOL = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;


    public TweetDao(HttpHelper httpHelper){
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet tweet) {
        String status = tweet.getText();
        PercentEscaper percentEscaper = new PercentEscaper("", false);
        String uriStr = this.POST_PATH +this.QUERY_SYMBOL+"status"+this.EQUAL+ percentEscaper.escape(status) +
                (tweet.getCoordinate()==null? "" : this.AMPERSAND +
                "lat"+this.EQUAL+tweet.getCoordinate().getLatitude() + this.AMPERSAND+
                "long"+this.EQUAL+tweet.getCoordinate().getLongitude());
        URI uri = URI.create(uriStr);
        HttpResponse response = this.httpHelper.httpPost(uri);
        try {
            if(response.getStatusLine().getStatusCode() == HTTP_OK) {
                ObjectMapper mapper = new ObjectMapper();
                String body = EntityUtils.toString(response.getEntity());
                return mapper.readValue(body,Tweet.class);
            } else{
                throw new RuntimeException(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tweet getById(String id) {
        URI uri = URI.create(this.SHOW_PATH + this.QUERY_SYMBOL + "id" + this.EQUAL + id );
        HttpResponse response = this.httpHelper.httpGet(uri);
        try {
            if(response.getStatusLine().getStatusCode() == HTTP_OK) {
                ObjectMapper mapper = new ObjectMapper();
                String body = EntityUtils.toString(response.getEntity());
                return mapper.readValue(body,Tweet.class);
            } else{
                throw new RuntimeException(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Tweet deleteById(String id) {
        URI uri = URI.create(this.DELETE_PATH+"/" + id + ".json" );
        HttpResponse response = this.httpHelper.httpPost(uri);
        try {
            if(response.getStatusLine().getStatusCode() == HTTP_OK) {
                ObjectMapper mapper = new ObjectMapper();
                String body = EntityUtils.toString(response.getEntity());
                return mapper.readValue(body,Tweet.class);
            } else {
                throw new RuntimeException(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
