package dao;

import android.util.Log;

import com.example.e1_t6_mob_2dam.ConectionDB;
import com.example.e1_t6_mob_2dam.GlobalVariables;
import Callback.UserCallBack;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import objects.User;

public class UserDao {
    private ConectionDB conectionDB = new ConectionDB();

    public void searchUserDBByUser(String userIn, UserCallBack userCallBack){
        FirebaseFirestore db = conectionDB.getConnection();

        db.collection("erabiltzaileak").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AtomicInteger completedTasks = new AtomicInteger(0);
                int totalTasks = task.getResult().size();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getString("erabiltzailea").equals(userIn)){
                        User userDB = new User(
                                document.getId(),
                                document.getString("izena"),
                                document.getString("abizenak"),
                                document.getString("erabiltzailea"),
                                document.getString("pasahitza"),
                                document.getDate("jaiotze_data"),
                                document.getString("email"),
                                document.getDouble("telefonoa").intValue(),
                                document.getDouble("maila").intValue(),
                                document.getString("argazkia")
                        );
                        userCallBack.userRetrieved(userDB);
                        break;
                    }
                    if (completedTasks.incrementAndGet() == totalTasks) {
                        User userDB = new User();
                        userCallBack.userRetrieved(userDB);
                    }
                }
            } else {
                User userDB = new User();
                userCallBack.userRetrieved(userDB);
            }
        });
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

    public void updatePwd(String newPdw) {
        FirebaseFirestore db = conectionDB.getConnection();
        db.collection("erabiltzaileak").document(GlobalVariables.logedUser.getId())
                .update("pasahitza", BCrypt.hashpw(newPdw, BCrypt.gensalt()))
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firestore", "DocumentSnapshot successfully updated!");
                })
                .addOnFailureListener(e -> {
                    Log.w("Firestore", "Error updating document", e);
                });
    }

    public void updateUser (User userUpdate) {
        Log.d("UpdateDVB", userUpdate.toString());
        userUpdate.setId(GlobalVariables.logedUser.getId());
        userUpdate.setMaila(GlobalVariables.logedUser.getMaila());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("erabiltzaileak").document(GlobalVariables.logedUser.getId())
                .update(
                        "izena", userUpdate.getIzena(),
                        "abizenak", userUpdate.getAbizenak(),
                        "jaiotze_data", userUpdate.getJaiotze_data(),
                        "email", userUpdate.getEmail(),
                        "telefonoa", userUpdate.getTelefonoa()
                ).addOnSuccessListener(aVoid -> {
                    GlobalVariables.logedUser = userUpdate;
                })
                .addOnFailureListener(e -> {
                    // Failed to update user data

                });

    }
}
