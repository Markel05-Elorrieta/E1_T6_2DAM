package com.example.e1_t6_mob_2dam;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

import objects.User;
import objects.Workout;

public class GlobalVariables {
    public static User logedUser = null;
    public static ArrayList<User> usersDB = new ArrayList<User>();
    public static ArrayList<Workout> workoutsDB = new ArrayList<Workout>();
}
