package it.keisoft.garefijlkam.bean;

import org.json.JSONException;
import org.json.JSONObject;

import it.keisoft.garefijlkam.R;
import it.keisoft.garefijlkam.util.Items;

/**
 * Created by mmarcheselli on 20/01/2016.
 */
public class Atleta {
    public String c_atl;
    public String t_nome;
    public String c_soc;
    public String t_soc;

    public Atleta(){};

    public Atleta(String c_atl, String t_nome, String c_soc, String t_soc) {
        this.c_atl = c_atl;
        this.c_soc = c_soc;
        this.t_nome = t_nome;
        this.t_soc = t_soc;
    }

    public Atleta(JSONObject jsonObject) {
        try {
            setC_atl(jsonObject.getString("c_atl"));
            setC_soc(jsonObject.getString("c_soc"));
            setT_nome(jsonObject.getString("t_nome"));
            setT_soc(jsonObject.getString("t_soc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "{'c_atl':'" + c_atl + "','c_soc':'" + c_soc + "','t_nome':'" + t_nome + "','t_soc':'" + t_soc + "'}";
    }

    @Override
    public boolean equals(Object o) {
        return o != null ? getC_atl().equalsIgnoreCase(((Atleta) o).getC_atl()) : false;
//        return super.equals(o);
    }

    /*   @Override
    public boolean equals(Object o) {
        if(o!= null) {
            return this.c_atl.equalsIgnoreCase(((Atleta) o).getC_atl());
        }else{
            return false;
        }
    }*/
/*   public Atleta(JSONObject jsonObject){
        try {
            setC_id(jsonObject.getString("c_id_gara"));
            setT_nome(jsonObject.getString("t_nome"));
            setF_cpl(jsonObject.getString("f_cpl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };*/

 /*   public Items toItem(){
        Items i = new Items(getC_id(), getT_nome(), getF_cpl(), R.drawable.icon);
        return i;
    }*/

    public String getC_atl() {
        return c_atl;
    }

    public void setC_atl(String c_atl) {
        this.c_atl = c_atl;
    }

    public String getC_soc() {
        return c_soc;
    }

    public void setC_soc(String c_soc) {
        this.c_soc = c_soc;
    }

    public String getT_nome() {
        return t_nome;
    }

    public void setT_nome(String t_nome) {
        this.t_nome = t_nome;
    }

    public String getT_soc() {
        return t_soc;
    }

    public void setT_soc(String t_soc) {
        this.t_soc = t_soc;
    }
}
