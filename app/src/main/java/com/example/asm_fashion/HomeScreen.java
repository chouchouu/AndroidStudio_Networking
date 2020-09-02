package com.example.asm_fashion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        BottomNavigationView navigationView = findViewById(R.id.navigation);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)
                navigationView.getLayoutParams();

        //ActionBar
        actionBar = getSupportActionBar();
// Cho Màn Hình mặc định khi vào app là Home
        if (savedInstanceState == null) {
            actionBar.hide();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    new FragmentHome()).commit();
            navigationView.setSelectedItemId(R.id.home);
        }
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.page_1:
                        actionBar.hide();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                                new FragmentHome()).commit();
                        return true;
                    case R.id.page_2:
                        actionBar.hide();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                                new FragmentAdd()).commit();
                        return true;
                    case R.id.page_3:
                        actionBar.hide();
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                                new FragmentUser()).commit();
                        return true;
                }
                return false;
            }
        });

    }
}