package com.example.e1_t6_mob_2dam;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import objects.Workout;

public class AdapterWorkoutList extends RecyclerView.Adapter<AdapterWorkoutList.WorkoutViewHolder> {

    private Context context;
    private ArrayList<Workout> workoutList;

    // Constructor
    public AdapterWorkoutList(Context context, ArrayList<Workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
    }

    // ViewHolder class that describes each item's view
    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvNumAriketak, tvMaila;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvList_name);
            tvNumAriketak = itemView.findViewById(R.id.tvList_numAriketak);
            tvMaila = itemView.findViewById(R.id.tvList_maila);
        }
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(context).inflate(R.layout.workoutlistformat, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        // Get the current workout from the list
        Workout currentWorkout = workoutList.get(position);

        // Set the values for each TextView
        holder.tvName.setText(currentWorkout.getIzena());
        holder.tvNumAriketak.setText(String.valueOf(currentWorkout.getAriketaCount()));  // Number of exercises
        holder.tvMaila.setText(String.valueOf(currentWorkout.getMaila()));  // Difficulty level

        // Set click listener for the item
        holder.itemView.setOnClickListener(view -> {
            Log.d("Workout Clicked", "Workout clicked: " + currentWorkout.getIzena());
            Intent intent = new Intent(context, WorkoutInfoActivity.class);
            GlobalVariables.currentWorkout = currentWorkout;
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();  // Return the total number of items in the list
    }
}
