package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import Callback.UserCallBack;
import Callback.WorkoutCallBack;
import dao.UserDao;
import dao.WorkoutDao;
import exceptions.ErrorWrongPassword;
import exceptions.UserNotFound;
import objects.User;
import objects.Workout;

public class LoginActivity extends AppCompatActivity {
    private Functions functions = new Functions();
    private UserDao userDao = new UserDao();
    private WorkoutDao workoutDao = new WorkoutDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loginView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GlobalVariables.context = this;

        // Builder to do the AlertDialogs
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        // Get object from view
        EditText userIn = (EditText) findViewById(R.id.ptLogin_user);
        EditText passwordIn = (EditText) findViewById(R.id.ptLogin_password);
        CheckBox miCheckBox = findViewById(R.id.cbLogin_saveUser);
        Button btnLogin = (Button) findViewById(R.id.btnLogin_login);
        Button btnRegister = (Button) findViewById(R.id.btnLogin_register);

        /*-----------------------LOGIN BUTTON-----------------------*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setEnabled(false);
                btnRegister.setEnabled(false);
                // Call DB to search user
                userDao.searchUserDBByUser(userIn.getText().toString().toLowerCase(), new UserCallBack() {
                    @Override
                    public void userRetrieved(User userOut) {
                        // When u get the user from DB do this
                        try {
                            // Check the user and password are correct with the user of DB
                            // If is OK userOK id in GlobalVariables
                            functions.checkLogin(userOut, passwordIn.getText().toString());

                            if (miCheckBox.isChecked()){
                                SharedPreferences sharedPref = getSharedPreferences("cache", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("userKey", GlobalVariables.logedUser.getErabiltzailea());
                                editor.apply();
                            }

                            // Call DB to take the workouts of the user
                            workoutDao.getWorkouts(new WorkoutCallBack() {
                                @Override
                                public void onWorkoutsRetrieved(ArrayList<Workout> workoutsDB) {
                                    // When u get the workouts from DB do this
                                    // Set the workouts in GlobalVariables
                                    GlobalVariables.workoutsDB = workoutsDB;
                                    // Go to Workouts
                                    Intent intent = new Intent(LoginActivity.this, WorkoutsActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } catch (ErrorWrongPassword | UserNotFound error) {
                            // Login txarto atera alerta bat
                            btnLogin.setEnabled(true);
                            btnRegister.setEnabled(true);
                            functions.alertDisplay(builder, getString(R.string.txt_LoginError), error.getMessage(), getString(R.string.txt_TryAgain));
                        }
                    }
                });
            }
        });

        /*-----------------------REGISTER BUTTON-----------------------*/
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Register
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}