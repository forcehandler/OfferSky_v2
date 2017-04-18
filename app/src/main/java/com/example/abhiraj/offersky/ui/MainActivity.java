package com.example.abhiraj.offersky.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.abhiraj.offersky.BaseActivity;
import com.example.abhiraj.offersky.Constants;
import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.adapter.ShopAdapter;
import com.example.abhiraj.offersky.clickListener.ShopItemClickListenerImplementation;
import com.example.abhiraj.offersky.drawable.BadgeDrawable;
import com.example.abhiraj.offersky.model.Shop;
import com.example.abhiraj.offersky.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener, AHBottomNavigation.OnTabSelectedListener, SearchView.OnQueryTextListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TABSTATE_KEY = "tabstate";


    private enum TabState {Shopping, Food}
    LayerDrawable notification_icon;

    private TabState tabState = TabState.Shopping;
    private boolean isMallReady = false;


    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.bottom_navigation) AHBottomNavigation bottomNavigation;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mallDataReadyReceiver,
                new IntentFilter(Constants.Broadcast.MALL_DATA_READY));

        if(savedInstanceState != null){
            tabState = (TabState) savedInstanceState.getSerializable(TABSTATE_KEY);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                setBadgeCount(MainActivity.this, notification_icon, "7");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO: Replace with proper implementation
        // Get malls
        FirebaseUtils.getMall(this, "GA_0832_MDG");
        // Show loading dialog
        showProgressDialog();

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

        MenuItem itemCart = menu.findItem(R.id.action_cart);
        notification_icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, notification_icon, "9");

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            Log.d(TAG, "cart clicked");
            setBadgeCount(this, notification_icon, "0");
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
            Intent intent = new Intent(MainActivity.this, FilterActivity.class);
            intent.putExtra("Title", "Category");
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, FilterActivity.class);
            intent.putExtra("Title", "Category");
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        // Get the name of the item clicked in drawer and pass it along to the new filter activity
        else{
            Intent intent = new Intent(MainActivity.this, FilterActivity.class);
            intent.putExtra("Title", item.getTitle());
            startActivity(intent);
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

    // TODO: Properly Implement bottom nav tab selection and replace fragments with category filter logic
    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {

        Log.d(TAG, "btab " + position + " selected" + " and wasSelected = " + wasSelected+"");


        switch (position){

            case 0:
                switch (tabState){
                    case Shopping:
                        break;
                    case Food:
                        break;

                }
                if(isMallReady)
                    startFilteringCategory(position);
                addNavigationItems(0);
                return true;

            case 1:
                switch (tabState){
                    case Shopping:
                        break;
                    case Food:
                        break;
                }
                if(isMallReady)
                    startFilteringCategory(position);
                addNavigationItems(1);
                return true;

            case 2:
                switch (tabState){
                    case Shopping:

                        break;
                    case Food:
                        break;

                }
                if(isMallReady)
                    startFilteringCategory(position);
                addNavigationItems(2);
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
                    try {
                        s_tab.select();
                    }catch (Exception e){
                        Log.e(TAG, e.toString());
                    }
                    break;
                case Food:
                    Log.d(TAG, "food tab select");
                    TabLayout.Tab f_tab = tabLayout.getTabAt(0);
                    try {
                        f_tab.select();
                    }catch (Exception e){
                        Log.e(TAG, e.toString());
                    }
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


    // TODO: Replace TestReyclerView with proper Implementation
    ShopAdapter shopAdapter;
    List<Shop> mModels;
    private void setupTestRecyclerView(){

        Log.d(TAG, "setupTestRecyclerView()");
        final Comparator<Shop> ALPHABETICAL_COMPARATOR = new Comparator<Shop>() {
            @Override
            public int compare(Shop a, Shop b) {
                return a.getName().compareTo(b.getName());
            }
        };
        shopAdapter = new ShopAdapter(this, Shop.class, ALPHABETICAL_COMPARATOR, new ShopItemClickListenerImplementation(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(shopAdapter);

        mModels = new ArrayList<>();

        mModels.addAll(FirebaseUtils.sMall.getShops().values());
        shopAdapter.edit().replaceAll(mModels)
                .commit();

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Shop> filteredModelList = filter(mModels, newText);
        shopAdapter.edit()
                .replaceAll(filteredModelList)
                .commit();
        recyclerView.scrollToPosition(0);
        return true;
    }

    // TODO: Change filter logic to filter according to shop category
    private static List<Shop> filter(List<Shop> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Shop> filteredModelList = new ArrayList<>();
        for (Shop model : models) {
            final String text = model.getName().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void startFilteringCategory(int position) {

        List<String> tabFilterCategories = new ArrayList<>();
        switch (position){
            case 0:
                switch (tabState){
                    case Shopping:
                        tabFilterCategories.add("Formals");
                        tabFilterCategories.add("Ethenic");
                        tabFilterCategories.add("Party wear");
                        tabFilterCategories.add("Sports wear");
                        break;
                    case Food:
                        tabFilterCategories.add("Meals");
                        tabFilterCategories.add("Chaat");
                        tabFilterCategories.add("South Indian");
                        break;
                }
                break;

            case 1:
                switch (tabState){
                    case Shopping:
                        tabFilterCategories.add("Gifts & Toys");
                        tabFilterCategories.add("Electronics");
                        tabFilterCategories.add("Books & Music");
                        tabFilterCategories.add("Games");
                        break;
                    case Food:
                        tabFilterCategories.add("Cafe");
                        tabFilterCategories.add("Chinese");
                        break;
                }
                break;

            case 2:
                switch (tabState){
                    case Shopping:
                        tabFilterCategories.add("Accessories");
                        tabFilterCategories.add("Jewellery");
                        tabFilterCategories.add("Bags");
                        tabFilterCategories.add("Watches");
                        break;
                    case Food:
                        tabFilterCategories.add("Ice cream");
                        tabFilterCategories.add("Drinks");
                        tabFilterCategories.add("Dessert");
                        break;
                }
                break;

        }
        final List<Shop> filteredModelList = categoryFilter(mModels, tabFilterCategories);
        shopAdapter.edit()
                .replaceAll(filteredModelList)
                .commit();
        recyclerView.scrollToPosition(0);
    }

    public static List<Shop> categoryFilter(List<Shop> models, List<String> categories)
    {
        final List<Shop> filteredModelList = new ArrayList<>();

        for(String query : categories) {

            for (Shop model : models) {

                if (model.getCategories().values().contains(query)) {
                    filteredModelList.add(model);
                }
            }
        }
        return filteredModelList;
    }

    // Function for setting the badge for the toolbar icons
    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        Log.d(TAG, "setting badge count to " + count);
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);

    }

    private BroadcastReceiver mallDataReadyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "recieved DATA READY broadcast");
            hideProgressDialog();
            setupTestRecyclerView();
            isMallReady = true;
            onTabSelected(bottomNavigation.getCurrentItem(), true);
        }
    };

    private void addNavigationItems(int position) {

        List<String> items = new ArrayList<>();

        switch (position){

            case 0:
                switch (tabState){
                    case Shopping:
                        items.add("Formals");
                        items.add("Ethenic");
                        items.add("Party wear");
                        items.add("Sports wear");
                        break;
                    case Food:
                        items.add("Meals");
                        items.add("Chaat");
                        items.add("South Indian");
                        break;
                }
                break;
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(navigationView != null){
            final Menu menu = navigationView.getMenu();
            menu.clear();
            navigationView.inflateMenu(R.menu.base);
            for (String item : items) {
                menu.add(R.id.group1, Menu.NONE, 1, item);
            }
        }
    }
}
