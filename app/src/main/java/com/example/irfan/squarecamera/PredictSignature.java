package com.example.irfan.squarecamera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
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

public class PredictSignature extends AppCompatActivity {
    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSendButton;

    EditText etNrp;
    EditText etPassword;
    private static String TAG = CameradActivity.class.getSimpleName();
    private CameraView camerad;
    private CameraKitEventListener cameradListener;
    private Button btnCapture;
    private TextView tvHint;
    private TextView tvValidasi;
    protected boolean pakaiKacamata;
    protected int counter = 0;
    private static String BASE_DIR = "camtest/";
    private String hint[];
    private String predictionResult = "none";
    private List<String> listPathFile;
    private ArrayList<String> encodedImagesList;
    protected SweetAlertDialog loadingDialog, errorDialog, successDialog;
    protected static String UPLOAD_URL = "http://etc.if.its.ac.id/doPredict_TTD/";
    private int requestCounter = 0;
    private boolean hasRequestFailed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_signature);
        etNrp= findViewById(R.id.nrp);
        etPassword= findViewById(R.id.passwrd);
        mSignaturePad = findViewById(R.id.signature_pad);
        mClearButton = (Button) findViewById(R.id.clear_button);
        mSendButton = (Button) findViewById(R.id.btn_send);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(PredictSignature.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                mClearButton.setEnabled(true);
                mSendButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mClearButton.setEnabled(false);
                mSendButton.setEnabled(false);
            }
        });
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nrp=etNrp.getText().toString();
                String password=etPassword.getText().toString();
                if(nrp!=null && password !=null)
                {
                    uploadSignature();
                }else Toast.makeText(getApplicationContext(),"Username atau Password belum diisi!",Toast.LENGTH_SHORT).show();

            }
        });
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        PredictSignature.this.sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {
            File svgFile = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.svg", System.currentTimeMillis()));
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void init() {
        etNrp = findViewById(R.id.nrp);
        etPassword = findViewById(R.id.passwrd);
        listPathFile = new ArrayList<>();
    }

    protected void uploadSignature() {
        //loadingDialog.setTitleText("Uploading Signature");
        StringRequest stringRequest;
        for (int i = 0; i < 1; i++) {
            final int index = i;
            stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Prediction Result : " + response, Toast.LENGTH_LONG).show();
                            Log.d(TAG, "onResponse: "+response);
                            predictionResult = response;
                            mSignaturePad.clear();
                            requestCounter--;
                            if (requestCounter == 0 && !hasRequestFailed) {
                                //closeLoadingDialog();
                                showSuccessDialog();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            hasRequestFailed = true;
                            //Showing toast
                            Toast.makeText(PredictSignature.this, volleyError.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    // Get encoded Image
                    //String image = encodedImagesList.get(index);
                    Map<String, String> params = new HashMap<>();

                    // Adding parameters
                    params.put("idUser", etNrp.getText().toString());
                    params.put("password", etPassword.getText().toString());

                    ByteArrayOutputStream baos;
                    byte[] byteArrayImage;
                    String image_base64;

                    Bitmap result = mSignaturePad.getSignatureBitmap();
                    baos=new ByteArrayOutputStream();


                    result.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byteArrayImage = baos.toByteArray();
                    image_base64 = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

                    params.put("image", "data:/image/jpeg;base64," + image_base64);

                    //returning parameters
                    return params;
                }
            };
            //Adding request to the queue
            requestCounter++;
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }
    }

    protected void closeLoadingDialog() {
        //loadingDialog.cancel();
    }

    protected void showSuccessDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Prediction Result : ")
                .setContentText(predictionResult)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                })
                .show();
    }
    protected void showHintDialog() {
        int hintNumber = getIntent().getIntExtra("counterCount", 0);
        tvHint.setText(hint[hintNumber]);
        new SweetAlertDialog(this)
                .setTitleText("Hint:")
                .setContentText(hint[hintNumber]);
        //.show();
    }

    protected void showLoadingDialog() {
        loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        loadingDialog.setTitleText("Uploading Images");
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }
}
