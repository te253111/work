package com.myproject.repaircar.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.myproject.repaircar.Contextor;
import com.myproject.repaircar.models.UserModel;



/**
 * Created by Semicolon07 on 1/8/2017 AD.
 */

public class SessionManager {
    private final Context context;
    private UserModel userModel;
    private SharedPreferences userPreferences;
    private SharedPreferences.Editor userPrefsEditor;

    public static final String USER_PREF_NAME = "PROFILE_PREF";
    private static final String PROPERTY_PROFILE = "user";

    public SessionManager(){
        this(Contextor.getInstance().getContext());
    }

    public SessionManager(Context context) {
        this.context = context;
        userPreferences = context.getSharedPreferences(USER_PREF_NAME, context.MODE_PRIVATE);
        userPrefsEditor = userPreferences.edit();
    }

    public void saveProfile(UserModel userModel) {
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        userPrefsEditor.putString(PROPERTY_PROFILE, json);
        userPrefsEditor.commit();
    }

    public void clear(){
        userPrefsEditor.clear();
        userPrefsEditor.commit();
    }

    public UserModel getProfile() {
        Gson gson = new Gson();
        String json = userPreferences.getString(PROPERTY_PROFILE, "");
        UserModel user = gson.fromJson(json, UserModel.class);

        return user;
    }
}
