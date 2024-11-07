package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.Locale;

import dao.UserDao;
import exceptions.NullField;
import objects.User;

public class ProfileActivity extends AppCompatActivity {
    private Functions functions = new Functions();
    private UserDao userDao = new UserDao();

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

        SharedPreferences prefTheme = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefTheme.edit();
        boolean isNightMode = prefTheme.getBoolean("NightMode", false);


        // Builder to do the AlertDialogs
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get object from view
        EditText etNameIn = (EditText) findViewById(R.id.etProfile_Name);
        EditText etSurnameIn = (EditText) findViewById(R.id.etProfile_surname);
        Button btnPwd = (Button) findViewById(R.id.btnProfile_changePassword);
        EditText etDateIn = (EditText) findViewById(R.id.etProfile_date);
        EditText etEmailIn = (EditText) findViewById(R.id.etProfile_email);
        EditText etPhoneIn = (EditText) findViewById(R.id.etProfile_phone);
        Switch sTheme =  (Switch) findViewById(R.id.sProfile_Theme);
        if(isNightMode){
            sTheme.setChecked(true);
        }
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_lenguaje);
        FloatingActionButton btnAtzera = (FloatingActionButton) findViewById(R.id.btnProfile_atzera);
        FloatingActionButton btnItxi = (FloatingActionButton) findViewById(R.id.fbtn_Logout);
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


        String[] languages = {"English", "Spanish", "French", "German", "Chinese"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3) {
                    Log.d("entroalspinner", "entro");
                    setLocaleeeeee("es");
                    mySpinner.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



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

                try {
                    if (nameIn.equals("") || surnameIn.equals("") || dateIn.equals("") || emailIn.equals("") || phoneIn.equals("")) {
                        throw new NullField();
                    }
                    Date d = new Date(dateIn);
                    User userUpdate = new User(nameIn, surnameIn, GlobalVariables.logedUser.getErabiltzailea(), GlobalVariables.logedUser.getPasahitza(),
                            d, emailIn, Integer.parseInt(phoneIn));

                    userDao.updateUser(userUpdate);

                    Intent intent = new Intent(ProfileActivity.this, WorkoutsActivity.class);
                    startActivity(intent);
                    finish();

                } catch (NullField | NumberFormatException e) {
                    functions.alertDisplay(builder, "Error", e.getMessage(), "Berriro sahiatu!");
                }

            }
        });


        if (isNightMode) {
            sTheme.setText("Iluna");
        } else {
            sTheme.setText("Argia");
        }
        sTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("NightMode", true);
                    sTheme.setText("Iluna");
                    editor.apply();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("NightMode", false);
                    sTheme.setText("Argia");
                    editor.apply();
                }
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

        btnItxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to Login Activity
                SharedPreferences sharedPref = getSharedPreferences("cache", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("userKey", "");
                editor.apply();

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setLocaleeeeee(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        recreate();
    }
}