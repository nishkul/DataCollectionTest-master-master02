package com.android.test.model;

/**
 * Created by root on 9/2/17.
 */

public class Entry {

    private String title;
    private String image;
    private String description;
    private String lat;
    private String lang;
    private String address;
    private int id;
    private int sendtoserver;
    private int category;

    public Entry(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Entry() {
    }

    public int getSendtoserver() {
        return sendtoserver;
    }

    public void setSendtoserver(int sendtoserver) {
        this.sendtoserver = sendtoserver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
