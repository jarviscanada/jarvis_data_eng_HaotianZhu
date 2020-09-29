package ca.jrvs.apps.twitter.Modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tweet {

    @JsonProperty("created_at")
    String createdAt;

    @JsonProperty("id")
    Long id;

    @JsonProperty("id_str")
    String idStr;

    @JsonProperty("text")
    String text;

    @JsonProperty("entities")
    Entity entities;

    @JsonProperty("coordinates")
    Coordinate coordinate;

    @JsonProperty("retweet_count")
    Integer reTweetCount;

    @JsonProperty("favorite_count")
    Integer favoriteCount;

    @JsonProperty("retweeted")
    Boolean retweeted;

    @JsonProperty("favorited")
    Boolean favorited;


    public Tweet(){}

    public Tweet(String text, Float lat, Float lon){
        this.coordinate = new Coordinate(lon, lat);
        this.text = text;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Entity getEntities() {
        return this.entities;
    }

    public void setEntities(Entity entities) {
        this.entities = entities;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getReTweetCount() {
        return this.reTweetCount;
    }

    public void setReTweetCount(Integer reTweetCount) {
        this.reTweetCount = reTweetCount;
    }

    public Integer getFavoriteCount() {
        return this.favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Boolean getRetweeted() {
        return this.retweeted;
    }

    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }
}
