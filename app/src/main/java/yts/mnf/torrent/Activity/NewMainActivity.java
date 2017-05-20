package yts.mnf.torrent.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.TextView;


import com.startapp.android.publish.adsCommon.AutoInterstitialPreferences;
import com.startapp.android.publish.adsCommon.StartAppAd;

import yts.mnf.torrent.AppController;
import yts.mnf.torrent.Fragment.PopcornFragment;
import yts.mnf.torrent.Fragment.YifyMovieFragment;
import yts.mnf.torrent.R;
import yts.mnf.torrent.Tools.PreferensHandler;

public class NewMainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
Context c;
    Toolbar toolbar;
    PreferensHandler pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        c = this;
        pref = new PreferensHandler(c);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StartAppAd.setAutoInterstitialPreferences(
                new AutoInterstitialPreferences()
                        .setSecondsBetweenAds(60)
        );

        //Intent stream = new Intent(NewMainActivity.this,StreamActivity.class);
       // startActivity(stream);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search = new Intent(NewMainActivity.this, SearchActivity.class);
                startActivity(search);
            }
        });
        if(getIntent().getExtras()!=null){
            Log.e("TAG","notification intent not null");
            if(getIntent().getExtras().containsKey("url")){
                Log.e("TAG","notification intent not url = "+getIntent().getExtras().getString("url"));
                new AppController().startBrowser(getIntent().getExtras().getString("url"),c);
            }
        }else{
            Log.e("TAG","notification intent is null");
        }
      //  setUpDrakTheme(pref.getThemeDark());

    }

    @Override
    protected void onResume() {
        super.onResume();
      //  setUpDrakTheme(pref.getThemeDark());

    }
    public void setUpDrakTheme(boolean key){
        if(toolbar!=null) {
            if (key) {
                toolbar.setBackgroundColor(getResources().getColor(R.color.blue_grey900));
            } else {
                toolbar.setBackgroundColor(getResources().getColor(R.color.red500));

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent search = new Intent(NewMainActivity.this, SearchActivity.class);
            startActivity(search);
            return true;
        }
        else if (id == R.id.action_settings) {
            Intent settings = new Intent(NewMainActivity.this, NewSettingsAct.class);
            startActivity(settings);
            return true;
        }
        else if (id == R.id.action_about) {
            Intent about = new Intent(NewMainActivity.this, AboutAcitivty.class);
            startActivity(about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_new_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0){
                return  YifyMovieFragment.newInstance("","");
            }else if(position ==1 ){
                return PopcornFragment.newInstance("","");
            }
            return TabbedActivity.PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.e("TabbedActivity","getPageTitle = "+position);
            switch (position) {
                case 0:
                    return "Yify Movies";
                case 1:
                    return "Popcorn Movies";

            }
            return null;
        }
    }
}
