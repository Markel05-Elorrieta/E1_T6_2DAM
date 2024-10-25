package com.example.e1_t6_mob_2dam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText nameIn = (EditText) findViewById(R.id.etProfile_Name);
        EditText surnameIn = (EditText) findViewById(R.id.etProfile_surname);
        EditText dateIn = (EditText) findViewById(R.id.etProfile_date);
        EditText emailIn = (EditText) findViewById(R.id.etProfile_email);
        EditText phoneIn = (EditText) findViewById(R.id.etProfile_phone);

        nameIn.setText(GlobalVariables.logedUser.getIzena());
        surnameIn.setText(GlobalVariables.logedUser.getAbizenak());
        //nameIn.setText(GlobalVariables.logedUser.getJaiotze_data().);
        emailIn.setText(GlobalVariables.logedUser.getEmail());
        //phoneIn.setText(GlobalVariables.logedUser.getTelefonoa());


        FloatingActionButton btnAtzera = (FloatingActionButton) findViewById(R.id.btnProfile_atzera);
        btnAtzera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, WorkoutsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}