package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.util.ArrayList;

import dao.UserDao;
import dao.WorkoutDao;
import exceptions.UserNotFound;
import exceptions.ErrorWrongPassword;
import objects.Cache;
import objects.User;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Boolean cacheUserExist = false;
        Cache cache = new Cache();

        /**********CACHE*******/
        /*
        try {
            String userCache = cache.get("rememberUser");
            Log.d("entro", userCache);
            for (int i = 0; i < usersList.size(); i++) {
                if (usersList.get(i).getErabiltzailea().equals(userCache)) {
                    GlobalVariables.logedUser = usersList.get(i);
                    cacheUserExist = true;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        */
        /*************/

        if (!cacheUserExist) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, WorkoutsActivity.class);
            startActivity(intent);
            finish();
        }
    }
}