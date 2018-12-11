package com.myproject.repaircar.template;

import android.os.Bundle;
import android.view.View;

import com.myproject.repaircar.base.BaseFragment;
import com.myproject.repaircar.fragment.SampleFragment;


/**
 * Created by Semicolon07 on 4/20/2017 AD.
 */

public class FragmentTemplate extends BaseFragment {
    public static FragmentTemplate newInstance() {
        FragmentTemplate fragment = new FragmentTemplate();
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
