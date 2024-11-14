package com.example.e1_t6_mob_2dam;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

import objects.Ariketa;

public class WorkoutInfoActivity extends AppCompatActivity {
    private Functions functions = new Functions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        GlobalVariables.context = this;
        // Builder to do the AlertDialogs
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get object from view
        TextView txtWorkoutName = (TextView) findViewById(R.id.tvWorkoutName);
        txtWorkoutName.setText(GlobalVariables.currentWorkout.getIzena());
        TextView txtWorkoutMaila = (TextView) findViewById(R.id.tvWorkoutMaila);
        txtWorkoutMaila.setText(getString(R.string.txt_columnLvl) + " " + GlobalVariables.currentWorkout.getMaila());
        WebView wbYoutubeVideo = findViewById(R.id.web);
        RecyclerView rvAriketakList = findViewById(R.id.rvAriketak_list);

        Button btnAtzera = (Button) findViewById(R.id.btnWorkoutInfo_back);

        // Settings for the webview
        WebSettings webSettings = wbYoutubeVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wbYoutubeVideo.loadUrl(GlobalVariables.currentWorkout.getVideoURL());

        AdapterAriketaList adapterAriketaList = new AdapterAriketaList(this, GlobalVariables.ariketakDB);
        rvAriketakList.setLayoutManager(new LinearLayoutManager(this));
        rvAriketakList.setAdapter(adapterAriketaList);

        // Listener of the Atzera
        btnAtzera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to Workouts
                Intent intent = new Intent(WorkoutInfoActivity.this, WorkoutsActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    // If u push in your mobile back, go to previus Activity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WorkoutInfoActivity.this, WorkoutsActivity.class);
        startActivity(intent);
        finish();
    }
}