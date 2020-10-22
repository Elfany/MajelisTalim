package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Input_MajlisTaklim extends AppCompatActivity {

    ProgressDialog pDialog;
    Button btn_tambah, btn_edit, btn_reset;
    EditText txt_namaacara, txt_temaacara, txt_alamatacara, txt_waktuacara, txt_tglacara,
            txt_pengisiacara, txt_longitude, txt_latitude;
    Intent intent;
    int success;
    ConnectivityManager conMgr;

    private String url = Server.URL + "input_majlistaklim.php";

    private static final String TAG = Input_MajlisTaklim.class.getSimpleName();
    private static final  String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_inputmajlis);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_tambah = (Button) findViewById(R.id.btn_tambah);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        txt_namaacara = (EditText) findViewById(R.id.txt_namaacara);
        txt_temaacara = (EditText) findViewById(R.id.txt_temaacara);
        txt_waktuacara = (EditText) findViewById(R.id.txt_waktuacara);
        txt_tglacara = (EditText) findViewById(R.id.txt_tglacara);
        txt_pengisiacara = (EditText) findViewById(R.id.txt_pengisiacara);
        txt_latitude = (EditText) findViewById(R.id.txt_latitude);
        txt_longitude = (EditText) findViewById(R.id.txt_longitude);

        btn_tambah.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String nama = txt_namaacara.getText().toString();
                String tema = txt_temaacara.getText().toString();
                String tanggal = txt_tglacara.getText().toString();
                String waktu = txt_waktuacara.getText().toString();
                String pemateri = txt_pengisiacara.getText().toString();
                String alamat = txt_alamatacara.getText().toString();
                String longitude = txt_longitude.getText().toString();
                String latitude = txt_latitude.getText().toString();

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkInput_MajlisTaklim(nama, tema, tanggal, waktu, pemateri, alamat, latitude, longitude);
                } else {
                    Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkInput_MajlisTaklim(final String nama, final String tema, final String tanggal,
                                         final String waktu, final String pemateri, final String alamat,
                                         final String latitude, final String longitude) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Data Telah Ditambah");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Majlis Taklim Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Successfully ADD DATA!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        txt_namaacara.setText("");
                        txt_temaacara.setText("");
                        txt_tglacara.setText("");
                        txt_waktuacara.setText("");
                        txt_pengisiacara.setText("");
                        txt_alamatacara.setText("");
                        txt_latitude.setText("");
                        txt_longitude.setText("");

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "ADD Data Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama_acara", nama);
                params.put("tema_acara", tema);
                params.put("tanggal_acara", tanggal);
                params.put("waktu_acara", waktu);
                params.put("pengisi_acara", pemateri);
                params.put("alamat_acara", alamat);
                params.put("latitude", latitude);
                params.put("longitude", longitude);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(Input_MajlisTaklim.this, MajlisTaklim.class);
        finish();
        startActivity(intent);
    }
}
