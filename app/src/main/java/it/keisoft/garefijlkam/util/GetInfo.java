package it.keisoft.garefijlkam.util;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class GetInfo extends AsyncTask<String, Void, String> {
    URL url;
    HttpURLConnection urlConnection = null;
    ArrayList<String> strings = new ArrayList<String>();
    BufferedReader in;

    @Override
    protected String doInBackground(String... urls) {
        try {

            Log.i("GareJudo", urls[0]);

            url = new URL(urls[0].replace("+","%2b"));
            urlConnection = (HttpURLConnection) url.openConnection();

            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                strings.add(inputLine);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace(); //If you want further info on failure...
            }
        }
        return strings.toString();
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
    }
}
