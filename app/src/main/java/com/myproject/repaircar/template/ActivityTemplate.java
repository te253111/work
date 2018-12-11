package com.myproject.repaircar.template;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.myproject.repaircar.R;
import com.myproject.repaircar.base.BaseActivity;


public class ActivityTemplate extends BaseActivity {

    public static Intent callingIntent(Context context){
        Intent intent = new Intent(context,ActivityTemplate.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();
    }

    private void initInstances() {

    }
}
