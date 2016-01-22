package it.keisoft.garefijlkam.bean;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import it.keisoft.garefijlkam.R;
import it.keisoft.garefijlkam.util.Items;

/**
 * Created by mmarcheselli on 20/01/2016.
 */
public class Torneo {
    public String c_id;
    public String t_nome;
    public String f_cpl;

    public Torneo(){};

    public Torneo(JSONObject jsonObject){
        try {
            setC_id(jsonObject.getString("c_id_gara"));
            setT_nome(jsonObject.getString("t_nome"));
            setF_cpl(jsonObject.getString("f_cpl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

    public Items toItem(){
        Items i = new Items(getC_id(), getT_nome(), getF_cpl(), R.drawable.icon);
        return i;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getF_cpl() {
        return f_cpl;
    }

    public void setF_cpl(String f_cpl) {
        this.f_cpl = f_cpl;
    }

    public String getT_nome() {
        return t_nome;
    }

    public void setT_nome(String t_nome) {
        this.t_nome = t_nome;
    }
}
