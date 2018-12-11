package com.myproject.repaircar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.myproject.repaircar.Navigator;
import com.myproject.repaircar.R;
import com.myproject.repaircar.base.BaseActivity;
import com.myproject.repaircar.manager.ApiManager;
import com.myproject.repaircar.manager.SessionManager;
import com.myproject.repaircar.models.UserModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDrawerActivity extends BaseActivity {
    ActionBarDrawerToggle actionBarDrawerToggle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;

    private SessionManager sessionManager;

    public static Intent callingIntent(Context context) {
        Intent intent = new Intent(context, MainDrawerActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawable);
        ButterKnife.bind(this);

        initInstances();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initInstances() {
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        apiManager = new ApiManager(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        setNavViewDetail();
    }

    private void setNavViewDetail() {
        //UserModel memberModel = sessionManager.getProfile();
        View headerView = navView.getHeaderView(0);
        TextView headerTitle = headerView.findViewById(R.id.headerTitle_textView);
        TextView detailTextView = headerView.findViewById(R.id.detail_textView);
//        headerTitle.setText(memberModel.getFullName());
//        detailTextView.setText(memberModel.getEmail());
        headerTitle.setText("test");
        detailTextView.setText("test");
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            if (!menuItem.isChecked()) menuItem.setChecked(true);
            if (menuItem.getItemId() == R.id.nav_logout) menuItem.setChecked(false);

            //Closing drawer on item click
            drawerLayout.closeDrawers();

            //Check to see which item was being clicked and perform appropriate action
            switch (menuItem.getItemId()) {
                case R.id.menu1:
                    navigateToUserProfileUi();
                    break;
                case R.id.menu2:
                    navigateToHistoryServiceUi();
                    break;
                case R.id.nav_logout:
                    showConfirmLogoutDialog();
                    break;
                default:
                    return true;
            }
            return true;
        }
    };

    private void openMenu1() {
        //replaceFragment(R.id.container, DaycareDeliveryCommentListFragment.newInstance());
        //setSubtitle(getString(R.string.title_daycare_comment));
    }

    private void showConfirmLogoutDialog() {
        final AlertDialog.Builder adbConfirmExit = new AlertDialog.Builder(this);
        adbConfirmExit.setTitle(getString(R.string.app_name));
        adbConfirmExit.setMessage(getString(R.string.confirm_logout));
        adbConfirmExit.setNegativeButton(getString(R.string.button_cancel), null);
        adbConfirmExit.setPositiveButton(getString(R.string.button_ok), (dialogInterface, i) -> logout());
        adbConfirmExit.show();
    }

    private void logout() {
        sessionManager.clear();

        Intent reStartIntent = new Intent(this, SplashScreenActivity.class);
        reStartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(reStartIntent);
        finish();
    }

    private void navigateToUserProfileUi() {
        Navigator.getInstance().navigateToUserProfile(MainDrawerActivity.this);
    }

    private void navigateToHistoryServiceUi() {
        Navigator.getInstance().navigateToHistoryService(MainDrawerActivity.this);
    }

}
