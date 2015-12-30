package it.keisoft.garefijlkam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import it.keisoft.garefijlkam.util.BaseActivity;
import it.keisoft.garefijlkam.util.GetInfo;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class CurrentTournamentActivity extends BaseActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.current_tournament, frameLayout);

        mDrawerList.setItemChecked(position, true);
//        setTitle(listArray[position]);

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                Intent intent = new Intent(getApplication(), ShowTableActivity.class);
                intent.putExtra(ShowTableActivity.EXTRA_INTENT, item);
                startActivity(intent);
            }
        });

        new GetTournamentName().execute("http://www.galileoprogetti.com/fijlkam/torneo.php");
        new GetTournamentWeights().execute("http://www.galileoprogetti.com/fijlkam/categorie.php");
    }

    private class GetTournamentName extends GetInfo{
        @Override
        protected void onPostExecute(Void aVoid) {
            if(getStrings().size() > 0){
                setTitle(getStrings().get(0));
            }else{
                setTitle(listArray[position]);
            }
        }
    }

    private class GetTournamentWeights extends GetInfo{

        @Override
        protected void onPostExecute(Void aVoid) {
            if(getStrings().size() > 0){
                String[] weights = getStrings().get(0).split(",");
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.list_weights_adapter, weights);
                //utilizzo dell'adapter
                listView.setAdapter(adapter);
            }
        }
    }

}
