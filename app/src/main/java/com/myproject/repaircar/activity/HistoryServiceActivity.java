package com.myproject.repaircar.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.myproject.repaircar.R;
import com.myproject.repaircar.base.BaseActivity;

public class HistoryServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_service);
    }

    public static Intent callingIntent(Context context) {
        Intent intent = new Intent(context, HistoryServiceActivity.class);
        return intent;
    }

    private void initProfile() {
        setTitle(R.string.history_service);
    }

}
