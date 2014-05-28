package com.tesis.restapp.restapp.activities.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.adapters.TablesAdapter;

/**
 * Created by feresr on 5/28/14.
 */
public class TablesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tables,container,false);

        ListView tablesListView = (ListView) rootView.findViewById(R.id.tables_listView);
        tablesListView.setAdapter(new TablesAdapter(getActivity(), R.layout.fragment_tables));

        return rootView;
    }
}
