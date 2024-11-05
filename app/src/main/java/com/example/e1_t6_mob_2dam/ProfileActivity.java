package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {
    private Functions functions = new Functions();

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

        // Builder to do the AlertDialogs
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get object from view
        EditText etNameIn = (EditText) findViewById(R.id.etProfile_Name);
        EditText etSurnameIn = (EditText) findViewById(R.id.etProfile_surname);
        Button btnPwd = (Button) findViewById(R.id.btnProfile_changePassword);
        EditText etDateIn = (EditText) findViewById(R.id.etProfile_date);
        EditText etEmailIn = (EditText) findViewById(R.id.etProfile_email);
        EditText etPhoneIn = (EditText) findViewById(R.id.etProfile_phone);
        FloatingActionButton btnAtzera = (FloatingActionButton) findViewById(R.id.btnProfile_atzera);
        Button btnGorde = (Button) findViewById(R.id.etProfile_gorde);
        TextView tvErabiltzailea = (TextView) findViewById(R.id.tv_Kaixo_Erabiltzailea);

        // Write in the EditTexts the actual data
        tvErabiltzailea.setText("Kaixo, " + GlobalVariables.logedUser.getErabiltzailea() + "! Hemen zure datuak alda dezakezu.");
        etNameIn.setText(GlobalVariables.logedUser.getIzena());
        etSurnameIn.setText(GlobalVariables.logedUser.getAbizenak());
        String dateString = GlobalVariables.logedUser.getJaiotze_data().getDay() + "/" +
                GlobalVariables.logedUser.getJaiotze_data().getMonth() + "/"
                + (GlobalVariables.logedUser.getJaiotze_data().getYear()+1900);
        etDateIn.setText(dateString);
        etEmailIn.setText(GlobalVariables.logedUser.getEmail());
        etPhoneIn.setText(GlobalVariables.logedUser.getTelefonoa() +"");

        // Listener of the button to change the password
        btnPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AlertDialog to change password
                functions.mostrarDialogoConDosEditText(ProfileActivity.this);
            }
        });

        btnGorde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameIn = etNameIn.getText().toString();
                String surnameIn = etSurnameIn.getText().toString();
                String dateIn = etDateIn.getText().toString();
                String emailIn = etEmailIn.getText().toString();
                String phoneIn = etPhoneIn.getText().toString();


            }
        });

        // Listener of the atzera
        btnAtzera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to WorkoutsActivity
                Intent intent = new Intent(ProfileActivity.this, WorkoutsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}