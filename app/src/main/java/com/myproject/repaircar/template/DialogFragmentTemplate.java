package com.myproject.repaircar.template;

import android.os.Bundle;
import android.view.View;

import com.myproject.repaircar.R;
import com.myproject.repaircar.base.BaseDialogFragment;


/**
 * Created by Semicolon07 on 3/14/2017 AD.
 */

public class DialogFragmentTemplate extends BaseDialogFragment {
    private Listener listener;

    public interface Listener {

    }

    public static DialogFragmentTemplate newInstance() {
        DialogFragmentTemplate fragment = new DialogFragmentTemplate();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.dialog_sample;
    }

    @Override
    protected boolean isFillScreen() {
        return true;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onInitInstances(View rootView, Bundle savedInstanceState) {
        super.onInitInstances(rootView, savedInstanceState);
    }
}
