package local.carstudiomanager;

import android.location.Location;

public class GPSManager extends Location {

    private boolean bUseMetricUnits = false;

    public GPSManager(Location location)
    {
        this(location, true);
    }

    public GPSManager(Location location, boolean bUseMetricUnits) {
        // TODO Auto-generated constructor stub
        super(location);
        this.bUseMetricUnits = bUseMetricUnits;
    }

    public boolean getUseMetricUnits()
    {
        return this.bUseMetricUnits;
    }

    public void setUseMetricUnits(boolean bUseMetricUnits)
    {
        this.bUseMetricUnits = bUseMetricUnits;
    }

    @Override
    public float distanceTo(Location dest) {
        // TODO Auto-generated method stub
        float nDistance = super.distanceTo(dest);
        if(!this.getUseMetricUnits())
        {
            //Convert meters to feet
            nDistance = nDistance * 3.28083989501312f;
        }
        return nDistance;
    }

    @Override
    public float getAccuracy() {
        // TODO Auto-generated method stub
        float nAccuracy = super.getAccuracy();
        if(!this.getUseMetricUnits())
        {
            //Convert meters to feet
            nAccuracy = nAccuracy * 3.28083989501312f;
        }
        return nAccuracy;
    }

    @Override
    public double getAltitude() {
        // TODO Auto-generated method stub
        double nAltitude = super.getAltitude();
        if(!this.getUseMetricUnits())
        {
            //Convert meters to feet
            nAltitude = nAltitude * 3.28083989501312d;
        }
        return nAltitude;
    }

    @Override
    public float getSpeed() {
        // TODO Auto-generated method stub
        float nSpeed = super.getSpeed() * 3.6f;
        if(!this.getUseMetricUnits())
        {
            //Convert meters/second to miles/hour
            nSpeed = nSpeed * 2.2369362920544f/3.6f;
        }
        return nSpeed;
    }

}