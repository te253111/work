package com.myproject.repaircar.fragment;

import android.os.Bundle;
import android.view.View;

import com.myproject.repaircar.base.BaseFragment;


/**
 * Created by Semicolon07 on 4/20/2017 AD.
 */

public class SampleFragment extends BaseFragment {

    public static SampleFragment newInstance() {
        SampleFragment fragment = new SampleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initInstances(View rootView, Bundle savedInstanceState) {

    }
}
