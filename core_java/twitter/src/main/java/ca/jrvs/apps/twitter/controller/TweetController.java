package ca.jrvs.apps.twitter.controller;
import ca.jrvs.apps.twitter.modules.Tweet;
import ca.jrvs.apps.twitter.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TweetController implements Controller{
    private TweetService service;

    @Autowired
    public TweetController(TweetService service){
        this.service = service;
    }

    public Tweet postTweet(String[] args){
        if(args.length != 4){
            throw new IllegalArgumentException("Usage: TwitterCLIApp post [text] [lat] [lon]");
        }

        String text;
        Float lat;
        Float lon;

        try {
            text = args[1];
            lat = Float.parseFloat(args[2]);
            lon = Float.parseFloat(args[3]);
        } catch (Exception e){
            throw new IllegalArgumentException("Usage: TwitterCLIApp post [text] [lat] [lon]");
        }
        Tweet postTweet = new Tweet(text, lat, lon);
        return service.postTweet(postTweet);
    }

    public Tweet getTweet(String[] args){
        if(args.length != 2){
            throw new IllegalArgumentException("Usage: TwitterCLIApp get [id]");
        }

        Long id;

        try {
            id = Long.parseLong(args[1]);
        } catch (Exception e){
            throw new IllegalArgumentException("Usage: TwitterCLIApp get [id]");
        }

        return service.getTweet(id);
    }


    public Tweet deleteTweet(String[] args){
        if(args.length != 2){
            throw new IllegalArgumentException("Usage: TwitterCLIApp delete [id]");
        }

        Long id;

        try {
            id = Long.parseLong(args[1]);
        } catch (Exception e){
            throw new IllegalArgumentException("Usage: TwitterCLIApp delete [id]");
        }

        return service.deleteTweet(id);
    }
}
