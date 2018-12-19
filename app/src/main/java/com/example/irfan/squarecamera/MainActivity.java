package com.example.irfan.squarecamera;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    public int counterCount=0;
    protected static String TAG = MainActivity.class.getSimpleName();
    protected CardView btnCamerad;
    protected CardView goToTable;
    protected CardView btnPredict;
    protected CardView truncateTable;
    protected CardView goToScanQR;
    protected CardView goToTrain;
    protected CardView signIn;
    protected CardView  uploadSignature;
    protected CardView  trainSignature;
    protected CardView  predictSignature;
    TextView hint;

    private String hintPakaiKacamata[] = new String[]{
            "1. Normal tegak lurus kamera.",
            "2. Normal tegak tegak lurus kamera tidak berkacamata.",
            "3. Tersenyum tegak lurus kamera.",
            "4. Sedih tegak lurus kamera.",
            "5. Mengantuk tegak lurus kamera.",
            "6. Normal menoleh ke kanan 30 derajat.",
            "7. Normal menoleh ke kanan 30 derajat tidak berkacamata.",
            "8. Tersenyum menoleh ke kanan 30 derajat.",
            "9. Sedih menoleh ke kanan 30 derajat.",
            "10. Mengantuk menoleh ke kanan 30 derajat.",
            "11. Normal menoleh ke kiri 30 derajat.",
            "12. Normal menoleh ke kiri 30 derajat tidak berkacamata.",
            "13. Tersenyum menoleh ke kiri 30 derajat.",
            "14. Sedih menoleh ke kiri 30 derajat.",
            "15. Mengantuk menoleh ke kiri 30 derajat.",
            "16. Normal tegak lurus kamera muka basah.",
            "17. Normal tegak lurus kamera tidak berkacamata muka basah.",
            "18. Tersenyum tegak lurus kamera muka basah.",
            "19. Sedih tegak lurus kamera muka basah.",
            "20. Mengantuk tegak lurus kamera muka basah.",
            "21. Normal menoleh ke kanan 30 derajat muka basah.",
            "22. Normal menoleh ke kanan 30 derajat tidak berkacamata muka basah.",
            "23. Tersenyum menoleh ke kanan 30 derajat muka basah.",
            "24. Sedih menoleh ke kanan 30 derajat muka basah.",
            "25. Mengantuk menoleh ke kanan 30 derajat muka basah.",
            "26. Normal menoleh ke kiri 30 derajat muka basah.",
            "27. Normal menoleh ke kiri 30 derajat tidak berkacamata muka basah.",
            "28. Tersenyum menoleh ke kiri 30 derajat muka basah.",
            "29. Sedih menoleh ke kiri 30 derajat muka basah.",
            "30. Mengantuk menoleh ke kiri 30 derajat muka basah."
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        requestReadStoragePermission();
        requestWriteStoragePermission();

        btnCamerad = findViewById(R.id.btnMenuDataset);
        btnPredict=findViewById(R.id.btnMenuPredict);
        goToTable = findViewById(R.id.btnMenuTable);
        truncateTable = findViewById(R.id.btnTruncateTable);
        goToScanQR = findViewById(R.id.scanQRCode);
        goToTrain = findViewById(R.id.btnMenuTrain);
        signIn = findViewById(R.id.signIn);
        uploadSignature = findViewById(R.id.UploadSignature);
        predictSignature = findViewById(R.id.PredictSignature);
        trainSignature = findViewById(R.id.btnTrainSignature);


        hint=findViewById(R.id.hintMain);
        hint.setText(hintPakaiKacamata[counterCount]);
        btnCamerad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameradActivity.class);
                intent.putExtra("counterCount",counterCount);
                startActivityForResult(intent,250);
            }
        });
        goToTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                startActivity(intent);
            }
        });
        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PredictActivity.class);
                startActivity(intent);
            }
        });
        goToScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });
        goToTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TrainActivity.class);
                startActivity(intent);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        uploadSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UploadSignature.class);
                startActivity(intent);
            }
        });
        predictSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PredictSignature.class);
                startActivity(intent);
            }
        });
        trainSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TrainSignature.class);
                startActivity(intent);
            }
        });
    }

    public  void requestReadStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        }
    }

    public  void requestWriteStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        counterCount++;
        hint.setText(hintPakaiKacamata[counterCount]);
    }
}