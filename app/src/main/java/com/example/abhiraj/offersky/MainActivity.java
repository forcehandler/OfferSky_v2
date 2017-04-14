package com.example.abhiraj.offersky;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.abhiraj.offersky.fragment.TestFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener, AHBottomNavigation.OnTabSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TABSTATE_KEY = "tabstate";


    private enum TabState {Shopping, Food};

    private TabState tabState = TabState.Shopping;

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        if(savedInstanceState != null){
            tabState = (TabState) savedInstanceState.getSerializable(TABSTATE_KEY);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // UI functions
        createTabLayout();
        setupBottomNavigation();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TABSTATE_KEY, tabState);
    }

    //=============================================

    private void setupBottomNavigation(){
        Log.d(TAG, "setupBottomNavigation()");
        // Enable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(true);

        // Enable the translation of the FloatingActionButton
        bottomNavigation.manageFloatingActionButtonBehavior(fab);

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(this);

        switch (tabState){

            case Shopping:
                // Create items
                AHBottomNavigationItem s_item1 = new AHBottomNavigationItem(R.string.shopping_tab_1, R.drawable.ic_menu_camera, R.color.tab1);
                AHBottomNavigationItem s_item2 = new AHBottomNavigationItem(R.string.shopping_tab_2, R.drawable.ic_menu_gallery, R.color.tab2);
                AHBottomNavigationItem s_item3 = new AHBottomNavigationItem(R.string.shopping_tab_3, R.drawable.ic_menu_send, R.color.tab3);

                // clear previous items (if any)
                bottomNavigation.removeAllItems();
                // Add items
                bottomNavigation.addItem(s_item1);
                bottomNavigation.addItem(s_item2);
                bottomNavigation.addItem(s_item3);

                // set 1st Tab as open
                bottomNavigation.setCurrentItem(0);
                break;
            case Food:
                // Create items
                AHBottomNavigationItem f_item1 = new AHBottomNavigationItem(R.string.food_tab_1, R.drawable.ic_menu_camera, R.color.tab1);
                AHBottomNavigationItem f_item2 = new AHBottomNavigationItem(R.string.food_tab_2, R.drawable.ic_menu_gallery, R.color.tab2);
                AHBottomNavigationItem f_item3 = new AHBottomNavigationItem(R.string.food_tab_3, R.drawable.ic_menu_send, R.color.tab3);

                // clear previous items (if any)
                bottomNavigation.removeAllItems();
                // Add items
                bottomNavigation.addItem(f_item1);
                bottomNavigation.addItem(f_item2);
                bottomNavigation.addItem(f_item3);

                // set 1st Tab as open
                bottomNavigation.setCurrentItem(0);
                break;

        }
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {

        Log.d(TAG, "btab " + position + " selected" + " and wasSelected = " + wasSelected+"");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (position){

            case 0:
                switch (tabState){
                    case Shopping:
                        fragmentTransaction.replace(R.id.fragment_container, TestFragment.newInstance("Shopping tab 1"))
                                .commit();
                        break;
                    case Food:
                        fragmentTransaction.replace(R.id.fragment_container, TestFragment.newInstance("Food tab 1"))
                                .commit();;
                        break;

                }
                return true;

            case 1:
                switch (tabState){
                    case Shopping:
                        fragmentTransaction.replace(R.id.fragment_container, TestFragment.newInstance("Shopping tab 2"))
                                .commit();;
                        break;
                    case Food:
                        fragmentTransaction.replace(R.id.fragment_container, TestFragment.newInstance("Food tab 2"))
                                .commit();;
                        break;
                }
                return true;

            case 2:
                switch (tabState){
                    case Shopping:
                        fragmentTransaction.replace(R.id.fragment_container, TestFragment.newInstance("Shopping tab 3"))
                                .commit();;
                        break;
                    case Food:
                        fragmentTransaction.replace(R.id.fragment_container, TestFragment.newInstance("Food tab 3"))
                                .commit();;
                        break;

                }
                return true;

        }
        return false;
    }

    private void createTabLayout(){
        Log.d(TAG, "createTabLayout()");
        if(tabLayout != null) {
            tabLayout.addTab(tabLayout.newTab().setText("Shopping"));
            tabLayout.addTab(tabLayout.newTab().setText("Food"));
            tabLayout.addOnTabSelectedListener(this);

            switch (tabState){
                case Shopping:
                    Log.d(TAG, "shopping tab select");
                    TabLayout.Tab s_tab = tabLayout.getTabAt(0);
                    s_tab.select();
                    break;
                case Food:
                    Log.d(TAG, "food tab select");
                    TabLayout.Tab f_tab = tabLayout.getTabAt(0);
                    f_tab.select();
                    break;
            }
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d(TAG, "tab " + tab.getPosition() + " selected");
        switch (tab.getPosition()){
            case 0:
                tabState = TabState.Shopping;
                setupBottomNavigation();
                break;
            case 1:
                tabState = TabState.Food;
                setupBottomNavigation();
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //Log.d(TAG, "tab " + tab.getPosition() + " unselected");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        //Log.d(TAG, "tab " + tab.getPosition() + " relected");
    }


}
