package com.example.e1_t6_mob_2dam;

import static com.example.e1_t6_mob_2dam.R.id.btnAtzera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import dao.WorkoutDao;
import objects.User;
import objects.Workout;

public class WorkoutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workouts);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.workoutView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rv = findViewById(R.id.rvWorkout_list);
        AdapterWorkoutList a = new AdapterWorkoutList(this, GlobalVariables.workoutsDB);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(a);


        FloatingActionButton atzeraButton = (FloatingActionButton) findViewById(R.id.btnAtzera);
        atzeraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables.logedUser = new User();

                Intent intent = new Intent(WorkoutsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

/*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login txarto");
        builder.setMessage(GlobalVariables.logedUser.getErabiltzailea() + ";" +GlobalVariables.logedUser.getMaila());
        builder.setPositiveButton("Berriro sahiatu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();*/
    }
}