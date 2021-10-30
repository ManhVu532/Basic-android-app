package com.strength.navigationdrawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.strength.navigationdrawer.fragment.HomeFragment;
import com.strength.navigationdrawer.fragment.LoginFragment;
import com.strength.navigationdrawer.fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    private int fragment = 0;
    SharedPreferences sharedPref;
    TextView txt_username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.account);
        setSupportActionBar(toolbar);

        sharedPref = getSharedPreferences("mySettings", MODE_PRIVATE);

        String user = getUser();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView nv = mDrawerLayout.findViewById(R.id.nav_view);
        txt_username =  nv.inflateHeaderView(R.layout.drawer_header).findViewById(R.id.txt_user);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_open, R.string.navigation_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(user.isEmpty()) {
            replaceFragment(new LoginFragment());
            toolbar.setTitle("Đăng nhập");
        }
        else{
            replaceFragment(new HomeFragment());
            txt_username.setText(user);
            toolbar.setTitle("Trang chủ");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                saveUser("");
                Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                if(fragment == 0) {
                    replaceFragment(new LoginFragment());
                    toolbar.setTitle("Đăng nhập");
                    txt_username.setText("Xin chào");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveUser(String username){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("mySetting", username);
        editor.commit();
    }

    public String getUser(){
        return sharedPref.getString("mySetting", "");
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TextView getTxt_username() {
        return txt_username;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.login) {
            if (fragment != 0) {
                if(getUser().isEmpty()) {
                    replaceFragment(new LoginFragment());
                    toolbar.setTitle("Đăng nhập");
                }
                else {
                    replaceFragment(new HomeFragment());
                    toolbar.setTitle("Trang chủ");
                    txt_username.setText(getUser());
                }
                fragment = 0;
            }
        }
        else if(id == R.id.signin) {
            if (fragment != 1) {
                replaceFragment(new RegisterFragment());
                toolbar.setTitle("Đăng ký");
                fragment = 1;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
