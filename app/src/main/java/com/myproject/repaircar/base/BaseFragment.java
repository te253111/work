package com.myproject.repaircar.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myproject.repaircar.Contextor;
import com.myproject.repaircar.R;
import com.myproject.repaircar.customview.MyProgressDialog;
import com.myproject.repaircar.manager.ApiManager;
import com.myproject.repaircar.manager.SessionManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;


/**
 * Created by Semicolon07 on 2/28/2017 AD.
 */

public abstract class BaseFragment extends Fragment {

    protected MyProgressDialog progressDialogFragment;
    private Unbinder unbinder;
    protected SessionManager sessionManager;
    protected ApiManager apiManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext().getApplicationContext());

        progressDialogFragment = MyProgressDialog.newInstance();
        progressDialogFragment.setCancelable(false);

        apiManager = new ApiManager(Contextor.getInstance().getContext());
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() <= 0)
            return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initInstances(rootView,savedInstanceState);
        return rootView;
    }

    protected abstract int getLayoutId();

    protected void initInstances(View rootView, Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected void showLoading(){
        showDialogFragment(progressDialogFragment);
    }

    protected void hideLoading(){
        hideDialogFragment(progressDialogFragment);
    }

    protected boolean isDialogShowing(DialogFragment dialogFragment){
        if(dialogFragment != null
                && dialogFragment.getDialog() != null
                && dialogFragment.getDialog().isShowing())
            return true;

        if(dialogFragment != null &&
                getChildFragmentManager().findFragmentByTag(dialogFragment.getClass().getSimpleName()) != null)
            return true;
        return false;
    }

    protected void showDialogFragment(DialogFragment dialogFragment) {
        try{
            if(isDialogShowing(dialogFragment))
                return;

            if(dialogFragment.isAdded())
                return;

            if (dialogFragment != null ) {
                String tag = dialogFragment.getClass().getSimpleName();
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag(tag);
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                dialogFragment.show(ft, tag);
            }
        }catch (Exception e){
            Log.e("Fragment",e.getMessage());
        }
    }

    protected void hideDialogFragment(DialogFragment dialogFragment) {
        try{
            if (isDialogShowing(dialogFragment)) {
                dialogFragment.dismiss();
            }
        }catch (Exception e){
            Log.e("Fragment",e.getMessage());
        }
    }

    protected void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void unSubscribe(Subscription subscription){
        if(subscription == null || !subscription.isUnsubscribed())
            return;

        subscription.unsubscribe();
    }

    protected void showAlertDialog(String message, DialogInterface.OnClickListener onClickListener){
        final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
        adbConfirmExit.setTitle(getString(R.string.app_name));
        adbConfirmExit.setMessage(message);
        adbConfirmExit.setNegativeButton(getString(R.string.button_ok), onClickListener);
        adbConfirmExit.show();
    }

    protected void showAlertDialog(String message) {
        showAlertDialog(message,null);
    }

    public void confirm(String message,DialogInterface.OnClickListener onClickListener){
        final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(getContext());
        adbConfirmExit.setTitle(getString(R.string.app_name));
        adbConfirmExit.setMessage(message);
        adbConfirmExit.setCancelable(false);
        adbConfirmExit.setNegativeButton(getString(R.string.button_cancel), null);
        adbConfirmExit.setPositiveButton(getString(R.string.button_ok), onClickListener);
        adbConfirmExit.show();
    }
}
