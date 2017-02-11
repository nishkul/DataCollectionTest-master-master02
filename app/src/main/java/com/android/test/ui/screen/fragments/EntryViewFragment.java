package com.android.test.ui.screen.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.test.R;
import com.android.test.adaptor.EntryAdaptor;
import com.android.test.database.EntryHelper;
import com.android.test.model.Entry;

import java.util.ArrayList;

/**
 * Created by Manish on 9/2/17.
 */

public class EntryViewFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Entry> entryArrayList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.entry_layout, container, false);
        init(view);

        setData();
        return view;
    }

    private void init(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
    }

    private void setData() {

        EntryHelper entryHelper = new EntryHelper(getActivity());
        entryArrayList = new ArrayList<>();
        entryArrayList = entryHelper.getAllEntry();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        EntryAdaptor entryAdaptor = new EntryAdaptor(getActivity(), entryArrayList);
        recyclerView.setAdapter(entryAdaptor);

    }

}
