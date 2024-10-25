package com.example.e1_t6_mob_2dam;

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
        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setImageBitmap(GlobalVariables.logedUser.getBitmap());
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutsActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView workoutsKopurua = (TextView) findViewById(R.id.tvWorkoutsKopurua);
        workoutsKopurua.setText("Workouts kopurua: " + GlobalVariables.workoutsDB.size());

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


    }
}