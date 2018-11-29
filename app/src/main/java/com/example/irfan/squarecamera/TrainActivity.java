package com.example.irfan.squarecamera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TrainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        doTrain();
    }

    protected void doTrain() {
        StringRequest stringRequest;
        for (int i = 0; i < 1; i++) {
            final int index = i;
            stringRequest = new StringRequest(Request.Method.POST, "http://etc.if.its.ac.id/doTrain/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "the response : " + response, Toast.LENGTH_LONG).show();
                            Log.d("RESP", "onResponse: "+response);
//                            JSONObject obj = new JSONObject();
//                            message = obj.optString("message");
//                            probability = obj.optLong("probability");
//                            validation = obj.optString("validation");
//                            switch (message) {
//                                case "ok":
//                                    tvValidasi.setText("Hasil Prediksi : " + validation + "\ndengan Probability : " + probability);
//                                default:
//                                    tvValidasi.setText("GAGAL PREDIKSI");
//                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            //Showing toast
                            Toast.makeText(TrainActivity.this, volleyError.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    // Adding parameters
                    params.put("idUser", "5115100007");
                    params.put("password", "5115100007");
                    //returning parameters
                    return params;
                }
            };
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }
}
