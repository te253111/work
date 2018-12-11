package com.myproject.repaircar.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.myproject.repaircar.R;
import com.myproject.repaircar.base.BaseDialogFragment;


/**
 * Created by Semicolon07 on 3/14/2017 AD.
 */

public class SampleDialog extends BaseDialogFragment {
    private Listener listener;

    public interface Listener {

    }

    public static SampleDialog newInstance() {
        SampleDialog fragment = new SampleDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_sample;
    }

    @Override
    protected boolean isFillScreen() {
        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onInit(Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
    }

    @Override
    protected void onInitInstances(View rootView, Bundle savedInstanceState) {
        super.onInitInstances(rootView, savedInstanceState);
    }
}
