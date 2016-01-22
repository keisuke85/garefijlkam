package it.keisoft.garefijlkam;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.keisoft.garefijlkam.bean.Torneo;
import it.keisoft.garefijlkam.util.BaseActivity;
import it.keisoft.garefijlkam.util.GetInfo;
import it.keisoft.garefijlkam.util.HomeScreenGridViewAdapter;
import it.keisoft.garefijlkam.util.Items;

/**
 * Created by mmarcheselli on 20/01/2016.
 */
public class TournamentsActivity extends BaseActivity {

    private GridView gridView;
    private ArrayList<Items> tornei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        initializeActivity();
    }

    private void initializeActivity(){
        new GetTournamentName().execute("http://keisuke85.altervista.org/gareJudo/getInfo.php");
    }

    private class GetTournamentName extends GetInfo {
        @Override
        protected void onPostExecute(String json) {
            tornei = new ArrayList<Items>();
            try {
                JSONObject jsonObject = (JSONObject) new JSONArray(json).get(0);
                JSONArray jsonArray = jsonObject.getJSONArray("tornei");
                for(int i=0; i<jsonArray.length();i++){
                    Torneo t = new Torneo((JSONObject) jsonArray.get(i));
                    tornei.add(t.toItem());
                }
/*                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("tornei")) {
                        reader.beginArray();
                        while (reader.hasNext()){
                            Torneo t = new Torneo(reader);
                            tornei.add(t.toItem());
                        }
                        reader.endArray();
                    }else {
                        reader.skipValue();
                    }
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
            gridView = (GridView) findViewById(R.id.gridview);
            gridView.setAdapter(new HomeScreenGridViewAdapter(getApplicationContext(), tornei));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), CurrentTournamentActivity.class);
                    intent.putExtra(CurrentTournamentActivity.ID_GARA, ((Items) gridView.getItemAtPosition(i)).getId());
                    startActivity(intent);
                }
            });
        }
    }
}
