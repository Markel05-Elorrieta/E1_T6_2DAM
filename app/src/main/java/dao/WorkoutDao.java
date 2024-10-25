package dao;

import android.util.Log;

import com.example.e1_t6_mob_2dam.ConectionDB;
import com.example.e1_t6_mob_2dam.GlobalVariables;
import CallBacks.WorkoutCallBack; // Ensure you have this interface defined
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import objects.Workout;

public class WorkoutDao {
    private ConectionDB conectionDB = new ConectionDB();

    public void getWorkouts(WorkoutCallBack callback) { // Accepting a callback
        FirebaseFirestore db = conectionDB.getConnection();

        db.collection("workouts").get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                ArrayList<Workout> workoutsDB = new ArrayList<>();
                AtomicInteger completedTasks = new AtomicInteger(0);
                int totalTasks = task.getResult().size();

                if (totalTasks == 0) {
                    Log.d("WorkoutDao", "No workouts found.");
                    callback.onWorkoutsRetrieved(workoutsDB);
                    return;
                }

                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document.getDouble("maila") <= GlobalVariables.logedUser.getMaila()) {
                        Workout workout = new Workout();
                        workout.setIzena(document.getString("izena"));
                        workout.setMaila(document.getDouble("maila").intValue());
                        workout.setVideoURL(document.getString("video_url"));
                        ArrayList<String> idList = (ArrayList<String>) document.get("ariketasID");
                        workout.setAriketakId(idList);
                        workoutsDB.add(workout);
                    }

                    if (completedTasks.incrementAndGet() == totalTasks) {
                        callback.onWorkoutsRetrieved(workoutsDB);
                    }
                }
            } else {
                Log.d("DBError", "Error getting workouts: ", task.getException());
                callback.onWorkoutsRetrieved(null);
            }
        });
    }

}
