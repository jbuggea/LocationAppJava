package uk.ac.ucl.cege.cegeg077.zcwcjbu.geojsontest1;

/**
 * Created by clairee on 26/12/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


// a listener is a background service that is activated
// every time something happens that it is interested in - in this case
// the listener will be checking at regular intervals to see if the
// device has changed location.

//this class is unnecessary for this project however I have included it to show how a listener is constructed in case it is unclear within my main activity 
// this method is called every time the user moves around - i.e. changes location
// it pops up a toast message with the new coordinates
public class CustomLocationListener extends FragmentActivity implements LocationListener {
    public static ShowGeoJSONOnMapWithInfoActivity parentActivity ;
    public ArrayList<GeoPoint> pointList;
    private Context baseContext;
    private View v;

    Context context = this;


    // this method is called every time the user moves around - i.e. changes location
    // it pops up a toast message with the new coordinates
    public void onLocationChanged(Location location) {

        ArrayList<GeoPoint> pointList = new ArrayList<GeoPoint>();
        // ucl
        GeoPoint gMapPoint = new GeoPoint(51.524610,-0.133510);
        // petrie
        GeoPoint gMapPoint2 = new GeoPoint(51.523604, -0.132952);
        //bf
        GeoPoint gMapPoint3 = new GeoPoint(51.525000, -0.132621);
        //archaeology
        GeoPoint gMapPoint4 = new GeoPoint(51.524908, -0.131580);


        // add these points to the list
        pointList.add(gMapPoint);
        pointList.add(gMapPoint2);
        pointList.add(gMapPoint3);
        pointList.add(gMapPoint4);

        // Called when a new location is found by the network location provider.
        Toast.makeText(parentActivity.getBaseContext(),
                "You have moved to: Latitude/longitude:" + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();
        //Intent intent = new Intent();
        // set the class name for the intent - this will tell Android which Java file to use
         //to start the activity
         //depending on the marker title, pass the appropriate URL
        //intent.setClassName("uk.ac.ucl.cege.cegeg077.zcwcjbu.geojsontest1", "uk.ac.ucl.cege.cegeg077.zcwcjbu.geojsontest1.SendCheckboxToServer");

        //parentActivity.startActivity(intent);
        // now measure distance from all the pre-set proximity alerts
        for (int i = 0; i < pointList.size(); i++) {
            GeoPoint gp = pointList.get(i);
            Location fixedLoc = new Location("one");

                Double lat = Double.valueOf(String.valueOf(gp.getLatitude()));
            Double lng = Double.valueOf(String.valueOf(gp.getLongitude()));

            fixedLoc.setLatitude(lat);
            fixedLoc.setLongitude(lng);
            Log.i("location", lat + " " + location.getLatitude());
            Log.i("location", lng + " " + location.getLongitude());

            // we can make use of the Android distanceTo function to calculate the distances
            float distance = location.distanceTo(fixedLoc);

                }
            }

    // these methods are called when the GPS is switched on or off
    // and will allow the App to warn the user and then
    // shut down without an error
    public void onProviderDisabled(String s) {
    }
    public void onProviderEnabled(String s) {
    }


    // this method is required by the LocationListener
    // we do not need to do anything here
    // but in a full implementation this could be used to react when the GPS signal is not available
   public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
         //TODO Auto-generated method stub

    }


    public Context getBaseContext() {
        return baseContext;
    }

    public void setBaseContext(Context baseContext) {
        this.baseContext = baseContext;
    }
}

