package it.keisoft.garefijlkam.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;

import it.keisoft.garefijlkam.CurrentTournamentActivity;
import it.keisoft.garefijlkam.MainActivity;
import it.keisoft.garefijlkam.R;
import it.keisoft.garefijlkam.TournamentsActivity;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class BaseActivity extends AppCompatActivity {

    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    private DrawerLayout mDrawerLayout;

    protected String[] listArray;
    protected ArrayList<Items> _items;

    protected static int position;

    private static boolean isLaunch = true;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_base_layout);

        listArray = new String[]{getString(R.string.title_Home),
                getString(R.string.title_tournaments),
                getString(R.string.action_settings)};

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        _items = new ArrayList<>();
        _items.add(new Items("0£", getString(R.string.title_tournaments), null, R.drawable.icon));
//        _items.add(new Items(getString(R.string.action_settings), null, android.R.drawable.ic_menu_send));

        View header = getLayoutInflater().inflate(R.layout.list_view_header_layout, null);
        mDrawerList.addHeaderView(header);

        mDrawerList.setAdapter(new NavigationDrawerListAdapter(this, _items));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openActivity(i);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
//                R.mipmap.ic_launcher,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle(listArray[position]);
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        if(isLaunch){
            isLaunch = false;
            // apro direttamente la lista tornei
            openActivity(1);
        }
    }


    protected void openActivity(int position){
        mDrawerLayout.closeDrawer(mDrawerList);
        BaseActivity.position = position;
        Intent intent;

        switch (position){
            case 0:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case 1:
                intent = new Intent(this, TournamentsActivity.class);
//                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "158");
                startActivity(intent);
                break;
/*            case 2:
                intent = new Intent(this, ShowPage.class);
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "servizi.html");
                startActivity(intent);
                break;
            case 3:
                startActivity(new Intent(this, GalleryActivity.class));
//                intent = new Intent(this, GalleryActivity.class);
//                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "41");
//                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, ShowPage.class);
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "12");
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(this, ShowPage.class);
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "storia.html");
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(this, ShowPage.class);
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "268");
                startActivity(intent);
                break;
            case 7:
                intent = new Intent(this, ShowPage.class);
                //intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "26");
                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "contatti.html");
                startActivity(intent);
                break;
            case 8:
//                getFragmentManager().beginTransaction().replace(android.R.id.content,
//                        new SettingsActivity()).commit();
                startActivity(new Intent(this, SettingsActivity.class));
//                    intent = new Intent(this, SettingsActivity.class);
                //intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "26");
//                intent.putExtra(ShowPage.ARG_SECTION_NUMBER, "contatti.html");
//                startActivity(intent);
                break;
*/            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawer(mDrawerList);
        }else{
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }

}
