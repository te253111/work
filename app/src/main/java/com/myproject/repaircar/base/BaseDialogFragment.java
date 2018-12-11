package com.myproject.repaircar.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Semicolon07 on 4/17/2016 AD.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected boolean isVisibleToUser;
    private Unbinder unbinder;

    public BaseDialogFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (isFillScreen() && dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    protected boolean isFillScreen() {
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onInit(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutResId() == -1)
            return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(getLayoutResId(), container, false);
        bindView(this, rootView);
        onInitInstances(rootView, savedInstanceState);
        return rootView;
    }

    protected int getLayoutResId() {
        return -1;
    }

    protected void onInit(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    protected void onInitInstances(View rootView, Bundle savedInstanceState) {

    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

    /**
     * Shows a {@link Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackBarMessage(String message) {
        //TODO : Do Stuff
    }


    protected AppCompatActivity getHolderActivity() {
        return (AppCompatActivity) getActivity();
    }

    protected void showUpAction() {
        getHolderActivity().getSupportActionBar().setHomeButtonEnabled(true);
        getHolderActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void setToolbarTitle(String title) {
        getHolderActivity().getSupportActionBar().setTitle(title);
    }

    protected void setToolbarSubTitle(String subTitle) {
        getHolderActivity().getSupportActionBar().setSubtitle(subTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }

    protected void bindView(@NonNull Object target, @NonNull View source) {
        unbinder = ButterKnife.bind(target, source);
    }

}
