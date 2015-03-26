package com.example.root.profile_app;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class parent extends ActionBarActivity implements
        android.support.v7.app.ActionBar.TabListener {

    ViewPager viewpager;



    android.support.v7.app.ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent);


        viewpager = (ViewPager) findViewById(R.id.pager);


        FragmentManager fragManager =    getSupportFragmentManager();

        viewpager.setAdapter(new MyAdapter(fragManager));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        android.support.v7.app.ActionBar.Tab tab1;
        tab1 = actionBar.newTab();
        tab1.setText( "overview");
        tab1.setTabListener(this);

        android.support.v7.app.ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("dashboard");
        tab2.setTabListener(this);

        android.support.v7.app.ActionBar.Tab tab3 = actionBar.newTab();
        tab3.setText("statistic");
        tab3.setTabListener(this);

        android.support.v7.app.ActionBar.Tab tab4 = actionBar.newTab();
        tab4.setText("history");
        tab4.setTabListener(this);

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        actionBar.addTab(tab4);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab,
                              android.support.v4.app.FragmentTransaction fragmentTransaction) {

        viewpager.setCurrentItem( tab.getPosition());


    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab,
                                android.support.v4.app.FragmentTransaction fragmentTransaction) {


    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction
            fragmentTransaction) {

    }


}


class  MyAdapter extends FragmentPagerAdapter{

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;

        if (position  == 0)
        {
            frag = new overview();
        }
        if (position == 1)
        {
            frag = new dashboard();
        }

        if (position == 2)
        {
            frag = new statistic();
        }

        if (position == 3)
        {
            frag = new history();
        }
        return frag;


    }

    @Override
    public int getCount() {
        return 4;
    }
}