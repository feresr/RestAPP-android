package com.tesis.restapp.restapp.activities.search;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.search.adapters.ItemsAdapter;

public class ItemsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView itemsListView;
    private ItemsAdapter itemsAdapter;
    private int category_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search,container,false);
        category_id = getArguments().getInt("category_id");

        itemsListView = (ListView) rootView.findViewById(R.id.search_listview);
        itemsAdapter = new ItemsAdapter(getActivity(), R.layout.listview_order_item , category_id);
        itemsListView.setAdapter(itemsAdapter);
        itemsListView.setOnItemClickListener(this);

        return rootView;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
