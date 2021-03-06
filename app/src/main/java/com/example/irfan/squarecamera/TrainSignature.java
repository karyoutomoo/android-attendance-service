package com.example.irfan.squarecamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.williamww.silkysignature.views.SignaturePad;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TrainSignature extends AppCompatActivity {
    private SignaturePad mSignaturePad;
    EditText etNrp;
    EditText etPasswrd;
    String nrp;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        Button doTrain = findViewById(R.id.btn_train);
        doTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNrp = findViewById(R.id.nrp);
                etPasswrd = findViewById(R.id.passwrd);
                nrp = etNrp.getText().toString();
                password = etPasswrd.getText().toString();

                if (nrp != null && password != null) {
                    doTrain();
                }
                else Toast.makeText(getApplicationContext(),"User atau Password belum diisi!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void doTrain() {
        StringRequest stringRequest;
        for (int i = 0; i < 1; i++) {
            final int index = i;
            stringRequest = new StringRequest(Request.Method.POST, "http://etc.if.its.ac.id/doTrain_TTD/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "SIGNATURE : " + response, Toast.LENGTH_LONG).show();
                            Log.d("RESP", "onResponse: "+response);
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            //Showing toast
                            Toast.makeText(TrainSignature.this, volleyError.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    // Adding parameters
                    params.put("idUser", nrp);
                    params.put("password", password);
                    //returning parameters
                    return params;
                }
            };
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }
}
