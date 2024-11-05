package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.HashMap;

import CallBacks.UserCallBack;
import dao.UserDao;
import exceptions.ErrorWrongPassword;
import exceptions.PasswordDoNotMatch;
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

    public void checkRegister (User userDB, String passwordIn, String password2In) throws PasswordDoNotMatch, UserAlreadyExists {
        /** Falta comprobar la fecha, telefono...**/
        if (userDB.getErabiltzailea() != null){
            throw new UserAlreadyExists();
        } else if (!passwordIn.equals(password2In)){
            throw new PasswordDoNotMatch();
        }
    }

    public void insertNewUser(User userNew){
        FirebaseFirestore db = conectionDB.getConnection();

        HashMap<String, Object> userNewHashMap = new HashMap<>();
        userNewHashMap.put("erabiltzailea", userNew.getErabiltzailea());
        userNewHashMap.put("izena", userNew.getIzena());
        userNewHashMap.put("abizenak", userNew.getAbizenak());
        userNewHashMap.put("pasahitza", BCrypt.hashpw(userNew.getPasahitza(), BCrypt.gensalt()));
        userNewHashMap.put("email", userNew.getEmail());
        userNewHashMap.put("maila", userNew.getMaila());
        userNewHashMap.put("telefonoa", userNew.getTelefonoa());
        userNewHashMap.put("jaiotze_data", userNew.getJaiotze_data());
        userNewHashMap.put("argazkia", userNew.getimgBase64());

        db.collection("erabiltzaileak").add(userNewHashMap).addOnSuccessListener(documentReference -> {
                    Log.d("Firestore", "DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error adding document", e);
                });

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
        builder.setTitle("Ingrese los datos");

        // Crear un LinearLayout para contener los EditText
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10); // Ajusta el padding si deseas espaciar el contenido

        final EditText input = new EditText(context);
        input.setHint("Pasahitza oraingoa"); // Hint para el primer campo de texto
        layout.addView(input); // Agregar el primer EditText al layout

        // Crear el primer EditText
        final EditText input1 = new EditText(context);
        input1.setHint("Pasahitza berria"); // Hint para el primer campo de texto
        layout.addView(input1); // Agregar el primer EditText al layout

        // Crear el segundo EditText
        final EditText input2 = new EditText(context);
        input2.setHint("Pasahitza errepikatu"); // Hint para el segundo campo de texto
        layout.addView(input2); // Agregar el segundo EditText al layout

        // TextView para mostrar el mensaje de error
        final TextView errorTextView = new TextView(context);
        errorTextView.setText(""); // Inicialmente vacío
        errorTextView.setTextColor(Color.RED); // Color para el texto de error
        layout.addView(errorTextView); // Agregar el TextView al layout

        // Configurar el layout en el AlertDialog
        builder.setView(layout);

        // Configurar los botones
        builder.setPositiveButton("Aceptar", null); // Usar null para evitar el cierre automático

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

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
                if (BCrypt.checkpw(textoIngresado, GlobalVariables.logedUser.getPasahitza())) {
                    if (!textoIngresado1.equals(textoIngresado2)) {
                        errorTextView.setText("Las contraseñas no coinciden.");
                        errorTextView.requestLayout(); // Forzar la actualización de la vista
                    } else if (BCrypt.checkpw(textoIngresado1, GlobalVariables.logedUser.getPasahitza())) {
                        errorTextView.setText("La nueva contraseña no puede ser igual a la actual.");
                        errorTextView.requestLayout(); // Forzar la actualización de la vista
                    } else {
                        // Actualizar la contraseña en la base de datos
                        errorTextView.setText("Se cambio la contraseña correctamente");
                        errorTextView.setTextColor(Color.GREEN);
                        errorTextView.requestLayout();
                        userDao.updatePwd(textoIngresado1);
                        GlobalVariables.logedUser.setPasahitza(BCrypt.hashpw(textoIngresado1, BCrypt.gensalt()));
                        dialog.dismiss(); // Cerrar el diálogo si la contraseña se actualiza
                    }
                } else {
                    errorTextView.setText("La contraseña actulal no coincide");
                    errorTextView.requestLayout(); // Forzar la actualización de la vista
                }

            });
        });
        // Mostrar el diálogo
        dialog.show();// O devuelve un valor adecuado según tu lógica
    }



}
