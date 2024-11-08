package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

        /*-----------------------GET VIEW OBJECTS-----------------------*/
        GlobalVariables.context = this;
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
        Spinner mySpinner = (Spinner) findViewById(R.id.spinner_lenguaje);
        FloatingActionButton btnAtzera = (FloatingActionButton) findViewById(R.id.btnProfile_atzera);
        FloatingActionButton btnItxi = (FloatingActionButton) findViewById(R.id.fbtn_Logout);
        Button btnGorde = (Button) findViewById(R.id.etProfile_gorde);
        TextView tvErabiltzailea = (TextView) findViewById(R.id.tv_Kaixo_Erabiltzailea);

        /*-----------------------PUT USER INFO-----------------------*/
        // Write in the EditTexts the actual data
        tvErabiltzailea.setText(getString(R.string.txt_lblHello) + GlobalVariables.logedUser.getErabiltzailea() + getString(R.string.txt_lblProfileInfo));
        etNameIn.setText(GlobalVariables.logedUser.getIzena());
        etSurnameIn.setText(GlobalVariables.logedUser.getAbizenak());
        String dateString = GlobalVariables.logedUser.getJaiotze_data().getDay() + "/" +
                GlobalVariables.logedUser.getJaiotze_data().getMonth() + "/"
                + (GlobalVariables.logedUser.getJaiotze_data().getYear()+1900);
        etDateIn.setText(dateString);
        etEmailIn.setText(GlobalVariables.logedUser.getEmail());
        etPhoneIn.setText(GlobalVariables.logedUser.getTelefonoa() +"");

        /*-----------------------CHECK APP THEME AND PUT INFO-----------------------*/
        SharedPreferences prefTheme = getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefTheme.edit();
        boolean isNightMode = prefTheme.getBoolean("NightMode", false);
        if(isNightMode){
            sTheme.setChecked(true);
        }

        /*-----------------------SPINNER LENGUAJE-----------------------*/
        String[] languages = {getString(R.string.txt_lenguajeNone), getString(R.string.txt_lenguajeEnglish), getString(R.string.txt_lenguajeSpanish), getString(R.string.txt_lenguajeBasque), getString(R.string.txt_lenguajePolish)};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        /*-----------------------DO WHEN CLICK LENGUAJE-----------------------*/
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                } else if (position == 1) {
                    GlobalVariables.lenguaje = "en";
                    setLocale("en");
                    functions.alertDisplayWithListener(builder, getString(R.string.txt_NeedToConfirmAlert), getString(R.string.txt_LenguajeChangeAlert), getString(R.string.txt_YesAlert), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mySpinner.setSelection(0);
                            recreate();
                        }
                    });
                } else if (position == 2) {
                    GlobalVariables.lenguaje = "es";
                    setLocale("es");
                    functions.alertDisplayWithListener(builder, getString(R.string.txt_NeedToConfirmAlert), getString(R.string.txt_LenguajeChangeAlert), getString(R.string.txt_YesAlert), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mySpinner.setSelection(0);
                            recreate();
                        }
                    });
                } else if (position == 3) {
                    GlobalVariables.lenguaje = "eu";
                    setLocale("eu");
                    functions.alertDisplayWithListener(builder, getString(R.string.txt_NeedToConfirmAlert), getString(R.string.txt_LenguajeChangeAlert), getString(R.string.txt_YesAlert), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mySpinner.setSelection(0);
                            recreate();
                        }
                    });
                } else if (position == 4) {
                    GlobalVariables.lenguaje = "pl";
                    setLocale("pl");
                    functions.alertDisplayWithListener(builder, getString(R.string.txt_NeedToConfirmAlert), getString(R.string.txt_LenguajeChangeAlert), getString(R.string.txt_YesAlert), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mySpinner.setSelection(0);
                            recreate();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        /*-----------------------CHANGE PASSWORD BUTTON-----------------------*/
        // Listener of the button to change the password
        btnPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AlertDialog to change password
                functions.mostrarDialogoConDosEditText(ProfileActivity.this);
            }
        });

        /*-----------------------SAVE BUTTON-----------------------*/
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
                    functions.alertDisplay(builder, "Error", e.getMessage(), getString(R.string.txt_TryAgain));
                }

            }
        });


        if (isNightMode) {
            sTheme.setText(getString(R.string.txt_DarkTheme));
        } else {
            sTheme.setText(getString((R.string.txt_ClearTheme)));
        }
        sTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("NightMode", true);
                    sTheme.setText(getString(R.string.txt_DarkTheme));
                    editor.apply();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("NightMode", false);
                    sTheme.setText(getString((R.string.txt_ClearTheme)));
                    editor.apply();
                }
            }
        });

        /*-----------------------BACK BUTTON-----------------------*/
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

        /*-----------------------SESSION CLOSE-----------------------*/
        btnItxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to Login Activity
                functions.alertDisplayWithListener(builder, getString(R.string.txt_NeedToConfirmAlert), getString(R.string.txt_LogOutAlert), getString(R.string.txt_YesAlert), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // When u say continue, Go to LoginActivity
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
        });
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
    }
}