package com.example.admin.androidadvance;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ViewPager mPager;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mPager = (ViewPager) findViewById(R.id.viewPaper);
        mTabLayout = (TabLayout) findViewById(R.id.tabHost);

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        ViewPaperAdapter adapter = new ViewPaperAdapter(manager);
        mPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setTabsFromPagerAdapter(adapter);
        setupTabIcons(mTabLayout);
    }
    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_icon_tablayout);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_icon_tablayout);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_icon_tablayout);
    }

}
