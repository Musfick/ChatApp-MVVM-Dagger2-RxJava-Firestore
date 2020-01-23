package com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.foxhole.chatappmvvmdagger2rxjavafirestore.R;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.account.AccountActivity;
import com.foxhole.chatappmvvmdagger2rxjavafirestore.ui.main.connections.ConnectionsPageAdapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intToolbar();
        intView();


    }

    private void intView() {
        viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2.setAdapter(new ConnectionsPageAdapter(this));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Chats");
                        tab.setIcon(R.drawable.ic_chat);
                        break;
                    case 1:
                        tab.setText("Notifications");
                        tab.setIcon(R.drawable.ic_notifications);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorBadge));
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(5);
                        break;
                    case 2:
                        tab.setText("Users");
                        tab.setIcon(R.drawable.ic_people);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }

    private void intToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Connections");
        toolbar.setNavigationIcon(R.drawable.ic_nav);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            moveToAccountSetting();
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveToAccountSetting() {
        startActivity( new Intent(MainActivity.this, AccountActivity.class));
    }
}
