// WorkoutCallBack.java
package Callback;

import java.util.ArrayList;
import objects.Workout;

public interface WorkoutCallBack {
    void onWorkoutsRetrieved(ArrayList<Workout> workouts);
}
