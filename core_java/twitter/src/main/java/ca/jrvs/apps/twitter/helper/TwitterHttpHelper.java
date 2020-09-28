package ca.jrvs.apps.twitter.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;
import java.net.URI;

public class TwitterHttpHelper implements HttpHelper {
    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret){
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken,tokenSecret);

        httpClient = HttpClientBuilder.create().build();
    }

    @Override
    public  HttpResponse httpPost(URI uri, String body) {
        try{
            HttpPost postRequest = new HttpPost(uri);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setEntity(new StringEntity(body));
            consumer.sign(postRequest);
            return this.httpClient.execute(postRequest);
        } catch (IOException | OAuthExpectationFailedException |
                OAuthMessageSignerException | OAuthCommunicationException e){
            throw new RuntimeException(e);
        }
    }

    public  HttpResponse httpPost(URI uri) {
        try{
            HttpPost postRequest = new HttpPost(uri);
            postRequest.setHeader("Content-Type", "application/json");
            consumer.sign(postRequest);
            return this.httpClient.execute(postRequest);
        } catch (IOException | OAuthExpectationFailedException |
                OAuthMessageSignerException | OAuthCommunicationException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public HttpResponse httpGet(URI uri) {
        try{
            HttpGet postRequest = new HttpGet(uri);
            consumer.sign(postRequest);
            return this.httpClient.execute(postRequest);
        } catch (IOException | OAuthExpectationFailedException
                | OAuthMessageSignerException | OAuthCommunicationException e) {
            throw new RuntimeException(e);
        }
    }
}
