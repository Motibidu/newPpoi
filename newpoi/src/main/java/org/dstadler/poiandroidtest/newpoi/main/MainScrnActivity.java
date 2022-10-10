package org.dstadler.poiandroidtest.newpoi.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aspose.words.Document;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.Method;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;
import org.dstadler.poiandroidtest.newpoi.cls.RecyclerViewAdapter;
import org.dstadler.poiandroidtest.newpoi.cls.StorageUtil;
import org.dstadler.poiandroidtest.newpoi.gnrtDoc.DocCatActivity;
import org.dstadler.poiandroidtest.newpoi.profile.ProfileScrnActivity;

import java.io.File;

public class MainScrnActivity extends AppCompatActivity implements BottomSheetDialog.bottomSheetListener {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private MainRecentItemsFragment MainRecentItemsFragment;
    private main_examples main_examples;
    private main_open main_open;
    private main_bookmarked main_bookmarked;
    private DocCatActivity categoryScrn;
    MaterialToolbar toolbar;
    private long backBtnTime = 0;

    private Context mContext;

    private String[] allPath;
    private File storage;
    private BottomSheetDialog bottomSheetDialog;

    BottomSheetDialog.bottomSheetListener listener;

    private int filePosition;

    private final String TAG = "MAINSCRNACTIVITY";

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //require read_external_storage permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

        //Do these only at first open
        boolean first_open = PreferenceManager.getBoolean(getApplicationContext(), "firstOpen");
        if (first_open) {
            System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
            System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
            System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
        }

        bottomSheetDialog = new BottomSheetDialog();


        mContext = getApplicationContext();


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.themeColor));


        toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileScrnActivity.class);
                startActivity(intent);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_screen: {
                        Intent intent = new Intent(mContext, DocCatActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.search: {
                        //init allPath
                        allPath = StorageUtil.getStorageDirectories(mContext);

                        for (String path : allPath) {
                            storage = new File(path);
                            Method.load_Directory_Files(storage);
                        }

                        Fragment frg = getSupportFragmentManager().findFragmentByTag("0");
                        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.commit();
                    }
                }
                return false;
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
        MainRecentItemsFragment = new MainRecentItemsFragment();
        main_examples = new main_examples();
        main_open = new main_open();
        main_bookmarked = new main_bookmarked();
        categoryScrn = new DocCatActivity();
        setFrag(0);

    }

    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, MainRecentItemsFragment, "0");
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, main_examples, "1");
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, main_open, "2");
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, main_bookmarked, "3");
                ft.commit();
                break;
        }
    }


    @Override
    public void convertToPDF() {
        int i = PreferenceManager.getInt(getApplicationContext(),"filePosition");
        String fileDirectory = Constant.allDirectoryList.get(i);


        Log.d(TAG, "filePosition :"+i+", fileDirectory: "+fileDirectory);
    }

}