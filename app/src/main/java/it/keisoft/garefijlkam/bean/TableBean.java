package it.keisoft.garefijlkam.bean;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class TableBean {
    private String c_id_gara;
    private String c_id_cat;
    private String c_round;
    private String c_num_inc;
    private String f_rip;
    private String c_win; // 1 vince bianco, 2 vince blu, 0 attesa
    private Atleta bianco;
    private Atleta blu;
   /* c_atl_bia
    t_nome_bia
    t_soc_bia
    c_atl_blu
    t_nome_blu
    t_soc_blu*/


    private int image = 0;

    public TableBean() {}

    public TableBean(JSONObject jsonObject){
        try {
            setC_id_gara(jsonObject.getString("c_id_gara"));
            setC_id_cat(jsonObject.getString("c_id_cat"));
            setC_round(jsonObject.getString("c_round"));
            setC_num_inc(jsonObject.getString("c_num_inc"));
            setF_rip(jsonObject.getString("f_rip"));
            setC_win(jsonObject.getString("c_win"));
            Atleta bianco = new Atleta(jsonObject.getString("c_atl_bia"),jsonObject.getString("t_nome_bia"), null,jsonObject.getString("t_soc_bia"));
            setBianco(bianco);
            Atleta blu = new Atleta(jsonObject.getString("c_atl_blu"),jsonObject.getString("t_nome_blu"), null,jsonObject.getString("t_soc_blu"));
            setBlu(blu);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    };

/*    public TableBean(String string){
        String[] field = string.split(",");
        categoria = field[0];
        numRound = field[1];
        incontro = field[3];
        bianco = field[4];
        socBianco = field[5];
        blu = field[6];
        socBlu = field[7];
        tipo = field[8];
        win = field[9];
    }
*/
 /*   public TableBean(JsonReader reader){
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("c_id_cat")) {
                    setCategoria(reader.nextString());
                }else if (name.equals("c_round")) {
                    setNumRound(reader.nextString());
                }else if (name.equals("c_num_inc")) {
                    setIncontro(reader.nextString());
                }else if (name.equals("f_rip")) {
                    setTipo(reader.nextString());
                }else if (name.equals("t_nome_bia")) {
                    setBianco(reader.nextString());
                }else if (name.equals("t_soc_bia")) {
                    setSocBianco(reader.nextString());
                }else if (name.equals("t_nome_blu")) {
                    setBlu(reader.nextString());
                }else if (name.equals("t_soc_blu")) {
                    setSocBlu(reader.nextString());
                }else if (name.equals("c_win")) {
                    setWin(reader.nextString());
                }else {
                    reader.skipValue();
                }
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    public Atleta getBianco() {
        return bianco;
    }

    public void setBianco(Atleta bianco) {
        this.bianco = bianco;
    }

    public Atleta getBlu() {
        return blu;
    }

    public void setBlu(Atleta blu) {
        this.blu = blu;
    }

    public String getC_id_cat() {
        return c_id_cat;
    }

    public void setC_id_cat(String c_id_cat) {
        this.c_id_cat = c_id_cat;
    }

    public String getC_id_gara() {
        return c_id_gara;
    }

    public void setC_id_gara(String c_id_gara) {
        this.c_id_gara = c_id_gara;
    }

    public String getC_num_inc() {
        return c_num_inc;
    }

    public void setC_num_inc(String c_num_inc) {
        this.c_num_inc = c_num_inc;
    }

    public String getC_round() {
        return c_round;
    }

    public void setC_round(String c_round) {
        this.c_round = c_round;
    }

    public String getC_win() {
        return c_win;
    }

    public void setC_win(String c_win) {
        this.c_win = c_win;
    }

    public String getF_rip() {
        return f_rip;
    }

    public void setF_rip(String f_rip) {
        this.f_rip = f_rip;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
