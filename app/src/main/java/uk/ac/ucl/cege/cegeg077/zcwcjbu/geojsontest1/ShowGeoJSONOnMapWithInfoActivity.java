package uk.ac.ucl.cege.cegeg077.zcwcjbu.geojsontest1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.view.Display;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPoint;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;
import com.google.maps.android.kml.KmlPolygon;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ShowGeoJSONOnMapWithInfoActivity extends FragmentActivity
        implements OnMarkerClickListener, OnMapReadyCallback
        //, LocationListener
{

    private static TextView latitude;
    private static TextView longitude;
    Context context = this;
    private Context baseContext;
    private View v;
    public ArrayList<String> questionarray = new ArrayList<String>();
    private GeoJsonLayer mLayer;
    private GoogleMap map;
    private final static String mLogTag = "GeoJsonDemo";

    List<String> q = new ArrayList<String>();
    List<String> c1 = new ArrayList<String>();
    List<String> c2 = new ArrayList<String>();
    List<String> c3 = new ArrayList<String>();
    List<String> a = new ArrayList<String>();
    String question;
    String choiceone;
    String choicetwo;
    String choicethree;
    String answer;
    String qtest;
    String question1;
    String e;
    List listq = Arrays.asList(q);

    //establishing global Hash Map arrays to sort values baased on location i.e. getting the correct values to show upon pssing the intent
    ArrayList<HashMap<String, String>> questionLocation = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> choiceOneLocation = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> choiceTwoLocation = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> choiceThreeLocation = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> answerLocation = new ArrayList<HashMap<String, String>>();


    GeoPoint gMapPointsql;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geojson);

        // assign the map fragment to a variable so that we can manipulate it
        SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

        // set up the code to call onMapReady once the map is drawn
        // Async here refers to Asynchronous -i.e. the system will only call the next method once
        // the map is drawn (normally a line of code runs immediately after the previous one, i.e. is synchronous)
        mapFragment.getMapAsync(this);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //when location is changed set alert, start an action (in this case the activity)
                //hard coded point list
                ArrayList<GeoPoint> pointList = new ArrayList<GeoPoint>();
                // ucl
                GeoPoint gMapPoint = new GeoPoint(51.524610, -0.133510);
                // petrie
                GeoPoint gMapPoint2 = new GeoPoint(51.523604, -0.132952);
                //bf
                GeoPoint gMapPoint3 = new GeoPoint(51.525000, -0.132621);
                //archaeology
                GeoPoint gMapPoint4 = new GeoPoint(51.524908, -0.131580);
                //string to compare lat received to sql to gather the correct information
                // add these points to the list
                pointList.add(gMapPoint);
                pointList.add(gMapPointsql);

                pointList.add(gMapPoint2);
                pointList.add(gMapPoint3);
                pointList.add(gMapPoint4);
                //pointList.add(gMapPointsql);
                //Toast message that shows upon reaching a point

                Toast.makeText(getBaseContext(),
                        "You have moved to a Quiz Question Location: Latitude/longitude:" + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();
                //method to fetch points
                for (int i = 0; i < pointList.size(); i++) {
                    //GeoPoint gp = pointList.get(i);
                    GeoPoint gp = pointList.get(i);
                    //log statement to check whether location listner is functioning (activated onLocationChanged)
                    Log.d("MRG", "It worked");
                    Location fixedLoc = new Location("one");
                    float distance = location.distanceTo(fixedLoc);
                    //Double lat = Double.valueOf(String.valueOf(gp.getLatitude()));
                    //Double lng = Double.valueOf(String.valueOf(gp.getLongitude()));
                    //fixedLoc.setLatitude(lat);
                   // //fixedLoc.setLongitude(lng);
                   // Log.i("location", lat + " " + location.getLatitude());
                   // Log.i("location", lng + " " + location.getLongitude());

                    // we can make use of the Android distanceTo function to calculate the distances

                }
                Log.d("MRG", "It worked");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {


            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
       // if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
           // return;
        //}
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onMapReady(final GoogleMap newmap) {
        // centre the map on the UK at the start
        // NB - at this point the map hasn't initialised so we also need to define the map size
        // this is set to the screen size by default so we first need to get the size
        // of the screen in pixels
        LatLngBounds UK = new LatLngBounds(new LatLng(51, -0.5), new LatLng(51.5, 0.5));
        LatLng mapCentre = new LatLng(51.25, 0);
        map = newmap;
        // Marker marker1;
        //marker1 = map.addMarker(new MarkerOptions()
        //   .position(mapCentre)
        //   .title("Hello world")
        //  .snippet("This is a snippet")
        // .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(UK, 1, 1, 0));
        retrieveFileFromUrl();
        map.setOnMarkerClickListener((OnMarkerClickListener) this);
    }

    private void retrieveFileFromUrl() {
        //php file referenced with GeoJSON
        String mGeoJsonUrl
                = "http://developer.cege.ucl.ac.uk:30522/teaching/user36/QuestionsApp.php";
        //Triggers DownloadGeoJSONFile class
        DownloadGeoJsonFile downloadGeoJsonFile = new DownloadGeoJsonFile();
        downloadGeoJsonFile.execute(mGeoJsonUrl);
    }


    // the function that responds to the click event on a marker
    public boolean onMarkerClick(final Marker marker) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(marker.getTitle());
        dialog.setMessage(marker.getSnippet());
        String url;
        WebView wv = new WebView(this);
        wv.setBackgroundColor(Color.CYAN);
        wv.getSettings().setDefaultFontSize(16);
        wv.getSettings().setDefaultTextEncodingName("utf-8");
        dialog.setView(wv);
        dialog.show();
        System.out.print(listq.get(0));
       //Fetches correct values for a particular point values via hash map by evaluating them against the marker title of the point
        for (HashMap<String, String> questions : questionLocation) {
            if (questions.get(marker.getTitle()) != null) {
                question = questions.get(marker.getTitle());
                break;
            }
        }

        for (HashMap<String, String> choiceOnes : choiceOneLocation) {
            if (choiceOnes.get(marker.getTitle()) != null) {
                choiceone = choiceOnes.get(marker.getTitle());
                break;
            }
        }

        for (HashMap<String, String> choiceTwos : choiceTwoLocation) {
            if (choiceTwos.get(marker.getTitle()) != null) {
                choicetwo = choiceTwos.get(marker.getTitle());
                break;
            }
        }

        for (HashMap<String, String> choiceThrees : choiceThreeLocation) {
            if (choiceThrees.get(marker.getTitle()) != null) {
                choicethree = choiceThrees.get(marker.getTitle());
                break;
            }
        }

        for (HashMap<String, String> answers : answerLocation) {
            if (answers.get(marker.getTitle()) != null) {
                answer = answers.get(marker.getTitle());
                break;
            }
        }
//        if (marker.getTitle().equals("Bloomsbury Fitness")) {
//            question = "What is a Chest Exercise?";
//            choiceone = "Bench Press";
//            choicetwo = "Squat";
//            choicethree = "Lunge";
//            answer = "Bench Press";
//
//        } else if (marker.getTitle().equals("Petrie Museum")) {
//            question = "How many artefacts are at the Petrie Museum?";
//            choiceone = "Fifty Thousand";
//            choicetwo = "Eighty Thousand";
//            choicethree = "Sixty Thousand";
//            answer = "Eighty Thousand";
//        } else {
//            question = "What year was UCL founded?";
//            choiceone = "Eighteen Twenty Six";
//            choicetwo = "Eighteen Fifty";
//            choicethree = "Eighteen Eighty Six";
//            answer = "Eighteen Twenty Six";
//        }

        Intent intent = new Intent();
        // set the class name for the intent - this will tell Android which Java file to use
        // to start the activity
        // depending on the marker title, pass the appropriate URL
        intent.setClassName("uk.ac.ucl.cege.cegeg077.zcwcjbu.geojsontest1", "uk.ac.ucl.cege.cegeg077.zcwcjbu.geojsontest1.SendCheckboxToServer");


        // pass the URL to the next activity using the intent
        intent.putExtra("q", question);
        intent.putExtra("c1", choiceone);
        intent.putExtra("c2", choicetwo);
        intent.putExtra("c3", choicethree);
        intent.putExtra("a", answer);
        // and now start the activity itself
        this.startActivity(intent);
        return true;
    }

    private void addColorsToMarkers() {
        // Iterate over all the features stored in the layer
        for (GeoJsonFeature feature : mLayer.getFeatures()) {
            if (feature.hasProperty("name") && feature.hasProperty("question")) {
                // double magnitude = Double.parseDouble(feature.getProperty("mag"));

                // Get the icon for the feature
                BitmapDescriptor pointIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
                //double coords = Double.parseDouble(feature.getProperty("coords"));
                // Create a new point style
                // Set options for the point style
                pointStyle.setIcon(pointIcon);
                pointStyle.setTitle(feature.getProperty("name"));
                //Establish Hash Maps for each value type so that the correct quesion can be located in the next activity
                HashMap<String, String> qustionL = new HashMap<String, String>();
                qustionL.put(feature.getProperty("name"), feature.getProperty("question"));
                questionLocation.add(qustionL);
                pointStyle.setSnippet(feature.getProperty("question"));
                question = feature.getProperty("question");
                //no value showing for coords hence why the below lines of code (commented out) will not work to fetch the points
                //and add to SQL - luckily this does not affect the placing of the marker points
         //double coords = Double.parseDouble(feature.getProperty("coords"));
                //GeoJsonPoint coord = (GeoJsonPoint) feature.getGeometry();
                //double lat = coord.getCoordinates().latitude;
                //double lng = coord.getCoordinates().longitude;
                //GeoPoint gMapPoint = new GeoPoint(lat, lng);
                //ArrayList<GeoPoint> pointList = new ArrayList<GeoPoint>();
                //pointList.add(gMapPoint); - this would then be referenced in onLocationChanged and passed through
                //the listener instead of the hard coded points. Then in turn it would be possible to reference particular
                //question values using the Hash Map in relation to coordinate points
                String coords = feature.getProperty("coords");
                Log.d(coords, "coords");
                q.add(question);
                Log.d(question, "question");

                choiceone = feature.getProperty("choiceone");
                c1.add(feature.getProperty("choiceone"));
                Log.d(choiceone, "choiceone");
                HashMap<String, String> choiceOne = new HashMap<String, String>();
                choiceOne.put(feature.getProperty("name"), choiceone);
                choiceOneLocation.add(choiceOne);

                choicetwo = feature.getProperty("choicetwo");
                c2.add(feature.getProperty("choicetwo"));
                HashMap<String, String> choiceTwo = new HashMap<String, String>();
                choiceTwo.put(feature.getProperty("name"), choicetwo);
                choiceTwoLocation.add(choiceTwo);

                choicethree = feature.getProperty("choicethree");
                Log.d(choicethree, "choicethree");
                HashMap<String, String> choiceThree = new HashMap<String, String>();
                choiceThree.put(feature.getProperty("name"), choicethree);
                choiceThreeLocation.add(choiceThree);
                System.out.println(Arrays.asList(c3));
                c3.add(feature.getProperty("choicethree"));
                answer = feature.getProperty("answer");
                HashMap<String, String> answerhash = new HashMap<String, String>();
                answerhash.put(feature.getProperty("name"), answer);
                answerLocation.add(answerhash);
                Log.d(answer, "answer");
                System.out.println(Arrays.asList());
                feature.setPointStyle(pointStyle);

            }
        }
    }

    //@Override
    // public void onLocationChanged(Location location) {
    //     System.out.print("sss");
    //}

    // @Override
    // public void onStatusChanged(String s, int i, Bundle bundle) {

    // }

    // @Override
    // public void onProviderEnabled(String s) {

    //  }

    // @Override
    // public void onProviderDisabled(String s) {

    // }

    private class DownloadGeoJsonFile extends AsyncTask<String, Void, JSONObject> {
        protected JSONObject doInBackground(String... params) {
            try {
                // Open a stream from the URL
                InputStream stream = new URL(params[0]).openStream();

                String line;
                StringBuilder result = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                while ((line = reader.readLine()) != null) {
                    // Read and save each line of the stream
                    result.append(line);
                }

                // Close the stream
                reader.close();
                stream.close();

                // Convert result to JSONObject
                return new JSONObject(result.toString());
            } catch (IOException e) {
                Log.e(mLogTag, "GeoJSON file could not be read");
            } catch (JSONException e) {
                Log.e(mLogTag, "GeoJSON file could not be converted to a JSONObject");
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                // Create a new GeoJsonLayer, pass in downloaded GeoJSON file as JSONObject
                mLayer = new GeoJsonLayer(map, jsonObject);
                // Add the layer onto the map
                addColorsToMarkers();
                mLayer.addLayerToMap();
            }

        }

        public void onLocationChanged(Location location) {
            System.out.print("sss");
            Log.i("ww", "GeoJSON file could not be read");
        }
    }
}
