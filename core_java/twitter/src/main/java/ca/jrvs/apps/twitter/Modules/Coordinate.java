package ca.jrvs.apps.twitter.Modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coordinate {

    @JsonProperty("type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Float longitude;
    private Float latitude;

    public Coordinate(Float longitude, Float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Coordinate() {

    }
    @JsonIgnore
    public Float getLongitude() {
        return longitude;
    }

    @JsonIgnore
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    @JsonIgnore
    public Float getLatitude() {
        return this.latitude;
    }
    @JsonIgnore
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("coordinates")
    public List<Float> getCoordinates(){
        List<Float> arr = new ArrayList<Float>(2);
        arr.add(this.longitude);
        arr.add(this.latitude);
        return arr;
    }

    @JsonProperty("coordinates")
    public void setCoordinates(List<Float> arr){
        this.longitude = arr.get(0);
        this.latitude = arr.get(1);
    }

}
