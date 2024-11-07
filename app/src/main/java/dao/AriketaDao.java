package dao;

import android.util.Log;

import com.example.e1_t6_mob_2dam.ConectionDB;
import com.example.e1_t6_mob_2dam.GlobalVariables;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import Callback.AriketaCallBack;
import Callback.WorkoutCallBack;
import objects.Ariketa;
import objects.Workout;

public class AriketaDao {
    private ConectionDB conectionDB = new ConectionDB();

    public void getAriketakByArrayId(ArrayList<String> ariketakId, AriketaCallBack callback) { // Accepting a callback
        FirebaseFirestore db = conectionDB.getConnection();

        db.collection("ariketak").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                ArrayList<Ariketa> ariketakDB = new ArrayList<>();

                AtomicInteger completedTasks = new AtomicInteger(0);
                int totalTasks = task.getResult().size();

                if (totalTasks == 0) {
                    Log.d("WorkoutDao", "No workouts found.");
                    callback.onAriketakRetrieved(ariketakDB);
                    return;
                }
                Log.d("sacararray", totalTasks + "");

                for (QueryDocumentSnapshot document : task.getResult()) {
                    for (String ariketaID : ariketakId) {
                        Log.d("sacararray", document.getId() + "//" + ariketaID);
                        if (document.getId().equals(ariketaID)) {
                            Ariketa ariketa = new Ariketa();
                            ariketa.setName(document.getString("izena"));
                            ariketa.setDescription(document.getString("deskribapena"));
                            ariketa.setWorkedMuscle(document.getString("landutako_muskulua"));
                            ariketa.setVideoUrl(document.getString("video_url"));
                            ariketa.setDenbora(document.getDouble("denbora"));
                            Log.d("sacararray", ariketa.toString());
                            ariketakDB.add(ariketa);
                        }

                        if (completedTasks.incrementAndGet() == totalTasks) {
                            callback.onAriketakRetrieved(ariketakDB);
                        }
                    }
                }
            } else {
                Log.d("DBError", "Error getting workouts: ", task.getException());
                callback.onAriketakRetrieved(null);
            }
        });
    }
}
