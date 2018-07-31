package local.carstudiomanager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.capur16.digitspeedviewlib.DigitSpeedView;
import com.github.capur16.digitspeedviewlib.OnSpeedChangeListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainPage extends AppCompatActivity implements LocationListener {

    AppCompatSeekBar seekBar;
    DigitSpeedView digitSpeedView;

    private TextView dateTimeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private TextView batteryTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/www/index.html");

        webView.getSettings().setJavaScriptEnabled(true); // Enabling JavaScript

        // Hide status bar
        //    View decorView = getWindow().getDecorView();
        //    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //    decorView.setSystemUiVisibility(uiOptions);
        //    ActionBar actionBar = getActionBar();
        //    actionBar.hide();

        // Date & time display in TextView
        dateTimeDisplay = (TextView) findViewById(R.id.text_date_display);
        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("HH:mm");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

        // Battery display in TextView
        batteryTxt = (TextView) this.findViewById(R.id.batteryTxt);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        // Current Location
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        this.onLocationChanged(null);

        // Digital Speed dial
        DigitSpeedView digitSpeedView = (DigitSpeedView)findViewById(R.id.digit_speed_view);
        digitSpeedView.setOnSpeedChangeListener(new OnSpeedChangeListener() {
            @Override
            public void onSpeedChange(DigitSpeedView digitSpeedView, boolean isSpeedUp) {
                //Current speed: digitSpeedView.getSpeed();
            }
        });
    }

    // Battery info
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryTxt.setText(String.valueOf(level) + "%");
        }
    };

    // GPS SConfig
    public void finish()
    {
        super.finish();
        System.exit(0);
    }

    public void onLocationChanged(Location location) {
        TextView currentSpeedText = (TextView) this.findViewById(R.id.currentSpeed);

        if(location==null)
        {
            currentSpeedText.setText("-.- m/s");
        } else {
            float nCurrentSpeed = location.getSpeed();
            currentSpeedText.setText(nCurrentSpeed + " m/s");
        }
    }
    public void onProviderDisabled(String provider) {

    }
    public void onProviderEnabled(String provider) {

    }
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    public void onGpsStatusChanged(int event) {

    }
}
