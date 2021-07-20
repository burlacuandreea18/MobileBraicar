package com.example.mobilebraicar3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
/*
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

 */
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_MIN_UPDATE_TIME = 10;
    private static final int LOCATION_MIN_UPDATE_DISTANCE = 1000;

    private MapView mapView;
    private GoogleMap googleMap;
    private Location location = null;
    private static final String TAG = "MainActivity";
    ArrayList markerPoints= new ArrayList();

    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            drawMarker(location, getText(R.string.i_am_here).toString());
            locationManager.removeUpdates(locationListener);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initView(savedInstanceState);

        BottomNavigationView bottomNavigationView = findViewById(R.id.buttom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu:
                        return true;
                    case R.id.maps:
                       startActivity(new Intent(getApplicationContext(),SecondActivity.class));
                       overridePendingTransition(0,0);
                       return true;
                }
                return false;
            }
        });





        //String apiKey=getString(R.string.api_key);

        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(),"AIzaSyDtgdKNd0uU8wyRrBk1pLl89UD1TMlheaA" );
        }

        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.destination);

        AutocompleteSupportFragment autocompleteFragment2 = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.origin);

        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(45.9442858, 25.0094303),
                new LatLng(45.9442858, 25.0094303)
        ));

        autocompleteFragment.setCountries("RO");

        autocompleteFragment2.setLocationBias(RectangularBounds.newInstance(
                new LatLng(45.9442858, 25.0094303),
                new LatLng(45.9442858, 25.0094303)
        ));

        autocompleteFragment2.setCountries("RO");

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment2.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(TAG, "Place: " + place.getId() + ", " + place.getName() + ", " + place.getLatLng());

                LatLng origin=new LatLng(place.getLatLng().latitude,place.getLatLng().longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(origin);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                googleMap.addMarker(markerOptions);

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

                if (markerPoints.isEmpty()) {
                    markerPoints.add(origin);
                } else {
                    markerPoints.set(0, origin);
                }

                System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        autocompleteFragment2.setHint("De unde plecati?");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.

                Log.i(TAG, "Place: " + place.getId() + ", " + place.getName() + ", " + place.getLatLng());

                LatLng destination=new LatLng(place.getLatLng().latitude,place.getLatLng().longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(destination);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                googleMap.addMarker(markerOptions);

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

                if (markerPoints.size() < 2) {
                    markerPoints.add(destination);
                } else {
                    markerPoints.set(1, destination);
                }

                System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + markerPoints.size() + markerPoints.get(1) + markerPoints.get(0));
                //TraceTheJourney(markerPoints);
                if (markerPoints.size() >= 2) {
                    LatLng origin = (LatLng) markerPoints.get(markerPoints.size() - 2);
                    LatLng dest = (LatLng) markerPoints.get(markerPoints.size() - 1);

                    //Getting URL to the Google Directions API
                    String _url = getDirectionsUrl(origin, dest);
                    String placeComposer = "{\"" +
                            "fromPlace\":{\"" +
                            "lat\":" + origin.latitude + ",\"" +
                            "lon\":" + origin.longitude + "},\"" +
                            "toPlace\":{\"" +
                            "lat\":" + dest.latitude + ",\"" +
                            "lon\":" + dest.longitude + "},\"" +
                            "mode\":\"TRANSIT,WALK\"}";

                    Thread thread = new Thread(new Runnable() {

                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void run() {
                            try  {
                                String data = MainActivity.executePost(_url, placeComposer);
                                JSONObject jObject;
                                Converter parser = new Converter();
                                Welcome rootObject = parser.fromJsonString(data);
                                Itinerary itinerary = rootObject.getPlan().getItineraries()[0];
                                Leg[] result = itinerary.getLegs();

                                ArrayList points = null;
                                PolylineOptions lineOptions = null;
                                MarkerOptions markerOptions = new MarkerOptions();
                                lineOptions = new PolylineOptions();

                                for (int i = 0; i < result.length; i++) {

                                    points = new ArrayList();

                                    List path = result[i].decodePoly();

                                    for (int j = 0; j < path.size(); j++) {
                                        double lat = ((LatLng)path.get(j)).latitude;
                                        double lng = ((LatLng)path.get(j)).longitude;
                                        LatLng position = new LatLng(lat, lng);

                                        points.add(position);
                                    }

                                    Handler mainHandler = new Handler(Looper.getMainLooper());

                                    PolylineOptions finalLineOptions = lineOptions;
                                    Leg[] finalResult = result;
                                    int finalI = i;
                                    Runnable myRunnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            Leg point = finalResult[finalI];
                                            LatLng latLngFrom = new LatLng(point.getFrom().getLat(), point.getFrom().getLon());
                                            LatLng latLngTo = new LatLng(point.getTo().getLat(), point.getTo().getLon());

                                            if (point.getFrom().getVertexType() != VertexType.NORMAL) {
                                                MarkerOptions markerOptions = new MarkerOptions();
                                                markerOptions.position(latLngFrom);
                                                markerOptions.title("Statie: " + point.getFrom().getName() + " autobuz " + point.getRoute() + " mod " + point.getFrom().getVertexType());
                                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                                googleMap.addMarker(markerOptions);
                                            }

                                            if (point.getTo().getVertexType() != VertexType.NORMAL) {
                                                MarkerOptions markerOptionsTo = new MarkerOptions();
                                                markerOptionsTo.position(latLngTo);
                                                    markerOptionsTo.title("Statie: " + point.getTo().getName() + " autobuz " + point.getRoute() + " mod " + point.getTo().getVertexType());
                                                markerOptionsTo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                                googleMap.addMarker(markerOptionsTo);
                                            }
                                        }
                                    };
                                    mainHandler.post(myRunnable);

                                    lineOptions.addAll(points);
                                }

                                lineOptions.width(15);
                                lineOptions.color(Color.RED);
                                lineOptions.geodesic(true);

                                Handler mainHandler = new Handler(Looper.getMainLooper());

                                PolylineOptions finalLineOptions = lineOptions;
                                Runnable myRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        googleMap.addPolyline(finalLineOptions);
                                    }
                                };
                                mainHandler.post(myRunnable);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }

            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        autocompleteFragment.setHint("Unde mergeti?");

    }
/*
    private void TraceTheJourney(ArrayList markerPoints) {
        markerPoints=this.markerPoints;

        if (markerPoints.size() >= 2) {
            LatLng origin = (LatLng) markerPoints.get(markerPoints.size() - 2);
            LatLng dest = (LatLng) markerPoints.get(markerPoints.size() - 1);

            //Getting URL to the Google Directions API
            String _url = getDirectionsUrl(origin, dest);
            String placeComposer = "{\"" +
                    "fromPlace\":{\"" +
                    "lat\":" + origin.latitude + ",\"" +
                    "lon\":" + origin.longitude + "},\"" +
                    "toPlace\":{\"" +
                    "lat\":" + dest.latitude + ",\"" +
                    "lon\":" + dest.longitude + "},\"" +
                    "mode\":\"TRANSIT,WALK\"}";

            Thread thread = new Thread(new Runnable() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void run() {
                    try  {
                        String data = MainActivity.executePost(_url, placeComposer);
                        JSONObject jObject;
                        List<List<HashMap<String, String>>> result = null;

                        jObject = new JSONObject(data);
                        DirectionsJSONParser parser = new DirectionsJSONParser();

                        result = parser.parse(jObject);

                        ArrayList points = null;
                        PolylineOptions lineOptions = null;
                        MarkerOptions markerOptions = new MarkerOptions();

                        for (int i = 0; i < result.size(); i++) {
                            points = new ArrayList();
                            lineOptions = new PolylineOptions();

                            List<HashMap<String, String>> path = result.get(i);

                            for (int j = 0; j < path.size(); j++) {
                                HashMap<String, String> point = path.get(j);

                                double lat = Double.parseDouble(point.get("lat"));
                                double lng = Double.parseDouble(point.get("lng"));
                                LatLng position = new LatLng(lat, lng);

                                points.add(position);
                            }

                            lineOptions.addAll(points);
                            lineOptions.width(15);
                            lineOptions.color(Color.RED);
                            lineOptions.geodesic(true);

                        }

                        Handler mainHandler = new Handler(Looper.getMainLooper());

                        PolylineOptions finalLineOptions = lineOptions;
                        ArrayList finalPoints = points;
                        Runnable myRunnable = new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < finalPoints.size(); i++) {
                                    LatLng point = (LatLng) finalPoints.get(i);
                                    LatLng latLng = new LatLng(point.latitude, point.longitude);
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(latLng);
                                    markerOptions.title("I: " + i);
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                    googleMap.addMarker(markerOptions);
                                }
                                googleMap.addPolyline(finalLineOptions);
                            }
                        };
                        mainHandler.post(myRunnable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }

    }
    */


    private void initView(Bundle savedInstanceState) {
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapView_onMapReady(googleMap);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //getCurrentLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void initMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (googleMap != null) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.getUiSettings().setAllGesturesEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 13);
            }
        }
    }

  private void updateCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(getApplicationContext(), getText(R.string.provider_failed), Toast.LENGTH_LONG).show();
            } else {
                location = null;
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_MIN_UPDATE_TIME, LOCATION_MIN_UPDATE_DISTANCE, locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_MIN_UPDATE_TIME, LOCATION_MIN_UPDATE_DISTANCE, locationListener);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (location != null) {
                    //drawMarker(location, getText(R.string.i_am_here).toString());
                    LatLng pickUp= new LatLng(location.getLatitude(),location.getLongitude());


                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 13);
            }
        }
    }


    private void drawMarker(Location location,  String title) {
        if (this.googleMap != null) {
            googleMap.clear();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(title);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            googleMap.addMarker(markerOptions);

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        }
    }

    private void mapView_onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        initMap();
        //updateCurrentLocation();

        if (markerPoints.size() >= 2) {
            LatLng origin = (LatLng) markerPoints.get(0);
            LatLng dest = (LatLng) markerPoints.get(1);

            //Getting URL to the Google Directions API
            String url = getDirectionsUrl(origin, dest);

            DownloadTask downloadTask = new DownloadTask();

            downloadTask.execute(url);
        }

    }

    private class DownloadTask extends AsyncTask<String, Void, String>  {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }


    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

//                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

            PolylineOptions rectOptions = new PolylineOptions();

            rectOptions = rectOptions.add(new LatLng(45.2658613, 27.9750674));
            rectOptions = rectOptions.add(new LatLng(45.2648074555723, 27.9699187754632));
            rectOptions = rectOptions.width(5);
            rectOptions = rectOptions.color(Color.RED);
            googleMap.addPolyline(rectOptions);


// Drawing polyline in the Google Map for the i-th route
//            googleMap.addPolyline(lineOptions);
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=transit";

        // key
        String key = "key=AIzaSyDtgdKNd0uU8wyRrBk1pLl89UD1TMlheaA";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return "https://app.braila-transport.com/api/v1/74c48b41-6865-4be2-a305-639baa7ae148/transport/planner/plan";
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }



}
