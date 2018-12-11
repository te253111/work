package com.myproject.repaircar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.myproject.repaircar.R;
import com.myproject.repaircar.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static Intent callingIntent(Context context) {
        Intent intent = new Intent(context,MainActivity.class);
        return intent;
    }
}
