package com.android.test.model;

import java.util.ArrayList;

/**
 * Created by Manish on 10/2/17.
 */

public class Data {
    private ArrayList<Entry> entryArrayList = new ArrayList<>();

    public ArrayList<Entry> getEntryArrayList() {
        return entryArrayList;
    }

    public void setEntryArrayList(ArrayList<Entry> entryArrayList) {
        this.entryArrayList = entryArrayList;
    }
}
