package it.keisoft.garefijlkam;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import java.util.List;

import it.keisoft.garefijlkam.bean.Classifica;
import it.keisoft.garefijlkam.bean.TableBean;
import it.keisoft.garefijlkam.bean.Torneo;
import it.keisoft.garefijlkam.util.BaseActivity;
import it.keisoft.garefijlkam.util.Constants;
import it.keisoft.garefijlkam.util.GetInfo;
import it.keisoft.garefijlkam.util.Items;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class RouteActivity extends BaseActivity {
    public static final String ID_GARA = "ID_GARA";
    public static final String ID_ATLETA = "ID_ATLETA";
    private ListView listView;
    private TextView nomeAtl;
    private TextView socAtl;
    private ImageView medalImage;
    ArrayList<TableBean> beans = new ArrayList<TableBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String id_gara = intent.getExtras().getString(ID_GARA);
        final String id_atleta = intent.getExtras().getString(ID_ATLETA);

        getLayoutInflater().inflate(R.layout.route_atleta, frameLayout);

        mDrawerList.setItemChecked(position, true);
//        setTitle(listArray[position]);

        listView = (ListView) findViewById(R.id.listView);
//        nomeAtl = (TextView) findViewById(R.id.atlNome);
        socAtl = (TextView) findViewById(R.id.socNome);
        medalImage = (ImageView) findViewById(R.id.medalImg);

//        new GetTournamentName().execute("http://keisuke85.altervista.org/gareJudo/getInfo.php");
        new GetRoute().execute("http://keisuke85.altervista.org/gareJudo/getInfo.php?id_gara="+id_gara+"&id_atl="+id_atleta);
    }

    private class GetRoute extends GetInfo {

        @Override
        protected void onPostExecute(String json) {
            try {
                JSONObject jsonObject = (JSONObject) new JSONArray(json).get(0);
                int max_round = jsonObject.getInt("max_round");

                JSONArray jsonArray = jsonObject.getJSONArray("podio");
                Classifica classifica = new Classifica((JSONObject) jsonArray.get(0));
//                nomeAtl.setText(classifica.getAtleta().getT_nome());
                socAtl.setText(classifica.getAtleta().getT_soc());
                setTitle(classifica.getAtleta().getT_nome());
                if(classifica.getC_pos() != null && !classifica.getC_pos().equalsIgnoreCase("") && !classifica.getC_pos().equalsIgnoreCase("null")){
                    int idMedal = classifica.getC_pos().equalsIgnoreCase("1") ? R.drawable.oro :
                                   classifica.getC_pos().equalsIgnoreCase("2") ? R.drawable.argento : R.drawable.bronzo;
                    medalImage.setImageResource(idMedal);
                }

                //tabAdapter.setPodio(podio);

                jsonArray = jsonObject.getJSONArray("risultati");
                for(int i=0; i<jsonArray.length(); i++){
                    TableBean b;
                    b = new TableBean((JSONObject) jsonArray.get(i));
                    beans.add(b);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AtlAdapter atlAdapter = new AtlAdapter(getApplicationContext(), R.layout.route_atleta_adapter, beans);
            listView.setAdapter(atlAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class AtlAdapter extends ArrayAdapter<TableBean> {

        private int resource;
        private LayoutInflater inflater;

        public AtlAdapter(Context context, int resourceId, List<TableBean> objects) {
            super(context, resourceId, objects);
            resource = resourceId;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {

            // Recuperiamo l'oggetti che dobbiamo inserire a questa posizione
            final TableBean table = getItem(position);

            ViewHolder holder;

            if (v == null) {
                v = inflater.inflate(resource, parent, false);
                holder = new ViewHolder();
                holder.roundTextView = (TextView) v.findViewById(R.id.round);
                holder.primoTextView = (TextView) v.findViewById(R.id.namePrimo);
                holder.socPrimoTextView = (TextView) v.findViewById(R.id.socPrimo);
                holder.secondoTextView = (TextView) v.findViewById(R.id.nameSecondo);
                holder.socSecondoTextView = (TextView) v.findViewById(R.id.socSecondo);
                holder.resultImageView = (ImageView) v.findViewById(R.id.result);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            String roundText = Constants.ROUNDS[Constants.ROUNDS.length-Integer.parseInt(table.getC_round())-1];
            if(table.getF_rip().equalsIgnoreCase("1")){
                roundText += " (Ripescaggio)";
            }
            holder.roundTextView.setText(roundText);
            if(table.getBianco() != null) {
                holder.primoTextView.setText(table.getBianco().getT_nome());
                holder.socPrimoTextView.setText(table.getBianco().getT_soc());
            }
            if(table.getBlu() != null) {
                holder.secondoTextView.setText(table.getBlu().getT_nome());
                holder.socSecondoTextView.setText(table.getBlu().getT_soc());
            }
            if(table.getImage() != 0) {
                holder.resultImageView.setImageResource(table.getImage());
            }
            return v;
        }

    }

    private static class ViewHolder {
        TextView roundTextView;
        TextView primoTextView;
        TextView socPrimoTextView;
        TextView secondoTextView;
        TextView socSecondoTextView;
        ImageView resultImageView;
    }
}
