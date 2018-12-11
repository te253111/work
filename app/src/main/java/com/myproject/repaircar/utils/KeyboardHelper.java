package com.myproject.repaircar.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TabHost;

public class KeyboardHelper {
    public static void hideSoftKeyboardOnActivity(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }

    }

    public static void hideSoftKeyboardOnDialog(Context context, Dialog dialog) {
        try {
            /*InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), 0);
            inputMethodManager.hide*/
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
        }
    }

    public static void hideSoftKeyboardOnTabHost(Context context, TabHost tabHost) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(tabHost.getApplicationWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public static void hideSoftKeyboard(Context context, EditText p_edt){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(p_edt.getWindowToken(), 0);
    }

    public static void showSoftKeyboardOnDialog(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
