package com.myproject.repaircar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.myproject.repaircar.R;
import com.myproject.repaircar.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileActivity extends BaseActivity {

    @BindView(R.id.name_editText)
    EditText nameEditText;
    @BindView(R.id.birthday_editText)
    EditText birthdayEditText;
    @BindView(R.id.address_editText)
    EditText addressEditText;
    @BindView(R.id.telno_editText)
    EditText telnoEditText;
    @BindView(R.id.user_editText)
    EditText userEditText;
    @BindView(R.id.email_editText)
    EditText emailEditText;
    @BindView(R.id.pass_editText)
    EditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        initProfile();
    }

    public static Intent callingIntent(Context context) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        return intent;
    }

    private void initProfile() {
        setTitle(R.string.user_profile);
        nameEditText.setText("test");
    }


}
