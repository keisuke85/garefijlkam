package it.keisoft.garefijlkam.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mmarcheselli on 20/01/2016.
 */
public class Classifica {
    private String c_id_gara;
    private String c_cat;
    private String c_pos;
    private Atleta atleta;

    public Classifica(){};

    public Classifica(JSONObject jsonObject){
        try {
            setC_id_gara(jsonObject.getString("c_id_gara"));
            setC_cat(jsonObject.getString("c_cat"));
            setC_pos(jsonObject.getString("c_pos"));
            setAtleta(new Atleta(jsonObject.getString("c_atl"),jsonObject.getString("t_nome"),null,jsonObject.getString("t_soc")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

 /*   public Items toItem(){
        Items i = new Items(getC_id(), getT_nome(), getF_cpl(), R.drawable.icon);
        return i;
    }*/

    public String getC_cat() {
        return c_cat;
    }

    public void setC_cat(String c_cat) {
        this.c_cat = c_cat;
    }

    public Atleta getAtleta() {
        return atleta;
    }

    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }

    public String getC_id_gara() {
        return c_id_gara;
    }

    public void setC_id_gara(String c_id_gara) {
        this.c_id_gara = c_id_gara;
    }

    public String getC_pos() {
        return c_pos;
    }

    public void setC_pos(String c_pos) {
        this.c_pos = c_pos;
    }
}
