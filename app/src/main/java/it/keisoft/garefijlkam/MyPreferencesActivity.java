package it.keisoft.garefijlkam;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import it.keisoft.garefijlkam.util.Constants;

/**
 * Created by mmarcheselli on 12/02/2016.
 */
public class MyPreferencesActivity extends PreferenceActivity {

    private static GoogleCloudMessaging gcm;
    private static Context context;
    private static String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            SwitchPreference switchPreference = (SwitchPreference) findPreference("riceviNotifica");

            if (switchPreference != null) {
                switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                        registerInBackground((boolean)newValue);
                        return true;
                    }
                });
            }
        }
    }

    private static void registerInBackground(final boolean register) {
        new AsyncTask<Void, Void, String>() {
            private ProgressDialog dialog=null;

            protected void onPreExecute() {
                dialog= ProgressDialog.show(context, "Registrazione presso GCM", "Tentativo in corso...", true, false);
            };

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(Constants.SENDER_ID);

                } catch (IOException ex) {
                    return null;
                }
                return regid;
            }

            @Override
            protected void onPostExecute(String regid) {
                dialog.dismiss();
                if (regid!=null) {
                    sendIDToApplication(regid, register ? Constants.REGISTER_URL : Constants.UNREGISTER_URL);
                    //mDisplay.setText(regid);
                }
                else
                    Toast.makeText(context, "Errore: registrazione su GCM non riuscita!", Toast.LENGTH_LONG).toString();
            }
        }.execute();
    }

    private static void sendIDToApplication(String regid, final String actionUrl) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                String regid=params[0];
                try {
                    URL url = new URL(actionUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setReadTimeout(10000);
                    urlConnection.setConnectTimeout(15000);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);
                    String postParameter = "regId=" + regid;

                    OutputStreamWriter os = new OutputStreamWriter(urlConnection.getOutputStream());
                    BufferedWriter writer = new BufferedWriter(os);
                    writer.write(postParameter);
                    writer.flush();
                    writer.close();
                    os.close();
                    urlConnection.connect();
                    int respCode = urlConnection.getResponseCode();

                    urlConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute(regid);
    }
}
