package zhang.nz; /**
 * Created by Edward Zhang on 31/12/2016.
 */
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.text.*;
import java.util.*;


public class GeoNet {
    public static JSONObject fetchedJSON;
    public static String[] showQuakes(int mmi) throws Exception {
        String content = URLConnectionReader.getText("https://api.geonet.org.nz/quake?MMI=" + mmi);
        // parse the JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(content);
        fetchedJSON = (JSONObject) obj;
        JSONArray quakes = (JSONArray) fetchedJSON.get("features");
        String[] output =  new String[quakes.size()];
        int j = 0;
        for (int i = quakes.size()-1; i >= 0; i--) {
            JSONObject quakedata = (JSONObject) quakes.get(i);
            JSONObject properties = (JSONObject) quakedata.get("properties");
            Date quaketime = GeoNet.parseTime((String) properties.get("time"));
            Double depth = null;
            if (properties.get("depth") instanceof Double) {
                depth = (Double) properties.get("depth");
            } else if (properties.get("depth") instanceof Long) {
                depth = ((Long) properties.get("depth")).doubleValue();
            }
            Double magnitude = null;
            if (properties.get("magnitude") instanceof Double) {
                magnitude = (Double) properties.get("magnitude");
            } else if (properties.get("magnitude") instanceof Long) {
                magnitude = ((Long) properties.get("magnitude")).doubleValue();
            }
            String line = String.format("%02d | %tr %<tb %<td, %<ty | %.1f M        | %04.1f km | %s", i, quaketime, magnitude, depth , properties.get("locality"));
            output[j] = line;
            j++;
        }
        return output;
    }
    public static String quakeDetail(int id) throws Exception {
        //System.out.println("Showing data for quake ID " + id);
        JSONArray quakes = (JSONArray) fetchedJSON.get("features");
        JSONObject quakedata = (JSONObject) quakes.get(id);
        JSONObject properties = (JSONObject) quakedata.get("properties");
        JSONObject geometry = (JSONObject) quakedata.get("geometry");
        Date quaketime = GeoNet.parseTime((String) properties.get("time"));
        Double depth = null;
        if (properties.get("depth") instanceof Double) {
            depth = (Double) properties.get("depth");
        } else if (properties.get("depth") instanceof Long) {
            depth = ((Long) properties.get("depth")).doubleValue();
        }
        Double magnitude = null;
        if (properties.get("magnitude") instanceof Double) {
            magnitude = (Double) properties.get("magnitude");
        } else if (properties.get("magnitude") instanceof Long) {
            magnitude = ((Long) properties.get("magnitude")).doubleValue();
        }
        String out = String.format("%tc\n Magnitude: %.3f M\n Depth: %.2f km\n Locality: %s\n Coordinates: %s\n Quality: %s\n MMI: %d", quaketime, magnitude, depth, properties.get("locality"), geometry.get("coordinates"), properties.get("quality"), properties.get("mmi"));
        //System.out.println(out);
        return out;
    }
    public static Date parseTime(String timein) throws Exception{
        // parses the JSON time format
        SimpleDateFormat tformat = new SimpleDateFormat("yyyy-MM-dd'T'H:m:s.S");
        tformat.setTimeZone(TimeZone.getTimeZone("GMT")); // since geonet returns zulu time
        Date converted = tformat.parse(timein);
        return converted;
    }
}
