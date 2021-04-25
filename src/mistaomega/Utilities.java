package mistaomega;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Common Utilities
 */
public class Utilities {
    /**
     * Decode JSON file into JSONArray
     * @return Array of questions
     */
    public static JSONArray DecodeJSON(){
        JSONParser parser = new JSONParser(); // setup json parser
        try {
            Object obj = parser.parse(new FileReader("src/mistaomega/json/questions.json")); //parse into object and convert to json object
            JSONObject jsonObject = (JSONObject)obj;
            return (JSONArray)jsonObject.get("Questions");  //form array from Questions
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new JSONArray(); //return
    }
}
