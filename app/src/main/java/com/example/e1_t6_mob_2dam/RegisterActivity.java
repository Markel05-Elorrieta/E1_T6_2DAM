package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import Callback.UserCallBack;
import dao.UserDao;
import exceptions.NullField;
import exceptions.PasswordDoNotMatch;
import exceptions.UserAlreadyExists;
import objects.User;

public class RegisterActivity extends AppCompatActivity {
    Functions functions = new Functions();
    private UserDao userDao = new UserDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registerView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /*-----------------------GET VIEW OBJECTS-----------------------*/
        // Builder to do the AlertDialogs
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Get object from view
        EditText nameIn = (EditText) findViewById(R.id.ptRegister_name);
        EditText surnameIn = (EditText) findViewById(R.id.ptRegister_surname);
        EditText userIn = (EditText) findViewById(R.id.ptRegister_user);
        EditText passwordIn = (EditText) findViewById(R.id.ptRegister_password);
        EditText password2In = (EditText) findViewById(R.id.ptRegister_password2);
        EditText dateIn = (EditText) findViewById(R.id.dtRegister_date);
        EditText emailIn = (EditText) findViewById(R.id.etRegister_email);
        EditText phoneIn = (EditText) findViewById(R.id.ptRegister_phone);
        FloatingActionButton btnAtzera = (FloatingActionButton) findViewById(R.id.btnRegister_atzera);
        Button btnRegister = (Button) findViewById(R.id.btnRegister_register);

        /*-----------------------REGISTER BUTTON-----------------------*/
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text that user put
                String txtName = nameIn.getText().toString();
                String txtSurname = surnameIn.getText().toString();
                String txtUser = userIn.getText().toString();
                String txtPassword = passwordIn.getText().toString();
                String txtPassword2 = passwordIn.getText().toString();
                String txtDate = dateIn.getText().toString();
                String txtEmail = emailIn.getText().toString();
                String txtPhone = phoneIn.getText().toString();

                try {
                    // Check if is a field empty
                    if (txtName.equals("") || txtSurname.equals("") ||txtUser.equals("") || txtPassword.equals("") ||
                            txtPassword2.equals("") || txtDate.equals("") ||txtEmail.equals("") || txtPhone.equals("")){
                        throw new NullField();
                    }

                    // Call DB to search the user
                    userDao.searchUserDBByUser(userIn.getText().toString().toLowerCase(), new UserCallBack() {
                        @Override
                        public void userRetrieved(User userOut) {
                            // When u get the user from DB do this
                            try {
                                // Check if u can register with the info user provide
                                functions.checkRegister(userOut, passwordIn.getText().toString(),password2In.getText().toString());

                                // If is here is because the info is correct and can register
                                // Create an user to insert in DB
                                Date d = new Date(txtDate);
                                User userNew = new User(txtName, txtSurname, txtUser.toLowerCase(), txtPassword, d, txtEmail, Integer.parseInt(txtPhone));
                                functions.insertNewUser(userNew);

                                // All done, show Alert saying is register
                                functions.alertDisplayWithListener(builder, "Erregistro ondo", "Erabiltzailea ondo sortu da!", "Hurrengoa", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // When u say continue, Go to LoginActivity
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } catch (PasswordDoNotMatch | UserAlreadyExists error) {
                                // Alert display for errors
                                functions.alertDisplay(builder, "Erregistro txarto", error.getMessage(), "Berriro sahiatu");
                            }
                        }
                    });
                } catch (NullField |  NumberFormatException error) {
                    // Alert display for errors
                    functions.alertDisplay(builder, "Erregistro txarto", error.getMessage(), "Berriro sahiatu");
                }
            }
        });

        /*-----------------------BACK BUTTON-----------------------*/
        btnAtzera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /*-----------------------BACK MOBILE-----------------------*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}