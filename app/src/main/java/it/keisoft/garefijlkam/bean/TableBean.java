package it.keisoft.garefijlkam.bean;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class TableBean {
    private String categoria;
    private String numRound;
    private String nameRound;
    private String incontro;
    private String bianco;
    private String socBianco;
    private String blu;
    private String socBlu;
    private String tipo;// 0regular 1ripes.
    private String grassetto;//1 il bianco 2 il blu

    private int image = 0;

    public TableBean() {}

    public TableBean(String string){
        String[] field = string.split(",");
        categoria = field[0];
        numRound = field[1];
        nameRound = field[2];
        incontro = field[3];
        bianco = field[4];
        socBianco = field[5];
        blu = field[6];
        socBlu = field[7];
        tipo = field[8];
        grassetto = field[9];
    }

    public String getBianco() {
        return bianco;
    }

    public void setBianco(String bianco) {
        this.bianco = bianco;
    }

    public String getBlu() {
        return blu;
    }

    public void setBlu(String blu) {
        this.blu = blu;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getGrassetto() {
        return grassetto;
    }

    public void setGrassetto(String grassetto) {
        this.grassetto = grassetto;
    }

    public String getIncontro() {
        return incontro;
    }

    public void setIncontro(String incontro) {
        this.incontro = incontro;
    }

    public String getNameRound() {
        return nameRound;
    }

    public void setNameRound(String nameRound) {
        this.nameRound = nameRound;
    }

    public String getNumRound() {
        return numRound;
    }

    public void setNumRound(String numRound) {
        this.numRound = numRound;
    }

    public String getSocBianco() {
        return socBianco;
    }

    public void setSocBianco(String socBianco) {
        this.socBianco = socBianco;
    }

    public String getSocBlu() {
        return socBlu;
    }

    public void setSocBlu(String socBlu) {
        this.socBlu = socBlu;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
