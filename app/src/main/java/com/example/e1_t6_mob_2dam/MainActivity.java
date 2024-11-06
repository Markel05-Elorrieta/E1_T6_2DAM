package com.example.e1_t6_mob_2dam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import Callback.UserCallBack;
import Callback.WorkoutCallBack;
import dao.UserDao;
import dao.WorkoutDao;
import objects.User;
import objects.Workout;

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
        WorkoutDao workoutDao = new WorkoutDao();
        UserDao userDao = new UserDao();

        SharedPreferences prefTheme = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
        GlobalVariables.nightMode = prefTheme.getBoolean("NightMode", false);

        if (GlobalVariables.nightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        SharedPreferences sharedPref = getSharedPreferences("cache", Context.MODE_PRIVATE);
        String userSaved = sharedPref.getString("userKey","");

        Log.d("pruebas", userSaved);
        if (userSaved.equals("")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            userDao.searchUserDBByUser(userSaved, new UserCallBack() {
                @Override
                public void userRetrieved(User userOut) {
                    GlobalVariables.logedUser = userOut;

                    workoutDao.getWorkouts(new WorkoutCallBack() {
                        @Override
                        public void onWorkoutsRetrieved(ArrayList<Workout> workoutsDB) {
                            // When u get the workouts from DB do this
                            // Set the workouts in GlobalVariables
                            GlobalVariables.workoutsDB = workoutsDB;
                            // Go to Workouts
                            Intent intent = new Intent( MainActivity.this, WorkoutsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });

        }



    }
}