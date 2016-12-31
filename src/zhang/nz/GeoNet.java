package zhang.nz; /**
 * Created by Edward Zhang on 31/12/2016.
 */
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.text.*;
import java.util.*;


public class GeoNet {
    public static void showQuakes(int mmi) throws Exception {
        String content = URLConnectionReader.getText("https://api.geonet.org.nz/quake?MMI=" + mmi);
        // parse the JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(content);
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray quakes = (JSONArray) jsonObject.get("features");
        System.out.println("Showing last "+ quakes.size()+" quakes in GeoNet with MMI > "+ mmi);
        System.out.println("ID | Time                   | Magnitude | Depth   | Locality");
        for (int i = quakes.size()-1; i >= 0; i--) {
            JSONObject quakedata = (JSONObject) quakes.get(i);
            JSONObject properties = (JSONObject) quakedata.get("properties");
            Date quaketime = GeoNet.parseTime((String) properties.get("time"));
            String line = String.format("%02d | %tr %<tb %<td, %<ty | %.1f M     | %04.1f km | %s", i, quaketime, properties.get("magnitude"), properties.get("depth"), properties.get("locality"));
            System.out.println(line);
            //System.out.println(quakes.get(i).);
        }
        System.out.println("ID | Time                   | Magnitude | Depth   | Locality");

        //return(content);
        //System.out.println(content);
    }
    public static Date parseTime(String timein) throws Exception{
        // parses the JSON time format
        SimpleDateFormat tformat = new SimpleDateFormat("yyyy-MM-dd'T'H:m:s.S");
        tformat.setTimeZone(TimeZone.getTimeZone("GMT")); // since geonet returns zulu time
        Date converted = tformat.parse(timein);
        return converted;
    }
}
