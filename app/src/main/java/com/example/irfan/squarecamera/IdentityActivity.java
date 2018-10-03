package com.example.irfan.squarecamera;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class IdentityActivity extends AppCompatActivity {

    protected Button btnAmbilFoto;
    protected EditText etNrp;
    protected String nrp;
    protected SweetAlertDialog loadingDialog;
    protected static String CEK_URL = "https://ezforus.com/upload/cek.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);

        etNrp = (EditText) findViewById(R.id.etNrp);
        btnAmbilFoto = (Button) findViewById(R.id.btnAmbilFoto);
        btnAmbilFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nrp = etNrp.getText().toString();

                if (!nrp.equals("")) {
                    showLoadingDialog();
                    cek();
                }
            }
        });
    }

    protected void intentCameraActivity(boolean pakaiKacamata) {
        Intent intent = new Intent(IdentityActivity.this, CameradActivity.class);
        intent.putExtra("nrp", nrp);
        intent.putExtra("pakaiKacamata", pakaiKacamata);
        startActivity(intent);
        finish();
    }

    protected void showKacamataDialog() {
        loadingDialog
                .setContentText("Apakah anda berkacamata?")
                .setCancelText("Tidak")
                .setConfirmText("Ya")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        intentCameraActivity(false);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        intentCameraActivity(true);
                    }
                })
                .changeAlertType(SweetAlertDialog.NORMAL_TYPE);
    }

    protected void showDataExistDialog() {
        loadingDialog.setTitleText("Data Exists!")
                .setContentText("Data sudah ada untuk NRP: " + nrp)
                .changeAlertType(SweetAlertDialog.WARNING_TYPE);
    }

    protected void showLoadingDialog() {
        loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        loadingDialog.setTitleText("Checking data");
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    protected void closeLoadingDialog() {
        loadingDialog.cancel();
    }

    protected void cek() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CEK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("ok")) {
                            showDataExistDialog();
                        } else if (response.equals("error")) {
                            showKacamataDialog();
                        } else {
                            Toast.makeText(IdentityActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Showing toast
                        Toast.makeText(IdentityActivity.this, volleyError.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Get encoded Image
                Map<String, String> params = new HashMap<>();
                // Adding parameters
                params.put("nrp", nrp);

                //returning parameters
                return params;
            }
        };
        //Adding request to the queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
