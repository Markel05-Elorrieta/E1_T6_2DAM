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
import java.util.HashMap;

import CallBacks.UserCallBack;
import dao.UserDao;
import exceptions.NullField;
import exceptions.PasswordDoNotMatch;
import exceptions.UserAlreadyExists;
import objects.User;

public class RegisterActivity extends AppCompatActivity {
    private UserDao userDao = new UserDao();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

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

        Functions functions = new Functions();

        EditText nameIn = (EditText) findViewById(R.id.ptRegister_name);
        EditText surnameIn = (EditText) findViewById(R.id.ptRegister_surname);
        EditText userIn = (EditText) findViewById(R.id.ptRegister_user);
        EditText passwordIn = (EditText) findViewById(R.id.ptRegister_password);
        EditText password2In = (EditText) findViewById(R.id.ptRegister_password2);
        EditText dateIn = (EditText) findViewById(R.id.dtRegister_date);
        EditText emailIn = (EditText) findViewById(R.id.etRegister_email);
        EditText phoneIn = (EditText) findViewById(R.id.ptRegister_phone);
        Button btnRegister = (Button) findViewById(R.id.btnRegister_register);
        FloatingActionButton btnAtzera = (FloatingActionButton) findViewById(R.id.btnRegister_atzera);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = nameIn.getText().toString();
                String txtSurname = surnameIn.getText().toString();
                String txtUser = userIn.getText().toString();
                String txtPassword = passwordIn.getText().toString();
                String txtPassword2 = passwordIn.getText().toString();
                String txtDate = dateIn.getText().toString();
                String txtEmail = emailIn.getText().toString();
                String txtPhone = phoneIn.getText().toString();

                try {
                    if (txtName.equals("") || txtSurname.equals("") ||txtUser.equals("") || txtPassword.equals("") ||
                            txtPassword2.equals("") || txtDate.equals("") ||txtEmail.equals("") || txtPhone.equals("")){
                        throw new NullField();
                    }

                    userDao.searchUserDBByUser(userIn.getText().toString(), new UserCallBack() {
                        @Override
                        public void userRetrieved(User userOut) {
                            try {
                                functions.checkRegister(userOut, passwordIn.getText().toString(),password2In.getText().toString());

                                Date d = new Date(txtDate);
                                User userNew = new User(txtName, txtSurname, txtUser, txtPassword, d, txtEmail, Integer.parseInt(txtPhone));
                                functions.insertNewUser(userNew);

                                functions.alertDisplayWithListener(builder, "Erregistro ondo", "Erabiltzailea ondo sortu da!", "Hurrengoa", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } catch (PasswordDoNotMatch | UserAlreadyExists error) {
                                functions.alertDisplay(builder, "Erregistro txarto", error.getMessage(), "Berriro sahiatu");
                            }
                        }
                    });
                } catch (NullField nullField) {
                    functions.alertDisplay(builder, "Erregistro txarto", nullField.getMessage(), "Berriro sahiatu");
                } catch (NumberFormatException numberFormatException) {
                    functions.alertDisplay(builder, "Erregistro txarto", "Telefonoa ez dago ondo jarrita" , "Berriro sahiatu");
                }

            }
        });

        btnAtzera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}