package com.myproject.repaircar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myproject.repaircar.Navigator;
import com.myproject.repaircar.R;
import com.myproject.repaircar.base.BaseActivity;
import com.myproject.repaircar.customview.MyProgressDialog;
import com.myproject.repaircar.manager.ApiManager;
import com.myproject.repaircar.manager.DefaultSubscriber;
import com.myproject.repaircar.manager.SessionManager;
import com.myproject.repaircar.models.UserModel;
import com.myproject.repaircar.models.form.LoginApiForm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashScreenActivity extends BaseActivity {
    private static final String INTENT_PARAM_LOGOUT = "INTENT_PARAM_LOGOUT";
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 2000L;

    @BindView(R.id.app_name_textView)
    TextView appNameTextView;
    @BindView(R.id.login_container)
    LinearLayout loginContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.username_editText)
    EditText usernameEditText;
    @BindView(R.id.password_editText)
    EditText passwordEditText;

    private ApiManager serviceManager;
    private MyProgressDialog progressDialog;
    private SessionManager sessionManager;
    private UserModel employeeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        boolean logout = getIntent().getBooleanExtra(INTENT_PARAM_LOGOUT, false);
        if (logout) {
            showLoginContainer();
            return;
        }
        initInstances();
        checkSession();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.up_from_bottom);
        appNameTextView.startAnimation(animation);

        handler = new Handler();
        runnable = () -> {
            hideSplashProgress();
            if (employeeModel == null) {
                showLoginContainer();
                handler.removeCallbacks(runnable);
                time = delay_time - (System.currentTimeMillis() - time);
                return;
            }

            navigateToMainUi();
        };
    }

    private void navigateToMainUi() {
        Navigator.getInstance().navigateToMain(SplashScreenActivity.this);
        finish();
    }

    private void initInstances() {
        progressDialog = MyProgressDialog.newInstance();
        progressDialog.setCancelable(false);

        sessionManager = new SessionManager(getApplicationContext());
        serviceManager = new ApiManager(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void hideSplashProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void showLoginContainer() {
        loginContainer.setVisibility(View.VISIBLE);
    }

    private void hideLoginContainer() {
        loginContainer.setVisibility(View.GONE);
    }

    private void checkSession() {
        employeeModel = sessionManager.getProfile();
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    @OnClick({R.id.login_button})
    public void onLoginButtonClick(View view) {
        if (view.getId() == R.id.login_button) {
            login();
        }
    }

    private void login() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String invalidMessage = null;

        if (username.length() == 0)
            invalidMessage = getString(R.string.enter_username);
        else if (password.length() == 0)
            invalidMessage = getString(R.string.enter_password);

        if (invalidMessage != null) {
            showToast(invalidMessage);
            return;
        }
        showProgressDialog(progressDialog);

        LoginApiForm.Request reqModel = new LoginApiForm.Request();
        reqModel.setUsername(username);
        reqModel.setPassword(password);
        serviceManager.login(reqModel, new LoginSubscriber());
    }

    private void showLoginResult(LoginApiForm.Response loginResponseModel) {
        if (loginResponseModel.isValid()) {
            sessionManager.saveProfile(loginResponseModel.getUser());
            navigateToMainUi();
        } else {
            showToast(getString(R.string.error_login));
        }
    }

    final class LoginSubscriber extends DefaultSubscriber<LoginApiForm.Response> {
        @Override
        public void onError(Throwable e) {
            hideProgressDialog();
            showToast(e.getMessage());
        }

        @Override
        public void onNext(LoginApiForm.Response loginResponseModel) {
            hideProgressDialog();
            showLoginResult(loginResponseModel);
        }
    }

}
