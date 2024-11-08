package com.example.e1_t6_mob_2dam;

import android.content.Context;

import org.checkerframework.checker.units.qual.A;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

import objects.Ariketa;
import objects.User;
import objects.Workout;

public class GlobalVariables {
    public static User logedUser = new User();
    public static ArrayList<User> usersDB = new ArrayList<User>();
    public static ArrayList<Workout> workoutsDB = new ArrayList<Workout>();
    public static Workout currentWorkout = new Workout();
    public static ArrayList<Ariketa> ariketakDB = new ArrayList<>();
    public static boolean nightMode = false;
    public static String lenguaje = "";
    public static Context context;
}
