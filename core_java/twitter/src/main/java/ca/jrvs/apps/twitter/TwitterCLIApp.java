package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TweetController;
import ca.jrvs.apps.twitter.dao.TweetDao;
import ca.jrvs.apps.twitter.helper.HttpHelper;
import ca.jrvs.apps.twitter.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.modules.Tweet;
import ca.jrvs.apps.twitter.service.TweetService;

public class TwitterCLIApp {

    public final String USAGE = "TwitterCLIApp post|get|delete [options]";

    private TweetController controller;

    public TwitterCLIApp(TweetController controller){
        this.controller = controller;
    }

    public void run (String[] args){
        String type = args[0].toLowerCase();
        if(type == "post"){
            controller.postTweet(args);
        }else if(type == "get"){
            controller.getTweet(args);
        }else if(type == "delete"){
            controller.deleteTweet(args);
        }else{
            throw new IllegalArgumentException(USAGE);
        }
    }

    public static void main(String[] args) {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret,accessToken , tokenSecret);
        TweetDao dao = new TweetDao(httpHelper);
        TweetService service = new TweetService(dao);
        TweetController controller = new TweetController(service);
        TwitterCLIApp app = new TwitterCLIApp(controller);

    }
}
