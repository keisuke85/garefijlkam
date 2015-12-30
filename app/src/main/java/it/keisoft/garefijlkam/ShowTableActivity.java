package it.keisoft.garefijlkam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import java.util.ArrayList;

import it.keisoft.garefijlkam.bean.TableBean;
import it.keisoft.garefijlkam.util.BaseActivity;
import it.keisoft.garefijlkam.util.GetInfo;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class ShowTableActivity extends BaseActivity {
    public static final String EXTRA_INTENT = "WEIGHT";

    ViewPager tab;
    TabPagerAdapter tabAdapter;
    ActionBar actionBar;
    ArrayList<TableBean> beans = new ArrayList<TableBean>();
    ActionBar.TabListener tabListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String weight = intent.getExtras().getString(EXTRA_INTENT);
        new GetTable().execute("http://www.galileoprogetti.com/fijlkam/incontri.php?cat="+weight);

        getLayoutInflater().inflate(R.layout.show_table, frameLayout);

        setTitle(weight);


        tabAdapter = new TabPagerAdapter(getSupportFragmentManager());

        tab = (ViewPager)findViewById(R.id.pager);
        tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {

                        actionBar = getSupportActionBar();
                        actionBar.setSelectedNavigationItem(position);
                    }
                });
        tab.setAdapter(tabAdapter);

        actionBar = getSupportActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        tabListener = new ActionBar.TabListener() {

            @Override
            public void onTabSelected(ActionBar.Tab tabs, FragmentTransaction ft) {

                tab.setCurrentItem(tabs.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };
    }

    private class GetTable extends GetInfo {
        @Override
        protected void onPostExecute(Void aVoid) {
            ArrayList<String> rounds = new ArrayList<String>();
            String lastRound = "";
            int start = -1;
            String numRound = "";
            if(getStrings().size() > 0){
                for(String s : getStrings()){
                    if(s.toUpperCase().indexOf("SELECT") == 0){
                        continue;
                    }
                    TableBean b = new TableBean(s);
                    if(start == -1){
                        start = Integer.parseInt(b.getNumRound());
                    }
                    if(!lastRound.equalsIgnoreCase(b.getNameRound())){
                        if(beans.size() > 0) {
                            ArrayList <TableBean> tbs = (ArrayList<TableBean>) beans.clone();
                            tabAdapter.getMapBeans().put(numRound, tbs);
                            beans.clear();
                            rounds.add(lastRound);
                            lastRound = b.getNameRound();
                            numRound = b.getNumRound();
                        }else{
                            lastRound = b.getNameRound();
                            numRound = b.getNumRound();
                        }
                    }
                    beans.add(b);
                }
                ArrayList <TableBean> tbs = (ArrayList<TableBean>) beans.clone();
                tabAdapter.getMapBeans().put(numRound, tbs);
                beans.clear();
                rounds.add(lastRound);
                if(start != -1){
                    tabAdapter.setStart(start);
                }
                for(String s: rounds){
                    tabAdapter.notifyDataSetChanged();
                    actionBar.addTab(actionBar.newTab().setText(s).setTabListener(tabListener));
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

