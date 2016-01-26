package it.keisoft.garefijlkam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.keisoft.garefijlkam.bean.Classifica;
import it.keisoft.garefijlkam.bean.TableBean;
import it.keisoft.garefijlkam.util.BaseActivity;
import it.keisoft.garefijlkam.util.Constants;
import it.keisoft.garefijlkam.util.GetInfo;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class ShowTableActivity extends BaseActivity {
    public static final String WEIGHT = "WEIGHT";
    public static final String ID_GARA = "ID_GARA";

    ViewPager tab;
    TabPagerAdapter tabAdapter;
    ActionBar actionBar;
    ArrayList<TableBean> beans = new ArrayList<TableBean>();
    ActionBar.TabListener tabListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String weight = intent.getExtras().getString(WEIGHT);
        String id_gara = intent.getExtras().getString(ID_GARA);

        new GetTable().execute("http://keisuke85.altervista.org/gareJudo/getInfo.php?id_gara="+id_gara + "&id_cat="+weight);

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

        protected void onPostExecute(String json) {
            try {
                JSONObject jsonObject = (JSONObject) new JSONArray(json).get(0);
                int max_round = jsonObject.getInt("max_round");

                JSONArray jsonArray = jsonObject.getJSONArray("podio");
                ArrayList<Classifica> podio = new ArrayList<>();
                for(int i=0; i<jsonArray.length(); i++){
                    Classifica classifica = new Classifica((JSONObject) jsonArray.get(i));
                    podio.add(classifica);
                }
                tabAdapter.setPodio(podio);

                jsonArray = jsonObject.getJSONArray("risultati");
                String lastRound = "";
                for(int i=0; i<jsonArray.length(); i++){
                    TableBean b = new TableBean((JSONObject) jsonArray.get(i));
                    if(!lastRound.equalsIgnoreCase(b.getC_round())){
                        if(beans.size() > 0) {
                            ArrayList <TableBean> tbs = (ArrayList<TableBean>) beans.clone();
                            tabAdapter.getMapBeans().put(lastRound, tbs);
                            beans.clear();
                            lastRound = b.getC_round();
                        }else{
                            lastRound = b.getC_round();
                        }
                    }
                    beans.add(b);
                }
                ArrayList <TableBean> tbs = (ArrayList<TableBean>) beans.clone();
                tabAdapter.getMapBeans().put(lastRound, tbs);
                beans.clear();
//                tabAdapter.setStart(0);
                for(int i=(Constants.ROUNDS.length - max_round - 1); i<Constants.ROUNDS.length; i++){
                    tabAdapter.notifyDataSetChanged();
                    actionBar.addTab(actionBar.newTab().setText(Constants.ROUNDS[i]).setTabListener(tabListener));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            ArrayList<String> rounds = new ArrayList<String>();
/*            String lastRound = "";
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
//                rounds.add(lastRound);
                if(start != -1){
                    tabAdapter.setStart(start);
                }
                for(int i=0; i<rounds.length; i++){
                    tabAdapter.notifyDataSetChanged();
                    actionBar.addTab(actionBar.newTab().setText(rounds[i]).setTabListener(tabListener));
                }
            }*/
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

