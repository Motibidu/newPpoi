package org.dstadler.poiandroidtest.newpoi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;

public class recentitems extends Fragment {
    private View view;
    private MaterialToolbar toolbar;
    private add_screen add_screen;
    private profile_screen profile_screen;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_recent_items, container, false);

        add_screen = new add_screen();
        profile_screen = new profile_screen();
        toolbar = view.findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), profile_screen.class);
                startActivity(intent);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_screen: {
                        Intent intent = new Intent(getActivity(), add_screen.class);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
