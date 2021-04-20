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
    public static JSONArray DecodeJSON(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/mistaomega/json/questions.json"));
            JSONObject jsonObject = (JSONObject)obj;
            return (JSONArray)jsonObject.get("Questions");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
