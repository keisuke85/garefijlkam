package it.keisoft.garefijlkam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import it.keisoft.garefijlkam.bean.Torneo;
import it.keisoft.garefijlkam.util.BaseActivity;
import it.keisoft.garefijlkam.util.GetInfo;
import it.keisoft.garefijlkam.util.Items;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class CurrentTournamentActivity extends BaseActivity {
    public static final String ID_GARA = "ID_GARA";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String id_gara = intent.getExtras().getString(ID_GARA);

        getLayoutInflater().inflate(R.layout.current_tournament, frameLayout);

        mDrawerList.setItemChecked(position, true);
//        setTitle(listArray[position]);

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView) view).getText().toString();
                Intent intent = new Intent(getApplication(), ShowTableActivity.class);
                intent.putExtra(ShowTableActivity.WEIGHT, item);
                intent.putExtra(ShowTableActivity.ID_GARA, id_gara);
                startActivity(intent);
            }
        });

//        new GetTournamentName().execute("http://keisuke85.altervista.org/gareJudo/getInfo.php");
        new GetTournamentWeights().execute("http://keisuke85.altervista.org/gareJudo/getInfo.php?id_gara="+id_gara);
    }

    private class GetTournamentName extends GetInfo{
        @Override
        protected void onPostExecute(String json) {
/*            try {
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("t_nome")) {
                        setTitle(reader.nextString());
                    }else {
                        reader.skipValue();
                    }
                }
            } catch (IOException e) {
                setTitle(listArray[position]);
                e.printStackTrace();
            }
        */
 /*           if(getStrings().size() > 0){
                setTitle(getStrings().get(0));
            }else{
                setTitle(listArray[position]);
            }*/
        }
    }

    private class GetTournamentWeights extends GetInfo {

        @Override
        protected void onPostExecute(String json) {
            ArrayList<String> weights = new ArrayList<String>();
            try {
                JSONObject jsonObject = (JSONObject) new JSONArray(json).get(0);
                setTitle(jsonObject.getString("t_nome"));
                JSONArray jsonArray = jsonObject.getJSONArray("categorie");
                for (int i = 0; i < jsonArray.length(); i++) {
                    weights.add(((JSONObject) jsonArray.get(i)).getString("c_id_cat"));
                }
/*            try {
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("c_id_cat")) {
                        weights.add(reader.nextString());
                    }else {
                        reader.skipValue();
                    }
                }
            } catch (IOException e) {
                setTitle(listArray[position]);
                e.printStackTrace();
            }
*///            if(getStrings().size() > 0){
//                String[] weights = getStrings().get(0).split(",");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_weights_adapter, weights);
                //utilizzo dell'adapter
                listView.setAdapter(adapter);
//            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
