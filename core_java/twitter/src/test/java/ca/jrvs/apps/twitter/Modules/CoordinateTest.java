package ca.jrvs.apps.twitter.Modules;

import ca.jrvs.apps.twitter.utils.JsonParser;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.*;

public class CoordinateTest {



    @Test
    public void testStringToObject() throws IOException {
        String jsonBody = "{\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [\n" +
                "            -122.40061,\n" +
                "            37.7821120\n" +
                "        ]\n" +
                "    }";


        Coordinate coordinate = JsonParser.toObjectFromJson(jsonBody, Coordinate.class);
        assertEquals(coordinate.getType(), "Point");
        assertEquals(coordinate.getLongitude(), -122.40061f, 0);
        assertEquals(coordinate.getLatitude(), 37.7821120f, 0);
    }


    @Test
    public void testObjectToString() throws IOException {
        Coordinate coordinate = new Coordinate(-122.40061f, 37.7821120f);
        coordinate.setType("Point");
        String jsonBody = JsonParser.toJson(coordinate, Boolean.TRUE, Boolean.FALSE);
        Coordinate coordinateCopy = JsonParser.toObjectFromJson(jsonBody, Coordinate.class);
        assertEquals(coordinate.getType(), coordinateCopy.getType());
        assertEquals(coordinate.getLongitude(), coordinateCopy.getLongitude(), 0);
        assertEquals(coordinate.getLatitude(), coordinateCopy.getLatitude(), 0);
        System.out.println(jsonBody);
    }
}