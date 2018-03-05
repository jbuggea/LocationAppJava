package uk.ac.ucl.cege.cegeg077.zcwcjbu.geojsontest1;

/**
 * Created by clairee on 26/12/2015.
 * This is anattempt to establish the class GeoPoint for use when fetching the coordinates from the database. I could not achieve this in the overall
 code but have included it still to show how it would be created. The GeoPoin class would be referenced in the Main Activity screen
 */
public class GeoPoint {
    private Double latitude;
    private Double longitude;

    public GeoPoint(Double lat, Double lng){
        latitude = lat;
        longitude = lng;
    }

    public Double getLongitude() {
        return longitude;
    }
    public Double getLatitude() {
        return latitude;
    }
}
