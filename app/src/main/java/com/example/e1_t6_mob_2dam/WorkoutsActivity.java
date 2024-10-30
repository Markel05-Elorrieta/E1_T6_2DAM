package com.example.e1_t6_mob_2dam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import objects.User;

public class WorkoutsActivity extends AppCompatActivity {
    private Functions functions = new Functions();
    private AlertDialog.Builder builder;

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

        // Builder to do the AlertDialogs
        builder = new AlertDialog.Builder(this);

        // Get object from view
        TextView workoutsKopurua = (TextView) findViewById(R.id.tvWorkoutsKopurua);
        ImageButton btnImageProfile = findViewById(R.id.imageButton);
        RecyclerView rvWorkoutList = findViewById(R.id.rvWorkout_list);
        FloatingActionButton atzeraButton = (FloatingActionButton) findViewById(R.id.btnAtzera);

        // Set nombre of workouts in the text
        workoutsKopurua.setText("Workouts kopurua: " + GlobalVariables.workoutsDB.size());

        // Set img of the user in the button
        btnImageProfile.setImageBitmap(GlobalVariables.logedUser.getBitmap());
        btnImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Profile
                Intent intent = new Intent(WorkoutsActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set the table (Adapter) in the ReciclerView (The listener of each workout is insede)
        AdapterWorkoutList adapterWorkoutList = new AdapterWorkoutList(this, GlobalVariables.workoutsDB);
        rvWorkoutList.setLayoutManager(new LinearLayoutManager(this));
        rvWorkoutList.setAdapter(adapterWorkoutList);

        // Listener of the atzera
        atzeraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                functions.alertDisplayWithListener(builder, "Comprobaketa", "Saioa itxiko da, seguro atera nahi duzu?", "Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // When u say continue, Go to LoginActivity
                        Intent intent = new Intent(WorkoutsActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    // If u push in your mobile back, go to previus Activity
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        functions.alertDisplayWithListener(builder, "Comprobaketa", "Saioa itxiko da, seguro atera nahi duzu?", "Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // When u say continue, Go to LoginActivity
                Intent intent = new Intent(WorkoutsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}