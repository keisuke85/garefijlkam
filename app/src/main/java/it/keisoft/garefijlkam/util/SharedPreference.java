package it.keisoft.garefijlkam.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.keisoft.garefijlkam.R;
import it.keisoft.garefijlkam.bean.Atleta;

/**
 * Created by mmarcheselli on 27/01/2016.
 */
public class SharedPreference {

    public static final String PREFS_NAME = "GF";

    public SharedPreference() {
        super();
    }
/**********************ATLETI******************************/
    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Atleta> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

//            Gson gson = new Gson();
        String jsonFavorites = "[";//gson.toJson(favorites);
        for(int i=0; i<favorites.size();i++){
            jsonFavorites += favorites.get(i).toString() + ",";
        }
        if(jsonFavorites.lastIndexOf(",") == jsonFavorites.length()-1) {
            jsonFavorites = jsonFavorites.substring(0, jsonFavorites.length() - 1);
        }
        jsonFavorites += "]";

        editor.putString(Constants.ATLETA_FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Atleta atleta) {
        List<Atleta> favorites = getFavorites(context, Constants.ATLETA_FAVORITES);
        if (favorites == null)
            favorites = new ArrayList<Atleta>();
        favorites.add(atleta);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Atleta atleta) {
        ArrayList<Atleta> favorites = getFavorites(context, Constants.ATLETA_FAVORITES);
        if (favorites != null) {
            favorites.remove(atleta);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList getFavorites(Context context, String chiave) {
        SharedPreferences settings;
        List favorites = new ArrayList<>();

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
/*        SharedPreferences.Editor editor = settings.edit();
        editor.remove(chiave);
        editor.apply();*/

        if (settings.contains(chiave)) {
            String jsonFavorites = settings.getString(chiave, null);
            switch (chiave) {
                case Constants.ATLETA_FAVORITES:
                    try {
                        JSONArray jsonArray = new JSONArray(jsonFavorites);

                        if (jsonArray != null) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                favorites.add(new Atleta((JSONObject) jsonArray.get(i)));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    favorites = new ArrayList<String>(Arrays.asList(jsonFavorites.split(",")));
                    break;
            }
//            Gson gson = new Gson();
//            Atleta[] favoriteItems = gson.fromJson(jsonFavorites, Atleta[].class);

//            favorites = Arrays.asList(favoriteItems);
//            favorites = new ArrayList<Atleta>(favorites);
        } else
            return null;

        return (ArrayList) favorites;
    }


    /**********************Stringhe******************************/
    public void saveFavorites(Context context, String chiave, List<String> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

//            Gson gson = new Gson();
        String jsonFavorites = "";//gson.toJson(favorites);
        for(int i=0; i<favorites.size();i++){
            jsonFavorites += favorites.get(i).toString() + ",";
        }
        if(jsonFavorites.lastIndexOf(",") == jsonFavorites.length()-1) {
            jsonFavorites = jsonFavorites.substring(0, jsonFavorites.length() - 1);
        }
        //jsonFavorites += "]";

        editor.putString(chiave, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, String chiave, String favorito) {
        List<String> favorites = getFavorites(context, chiave);
        if (favorites == null)
            favorites = new ArrayList<String>();
        favorites.add(favorito);
        saveFavorites(context,chiave, favorites);
    }

    public void removeFavorite(Context context, String chiave, String favorito) {
        ArrayList<String> favorites = getFavorites(context, chiave);
        if (favorites != null) {
            favorites.remove(favorito);
            saveFavorites(context, chiave, favorites);
        }
    }

/*    public ArrayList<String> getFavorites(Context context, String chiave) {
        SharedPreferences settings;
        List<String> favorites = new ArrayList<>();

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
/*        SharedPreferences.Editor editor = settings.edit();
        editor.remove(FAVORITES);
        editor.apply();

        if (settings.contains(chiave)) {
            String jsonFavorites = settings.getString(chiave, null);
            favorites = new ArrayList<String>(Arrays.asList(jsonFavorites.split(",")));
        } else
            return null;

        return (ArrayList<String>) favorites;
    }*/

}
