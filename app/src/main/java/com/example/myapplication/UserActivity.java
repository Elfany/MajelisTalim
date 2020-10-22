package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myapplication.AdapterProcess;
import java.util.ArrayList;


public class UserActivity extends AppCompatActivity {

    ConnectivityManager conMgr;

    private static final String TAG = UserActivity.class.getSimpleName();
    private static final  String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";

    private String url = Server.URL + "user.php";

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    ArrayList<ModelData>
    mItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

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

        pd = new ProgressDialog(UserActivity.this);
        mRecyclerView = (RecyclerView)
                findViewById(R.id.list_data);
        mRecyclerView.setHasFixedSize(true);
        mManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mAdapter);

        loadjson();
    }

    private void loadjson(){
            pd.setMessage("Mengambil Data");
            pd.setCancelable(false);
            pd.show();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();

                        Log.d("volley", "response :" + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                ModelData md = new ModelData();
                                md.setNamaData(data.getString("nama_data"));

                                mItems.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();

                Log.d("volley", "Error :" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(arrayRequest);
    }
}
