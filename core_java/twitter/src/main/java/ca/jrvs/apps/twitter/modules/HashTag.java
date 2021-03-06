package ca.jrvs.apps.twitter.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HashTag {

    @JsonProperty("text")
    String text;

    @JsonProperty("indices")
    List<Integer> indices;

}
