package com.example.myapplication;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.login.LoginActivity;

public class MajlisTaklim extends AppCompatActivity {
    Button btn_inputtaklim;

    ConnectivityManager conMgr;

    private String url = Server.URL + "majlistaklim.php";

    private static final String TAG = MajlisTaklim.class.getSimpleName();
    private static final  String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String tag_json_obj = "json_obj_req";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.majlistaklim);

        btn_inputtaklim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MajlisTaklim.this, Input_MajlisTaklim.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
