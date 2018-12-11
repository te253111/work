package com.myproject.repaircar;

import android.content.Context;
import android.content.Intent;

import com.myproject.repaircar.activity.HistoryServiceActivity;
import com.myproject.repaircar.activity.MainActivity;
import com.myproject.repaircar.activity.MainDrawerActivity;
import com.myproject.repaircar.activity.UserProfileActivity;


/**
 * Created by Semicolon07 on 7/5/2016 AD.
 */

public class Navigator {
    private static Navigator INSTANCE = new Navigator();

    private Navigator() {

    }

    public static Navigator getInstance() {
        return INSTANCE;
    }

    public void navigateToMain(Context context) {
        Intent intent = MainDrawerActivity.callingIntent(context);
        context.startActivity(intent);
    }

    public void navigateToUserProfile(Context context) {
        Intent intent = UserProfileActivity.callingIntent(context);
        context.startActivity(intent);
    }

    public void navigateToHistoryService(Context context) {
        Intent intent = HistoryServiceActivity.callingIntent(context);
        context.startActivity(intent);
    }

}
