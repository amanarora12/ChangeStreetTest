package com.aman.changestreettest;

import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FlipAnimator.FlipAnimatorListener{
    //private NavigationView mNavMenu;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mListContainer;
    private FlipAnimator mFlipAnimator;
    private List<String> menuItemNames;

    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.colorAccent));
        mListContainer= (LinearLayout) findViewById(R.id.menu_list_container);
        //menuItemNames= Arrays.asList(getResources().getStringArray(R.array.MenuItemNames));
        menuItemNames=new ArrayList<>();
        menuItemNames.add("DINING");
        menuItemNames.add("TRAVEL");
        menuItemNames.add("EDUCATION");
        menuItemNames.add("SERVICES");
        menuItemNames.add("UTILITIES");
        menuItemNames.add("SECURITY");
        menuItemNames.add("HOUSING");
        mFlipAnimator=new FlipAnimator(this,mDrawerLayout,menuItemNames);

        mListContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });
        toggleActionBarConfig();
    }

    private void toggleActionBarConfig(){
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mListContainer.removeAllViews();
                mListContainer.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && mListContainer.getChildCount() == 0)
                    mFlipAnimator.showMenu();
            }

            @Override
            public void setToolbarNavigationClickListener(View.OnClickListener onToolbarNavigationClickListener) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if(item!=null && item.getItemId()==android.R.id.home){
                    if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        mDrawerLayout.closeDrawer(Gravity.RIGHT);
                    }
                    else {
                        mDrawerLayout.openDrawer(Gravity.RIGHT);
                    }
                }
                return true;
            }
        };
        mDrawerLayout.addDrawerListener(drawerToggle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                }
                else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item!=null && item.getItemId()==android.R.id.home){
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
            else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void addViewToListContainer(View view) {
        mListContainer.addView(view);
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.closeDrawers();
    }

}
