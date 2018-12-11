package com.myproject.repaircar.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.myproject.repaircar.Navigator;
import com.myproject.repaircar.R;
import com.myproject.repaircar.customview.MyProgressDialog;
import com.myproject.repaircar.manager.ApiManager;

import rx.Subscription;


/**
 * Created by Semicolon07 on 2/17/2017 AD.
 */

public class BaseActivity extends AppCompatActivity {
    protected Navigator navigator = Navigator.getInstance();
    private static final String TAG  = "Activity";
    protected MyProgressDialog progressDialog;
    protected ApiManager apiManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = MyProgressDialog.newInstance();
        apiManager = new ApiManager(getApplicationContext());
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, "fragment");
        fragmentTransaction.commit();
    }

    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(containerViewId, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setupUpButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void showProgressDialog(){
        showDialogFragment(progressDialog);
    }
    protected void hideProgressDialog(){
        hideProgressDialog(progressDialog);
    }

    protected void showDialogFragment(DialogFragment dialogFragment) {
        try{
            if(dialogFragment != null
                    && dialogFragment.getDialog() != null
                    && dialogFragment.getDialog().isShowing())
                return;

            if (dialogFragment != null ) {
                dialogFragment.show(getSupportFragmentManager(), dialogFragment.getClass().getSimpleName());
            }
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }

    protected void hideDialogFragment(DialogFragment dialogFragment) {
        if (dialogFragment != null && dialogFragment.getDialog() != null
                && dialogFragment.getDialog().isShowing()) {
            dialogFragment.dismiss();
        }
    }

    protected void setSubtitle(String message) {
        //getSupportActionBar().setSubtitle(message);
        getSupportActionBar().setTitle(message);
    }

    public void confirm(String message,DialogInterface.OnClickListener onClickListener){
        final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(this);
        adbConfirmExit.setTitle(getString(R.string.app_name));
        adbConfirmExit.setMessage(message);
        adbConfirmExit.setCancelable(false);
        adbConfirmExit.setNegativeButton(getString(R.string.button_cancel), null);
        adbConfirmExit.setPositiveButton(getString(R.string.button_ok), onClickListener);
        adbConfirmExit.show();
    }


    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showAlertDialog(String message, DialogInterface.OnClickListener onClickListener) {
        final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(this);
        adbConfirmExit.setTitle(getString(R.string.app_name));
        adbConfirmExit.setMessage(message);
        adbConfirmExit.setCancelable(false);
        adbConfirmExit.setNegativeButton(getString(R.string.button_ok), onClickListener);
        adbConfirmExit.show();
    }

    protected void showAlertDialog(String message) {
        showAlertDialog(message,null);
    }

    protected void showProgressDialog(DialogFragment dialogFragment) {
        if (dialogFragment != null) {
            dialogFragment.show(getSupportFragmentManager(), dialogFragment.getClass().getSimpleName());
        }
    }

    protected void hideProgressDialog(DialogFragment dialogFragment) {
        if (dialogFragment != null && dialogFragment.getDialog() != null
                && dialogFragment.getDialog().isShowing()) {
            dialogFragment.dismiss();
        }
    }

    protected void unSubscribe(Subscription subscription){
        if(subscription == null || !subscription.isUnsubscribed())
            return;

        subscription.unsubscribe();
    }
}
