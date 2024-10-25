package com.example.e1_t6_mob_2dam;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

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
    private User user;

    public void checkLogin(User userDB, String passwordIn) throws ErrorWrongPassword, UserNotFound {
       Log.d("pruebabasedatos", userDB.toString());
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
}
