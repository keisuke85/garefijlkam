package it.keisoft.garefijlkam.util;

/**
 * Created by mmarcheselli on 14/12/2015.
 */
public class Items {
    private String id;
    private String itemName;
    private String itemDesc;
    private long iconId;

    public Items(String id, String itemName, String itemDesc, long iconId) {
        this.id = id;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.iconId = iconId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public long getIconId() {
        return iconId;
    }

    public void setIconId(long iconId) {
        this.iconId = iconId;
    }

}
