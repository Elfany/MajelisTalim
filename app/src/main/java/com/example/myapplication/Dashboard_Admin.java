package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Dashboard_Admin extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    Button btn_user, btn_majlistaklim;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain_admin);

        btn_user = (Button) findViewById(R.id.btn_user);
        btn_majlistaklim = (Button) findViewById(R.id.btn_majlistaklim);

        Button show_menuadmin = findViewById(R.id.account);
        show_menuadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Dashboard_Admin.this, view);
                popupMenu.setOnMenuItemClickListener(Dashboard_Admin.this);
                popupMenu.inflate(R.menu.menu_admin);
                popupMenu.show();
            }
        });

        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard_Admin.this, UserActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btn_majlistaklim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Dashboard_Admin.this, MajlisTaklim.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.pil1:
                Intent intent = new Intent(Dashboard_Admin.this, LoginActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.pil2:
                signOut();
            break;
        }return true;
    }

        private void signOut () {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Dashboard_Admin.this, "Sign Out Successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        }

    }