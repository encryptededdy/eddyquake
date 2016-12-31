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
    public static void showQuakes(int mmi) throws Exception {
        String content = URLConnectionReader.getText("https://api.geonet.org.nz/quake?MMI=" + mmi);
        // parse the JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(content);
        fetchedJSON = (JSONObject) obj;
        JSONArray quakes = (JSONArray) fetchedJSON.get("features");
        System.out.println("Showing last "+ quakes.size()+" quakes in GeoNet with MMI > "+ mmi);
        System.out.println("ID | Time                   | Magnitude | Depth   | Locality");
        for (int i = quakes.size()-1; i >= 0; i--) {
            JSONObject quakedata = (JSONObject) quakes.get(i);
            JSONObject properties = (JSONObject) quakedata.get("properties");
            Date quaketime = GeoNet.parseTime((String) properties.get("time"));
            String line = String.format("%02d | %tr %<tb %<td, %<ty | %.1f M     | %04.1f km | %s", i, quaketime, properties.get("magnitude"), properties.get("depth"), properties.get("locality"));
            System.out.println(line);
        }
        System.out.println("ID | Time                   | Magnitude | Depth   | Locality");
    }
    public static void quakeDetail(int id) throws Exception {
        System.out.println("Showing data for quake ID " + id);
        JSONArray quakes = (JSONArray) fetchedJSON.get("features");
        JSONObject quakedata = (JSONObject) quakes.get(id);
        JSONObject properties = (JSONObject) quakedata.get("properties");
        JSONObject geometry = (JSONObject) quakedata.get("geometry");
        Date quaketime = GeoNet.parseTime((String) properties.get("time"));
        String out = String.format("Time: %tc\n Magnitude: %.2f M\n Depth: %.2f km\n Locality: %s\n Coordinates: %s\n Quality: %s\n MMI: %d", quaketime, properties.get("magnitude"), properties.get("depth"), properties.get("locality"), geometry.get("coordinates"), properties.get("quality"), properties.get("mmi"));
        System.out.println(out);
    }
    public static Date parseTime(String timein) throws Exception{
        // parses the JSON time format
        SimpleDateFormat tformat = new SimpleDateFormat("yyyy-MM-dd'T'H:m:s.S");
        tformat.setTimeZone(TimeZone.getTimeZone("GMT")); // since geonet returns zulu time
        Date converted = tformat.parse(timein);
        return converted;
    }
}
