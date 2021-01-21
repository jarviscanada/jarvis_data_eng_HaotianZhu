package ca.jrvs.apps.twitter.modules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserMention {
    @JsonProperty("name")
    String name;
    @JsonProperty("indices")
    List<Integer> indices;

    @JsonProperty("screen_name")
    String screenName;

    @JsonProperty("id")
    Integer id;

    @JsonProperty("id_str")
    String idStr;

}
