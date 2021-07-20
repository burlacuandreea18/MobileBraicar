package com.example.mobilebraicar3;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.min;



public class DirectionsJSONParser {

    public class Point {
            String mode;
            String name;
            Double latitude;
            Double longitude;
            Point from;
            Point to;
            ArrayList<PolylinePoint> polylinePoints;
    }

    public class PolylinePoint {
        String latitude;
        String longitude;
    }


    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Point> parse(JSONObject jObject){

        List<Point> routes = new ArrayList<Point>() ;
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;

        try {

            JSONObject plan = jObject.getJSONObject("plan");
            jRoutes = plan.getJSONArray("itineraries");

            /** Traversing all routes */
            for(int i=0;i<min(jRoutes.length(), 1  );i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");

                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
//                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");
//
//                    /** Traversing all steps */
//                    for(int k=0;k<jSteps.length();k++);

                    ArrayList<PolylinePoint> path = new ArrayList<PolylinePoint>();
                    String polyline = "";
                    polyline = (String)((JSONObject)((JSONObject)jLegs.get(j)).get("legGeometry")).get("points");
                    JSONObject fromObj = (JSONObject)((JSONObject)jLegs.get(j)).get("from");
                    JSONObject toObj = (JSONObject)((JSONObject)jLegs.get(j)).get("to");
                    Point from = new Point();
                    Point to = new Point();
                    Double fromLat = (Double)((JSONObject)fromObj).get("lat");
                    Double fromLng = (Double)((JSONObject)fromObj).get("lon");
                    String fromName = (String)((JSONObject)fromObj).get("name");
                    Double toLat = (Double)((JSONObject)toObj).get("lat");
                    Double toLng = (Double)((JSONObject)toObj).get("lon");
                    String toName = (String)((JSONObject)toObj).get("name");
                    from = new Point();
                    to = new Point();
                    Point leg = new Point();

                    from.longitude = fromLng;
                    from.latitude = fromLat;
                    from.name = fromName;
                    from.mode = (String)((JSONObject)fromObj).get("vertexType");
                    to.longitude = toLng;
                    to.latitude = toLat;
                    to.name = toName;
                    to.mode = (String)((JSONObject)toObj).get("vertexType");
                    leg.from = from;
                    leg.to = to;
                    leg.name = (String)((JSONObject)jLegs.get(j)).get("route");
                    leg.mode = (String)((JSONObject)jLegs.get(j)).get("mode");

                    List list = decodePoly(polyline);

                    /** Traversing all points */
                    for(int l=0;l <list.size();l++){
//                        HashMap<String, String> hm = new HashMap<String, String>();
//                        hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
//                        hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                        PolylinePoint p = new PolylinePoint();
                        p.latitude = Double.toString(((LatLng)list.get(l)).latitude);
                        p.longitude = Double.toString(((LatLng)list.get(l)).longitude);
                        path.add(p);
                    }

                    leg.polylinePoints = path;
//                    }
                    routes.add(leg);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
        }

        return routes;
    }

    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List decodePoly(String encoded) {

        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}