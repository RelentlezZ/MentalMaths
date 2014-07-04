package org.relentlezz.mentalmaths.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


public class HighScoreActivity extends FragmentActivity implements ActionBar.TabListener{


    ViewPager viewPager;
    SharedPreferences highscorePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_high_score);

        //Setup SharedPrefs
        highscorePrefs = getSharedPreferences("highScores", MODE_PRIVATE);

        //Setup the ActionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab additionTab = actionBar.newTab().setText(getResources().getString(R.string.addition_button));
        ActionBar.Tab subtractionTab = actionBar.newTab().setText(getResources().getString(R.string.subtraction_button));
        ActionBar.Tab multiplicationTab = actionBar.newTab().setText(getResources().getString(R.string.multiplication_button));
        ActionBar.Tab divisionTab = actionBar.newTab().setText(getResources().getString(R.string.division_button));

        //Setup the ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        //PageListener
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        //Set TabListeners
        additionTab.setTabListener(HighScoreActivity.this);
        subtractionTab.setTabListener(HighScoreActivity.this);
        multiplicationTab.setTabListener(HighScoreActivity.this);
        divisionTab.setTabListener(HighScoreActivity.this);

        //Add Tabs
        actionBar.addTab(additionTab);
        actionBar.addTab(subtractionTab);
        actionBar.addTab(multiplicationTab);
        actionBar.addTab(divisionTab);
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
         if(tab.getPosition() != viewPager.getCurrentItem()) {
                viewPager.setCurrentItem(tab.getPosition());
         }
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }


    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }



    public void playAgain(View v){

        Intent intent = new Intent(HighScoreActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void resetHighscores(){

        SharedPreferences.Editor editor = highscorePrefs.edit();
        editor.putString(EndResultActivity.HIGHSCORE_ADDITION_EASY, "x/xZx/0");
        editor.putString(EndResultActivity.HIGHSCORE_ADDITION_HARD, "x/xZx/0");
        editor.putString(EndResultActivity.HIGHSCORE_SUBTRACTION_EASY, "x/xZx/0");
        editor.putString(EndResultActivity.HIGHSCORE_SUBTRACTION_HARD, "x/xZx/0");
        editor.putString(EndResultActivity.HIGHSCORE_MULTIPLICATION_EASY, "x/xZx/0");
        editor.putString(EndResultActivity.HIGHSCORE_MULTIPLICATION_HARD, "x/xZx/0");
        editor.putString(EndResultActivity.HIGHSCORE_DIVISION_EASY, "x/xZx/0");
        editor.putString(EndResultActivity.HIGHSCORE_DIVISION_HARD, "x/xZx/0");

        editor.apply();
        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.high_score, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.action_reset:
                resetHighscores();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


class PagerAdapter extends FragmentStatePagerAdapter{


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        Fragment f;

        if(position == 0){
            f = new AdditionFragment();
        }else if(position == 1){
            f = new SubtractionFragment();
        }else if(position == 2){
            f = new MultiplicationFragment();
        }else{
            f = new DivisionFragment();
        }

        return f;
    }


    @Override
    public int getCount() {
        return 4;
    }
}




