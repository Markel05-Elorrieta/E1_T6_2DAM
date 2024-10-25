package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
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

import CallBacks.UserCallBack;
import CallBacks.WorkoutCallBack;
import dao.UserDao;
import dao.WorkoutDao;
import exceptions.ErrorWrongPassword;
import exceptions.UserNotFound;
import objects.Cache;
import objects.User;
import objects.Workout;



public class LoginActivity extends AppCompatActivity {

    private Functions functions = new Functions();
    private UserDao userDao = new UserDao();
    private WorkoutDao workoutDao = new WorkoutDao();
    private Cache cache = new Cache();

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

        Button btnLogin = (Button) findViewById(R.id.btnLogin_login);
        Button btnRegister = (Button) findViewById(R.id.btnLogin_register);
        EditText userIn = (EditText) findViewById(R.id.ptLogin_user);
        EditText passwordIn = (EditText) findViewById(R.id.ptLogin_password);
        CheckBox rememberIn = findViewById(R.id.cbLogin_remember);
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.searchUserDBByUser(userIn.getText().toString(), new UserCallBack() {
                    @Override
                    public void userRetrieved(User userOut) {
                        try {
                            functions.checkLogin(userOut, passwordIn.getText().toString());

                            /** CACHE **/
                            /** Falta guardar el usuario entero en el cache **/
                            /*********************
                            if (rememberIn.isChecked()){
                                Log.d("entro", "entro");
                                cache.put("rememberUser", GlobalVariables.logedUser.getErabiltzailea());
                            }
                            *********************/

                            workoutDao.getWorkouts(new WorkoutCallBack() {
                                @Override
                                public void onWorkoutsRetrieved(ArrayList<Workout> workoutsDB) {
                                    GlobalVariables.workoutsDB = workoutsDB;
                                    Intent intent = new Intent(LoginActivity.this, WorkoutsActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } catch (ErrorWrongPassword | UserNotFound error) {
                            functions.alertDisplay(builder, "Login txarto", error.getMessage(), "Berriro sahiatu");
                        }
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}