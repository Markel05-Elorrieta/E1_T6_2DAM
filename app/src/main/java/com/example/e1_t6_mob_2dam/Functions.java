package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.inputmethod.InsertGesture;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatException;

import dao.UserDao;
import exceptions.EmailFormatError;
import exceptions.ErrorWrongPassword;
import exceptions.IlogicalDate;
import exceptions.PasswordDoNotMatch;
import exceptions.PhoneFormatError;
import exceptions.UserAlreadyExists;
import exceptions.UserNotFound;
import objects.User;


public class Functions {
    public static ArrayList<User> users = new ArrayList<>();
    private ConectionDB conectionDB = new ConectionDB();
    private UserDao userDao = new UserDao();
    private User user;

    public void checkLogin(User userDB, String passwordIn) throws ErrorWrongPassword, UserNotFound {
        if (userDB.getErabiltzailea() == null) {
            throw new UserNotFound();
        }
        else if (!BCrypt.checkpw(passwordIn, userDB.getPasahitza())) {
            throw new ErrorWrongPassword();
        } else {
            GlobalVariables.logedUser = userDB;
        }
    }

    public void checkRegister (User userDB, String passwordIn, String password2In, String emailIn, String phoneIn) throws PasswordDoNotMatch, UserAlreadyExists, EmailFormatError, PhoneFormatError, NumberFormatException {
        if (userDB.getErabiltzailea() != null) {
            throw new UserAlreadyExists();
        }
        int phoneNum = Integer.parseInt(phoneIn);
        if (!emailIn.contains("@") || !emailIn.contains(".")) {
            throw new EmailFormatError();
        } else if (phoneNum < 0 || phoneNum > 999999999 || phoneIn.length() != 9){
            throw new PhoneFormatError();
        } else if (!passwordIn.equals(password2In)){
            throw new PasswordDoNotMatch();
        }
    }

    public Date checkDate (String txtDate) throws IlogicalDate {
        Log.d("fecharara", txtDate);
        String[] dates = txtDate.split("/");
        try {
            if (dates[0].length() > 2 || dates[1].length() > 2 || dates[2].length() > 4) {
                throw new IlogicalDate();
            }

            Date currentDate = new Date();
            int day = Integer.parseInt(dates[0]);
            int month = Integer.parseInt(dates[1]);
            int year = Integer.parseInt(dates[2]);

            Log.d("fecharara", day + "&" + month + "&" + year);
            Log.d("fecharara", currentDate.getYear() + "");
            if (day > 31 || month > 12 || year > (currentDate.getYear()+1900)) {
                throw new IlogicalDate();
            }
            Date dateIn = new Date((year-1900), month-1, day);

            Log.d("fecharara", dateIn.getDate() + "&" + dateIn.getMonth() + "&" + dateIn.getYear());

            if (dateIn.after(currentDate)){
                throw new IlogicalDate();
            }

            return dateIn;
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new IlogicalDate();
        }

    }

    public void alertDisplay(AlertDialog.Builder builder, String title, String msg, String msgBtn){
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(msgBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void alertDisplayWithListener(AlertDialog.Builder builder, String title, String msg, String msgBtn, DialogInterface.OnClickListener listener){
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(msgBtn, listener);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void mostrarDialogoConDosEditText(Context context) {
        // Crear el AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.txt_btnPasswordChange));

        // Crear un LinearLayout para contener los EditText
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10); // Ajusta el padding si deseas espaciar el contenido

        final EditText input = new EditText(context);
        input.setHint(context.getString(R.string.txt_lblCurrentPassword)); // Hint para el primer campo de texto
        layout.addView(input); // Agregar el primer EditText al layout

        // Crear el primer EditText
        final EditText input1 = new EditText(context);
        input1.setHint(context.getString(R.string.txt_lblnewPassword)); // Hint para el primer campo de texto
        layout.addView(input1); // Agregar el primer EditText al layout

        // Crear el segundo EditText
        final EditText input2 = new EditText(context);
        input2.setHint(context.getString(R.string.txt_lblnewPasswordRepeat)); // Hint para el segundo campo de texto
        layout.addView(input2); // Agregar el segundo EditText al layout

        // TextView para mostrar el mensaje de error
        final TextView errorTextView = new TextView(context);
        errorTextView.setText(""); // Inicialmente vacío
        errorTextView.setTextColor(Color.RED); // Color para el texto de error
        layout.addView(errorTextView); // Agregar el TextView al layout

        // Configurar el layout en el AlertDialog
        builder.setView(layout);

        // Configurar los botones
        builder.setPositiveButton(context.getString(R.string.txt_btnSave), null); // Usar null para evitar el cierre automático

        // Crear el AlertDialog
        AlertDialog dialog = builder.create();

        // Establecer un listener para el botón "Aceptar"
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(v -> {
                // Obtener los valores ingresados en los EditTexts
                String textoIngresado = input.getText().toString();
                String textoIngresado1 = input1.getText().toString();
                String textoIngresado2 = input2.getText().toString();

                // Limpiar el mensaje de error
                errorTextView.setText("");

                // Validar los datos
                if (textoIngresado.isEmpty() || textoIngresado1.isEmpty() || textoIngresado2.isEmpty()) {
                    errorTextView.setText(context.getString(R.string.txt_NullField));
                    errorTextView.requestLayout(); // Forzar la actualización de la vista
                } else {
                    if (BCrypt.checkpw(textoIngresado, GlobalVariables.logedUser.getPasahitza())) {
                        if (!textoIngresado1.equals(textoIngresado2)) {
                            errorTextView.setText(context.getString(R.string.txt_PasswordDoNotMatch));
                            errorTextView.requestLayout(); // Forzar la actualización de la vista
                        } else if (BCrypt.checkpw(textoIngresado1, GlobalVariables.logedUser.getPasahitza())) {
                            errorTextView.setText(context.getString(R.string.txt_PasswordCurrentSameNew));
                            errorTextView.requestLayout(); // Forzar la actualización de la vista
                        } else {
                            // Actualizar la contraseña en la base de datos
                            userDao.updatePwd(textoIngresado1);
                            GlobalVariables.logedUser.setPasahitza(BCrypt.hashpw(textoIngresado1, BCrypt.gensalt()));
                            dialog.dismiss(); // Cerrar el diálogo si la contraseña se actualiza
                        }
                    } else {
                        errorTextView.setText(context.getString(R.string.txt_CurrentPasswordError));
                        errorTextView.requestLayout(); // Forzar la actualización de la vista
                    }
                }
            });
        });
        // Mostrar el diálogo
        dialog.show();// O devuelve un valor adecuado según tu lógica
    }
}
