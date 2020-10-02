package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.modules.Tweet;
import ca.jrvs.apps.twitter.dao.TweetDao;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.function.Function;


@org.springframework.stereotype.Service
public class TweetService implements Service{

    private  TweetDao dao;
    private Function<Long, Boolean> validateId = (id) -> id > 0;
    private Function<Float, Boolean> validateLat = (lat) -> lat >= -90 && lat <= 90;
    private Function<Float, Boolean> validateLon = (lon) -> lon >= -180 && lon <=180;

    @Autowired
    public TweetService(TweetDao dao){
        this.dao =dao;
    }


    public Tweet postTweet(Tweet tweet){
        if( tweet.getId() != null) {
            throw new IllegalArgumentException("post tweet's id should be null");
        }
        if( !validateLat.apply(tweet.getCoordinate().getLatitude())) {
            throw new IllegalArgumentException("lat is out of range");
        }

        if( !validateLon.apply(tweet.getCoordinate().getLongitude())) {
            throw new IllegalArgumentException("lon is out of range");
        }
        return dao.create(tweet);
    }


    public Tweet getTweet(Long id){
        if( !validateId.apply(id)) {
            throw new IllegalArgumentException("tweet id is not correct");
        }
        return dao.getById(id.toString());
    }

    public Tweet deleteTweet(Long id){
        if( !validateId.apply(id)) {
            throw new IllegalArgumentException("tweet id is not correct");
        }
        return dao.deleteById(id.toString());
    }


}
