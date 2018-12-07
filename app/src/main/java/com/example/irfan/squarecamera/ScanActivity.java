package com.example.irfan.squarecamera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;

public class ScanActivity extends AppCompatActivity implements LocationListener {
    private CodeScanner mCodeScanner;
    private double classLong;
    private double classLat;
    private double longitude;
    private double latitude;
    Location location;
    private String className;
    public String idAgenda;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(final com.google.zxing.Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] splitted = result.getText().split(",");
                        classLat = Double.parseDouble(splitted[0]);
                        classLong = Double.parseDouble(splitted[1]);
                        className = splitted[2];

                        Log.d("CLASS NAME", splitted[2]);
                        Log.d("CLASS LAT", splitted[0]);
                        Log.d("CLASS LONG", splitted[1]);

                        GpsTracker gpsTracker = new GpsTracker(ScanActivity.this);
                        if(gpsTracker.canGetLocation()){
                            latitude = gpsTracker.getLatitude();
                            longitude = gpsTracker.getLongitude();
                            float[] results = new float[3];

                            Location locationA = new Location("point A");
                            locationA.setLatitude(latitude);
                            locationA.setLongitude(longitude);

                            Location locationB = new Location("point B");
                            locationB.setLatitude(classLat);
                            locationB.setLongitude(classLong);

                            double jarak = locationA.distanceTo(locationB);
                            Log.d("JARAK DARI KELAS : ", ""+jarak);

                            location.distanceBetween(latitude,longitude,classLat,classLong,results);
                            float distance = results[0];
                            Log.d("DISTANCE DARI KELAS : ", ""+distance);
                            float maxDistance = 50.00f;
                            if(distance <= maxDistance){
                                Toast.makeText(getApplicationContext(), "TERCATAT HADIR :"+distance, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ScanActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else Toast.makeText(getApplicationContext(), "ANDA JAUH DARI KELAS : " +distance, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // {Some Code}
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        int latitude = (int) (location.getLatitude());
        int longitude = (int) (location.getLongitude());

        Log.i("Geo_Location", "Latitude: " + latitude + ", Longitude: " + longitude);
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
}
