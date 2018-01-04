package com.setare.setare_96.store.Basic_Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.setare.setare_96.store.LISTs.LIST_Clothing_Fragment;
import com.setare.setare_96.store.LISTs.LIST_Digital_Fragment;
import com.setare.setare_96.store.LISTs.LIST_Home_Appliances_Fragment;
import com.setare.setare_96.store.LISTs.LIST_Sports_Fragment;
import com.setare.setare_96.store.LISTs.LIST_Toiletries_Fragment;
import com.setare.setare_96.store.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amirhossein on 10/17/17.
 */

public class LIST_ListActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new LIST_Digital_Fragment(), "کالای دیجیتال");
        adapter.addFrag(new LIST_Clothing_Fragment(), "پوشاک");
        adapter.addFrag(new LIST_Sports_Fragment(), "لوازم ورزشی و سرگرمی");
        adapter.addFrag(new LIST_Toiletries_Fragment(), "لوازم زیبایی");
        adapter.addFrag(new LIST_Home_Appliances_Fragment(), "لوازم خانگی");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
