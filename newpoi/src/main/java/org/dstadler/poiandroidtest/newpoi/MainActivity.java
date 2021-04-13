package org.dstadler.poiandroidtest.newpoi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private recentitems recentitems;
    private examples examples;
    private open open;
    private bookmarked bookmarked;
    private add_screen add_screen;
    MaterialToolbar toolbar;
    private long backBtnTime = 0;

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0<=gapTime && 2000 >= gapTime){
            super.onBackPressed();
        }
        else{
            backBtnTime = curTime;
            Toast.makeText(this,"한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.action_recentitems:
                        setFrag(0);
                        break;
                    case R.id.action_examples:
                        setFrag(1);
                        break;
                    case R.id.action_open:
                        setFrag(2);
                        break;
                    case R.id.action_bookmarked:
                        setFrag(3);
                        break;
                    case R.id.home:
                        finish();
                        break;
                }
                return true;
            }
        });
        recentitems = new recentitems();
        examples = new examples();
        open = new open();
        bookmarked = new bookmarked();
        add_screen = new add_screen();
        setFrag(0);

    }

    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n) {
            case 0:
                ft.replace(R.id.main_frame, recentitems);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, examples);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, open);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, bookmarked);
                ft.commit();
                break;
        }
    }
}