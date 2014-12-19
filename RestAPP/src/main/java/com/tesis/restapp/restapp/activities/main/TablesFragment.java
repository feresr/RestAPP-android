package com.tesis.restapp.restapp.activities.main;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.adapters.TablesAdapter;
import com.tesis.restapp.restapp.models.Table;

/**
 * Presents the user with a list of tables to choose from
 */
public class TablesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private MainHandler activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            this.activity = (MainHandler) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement MainHandler");
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tables,container,false);

        ListView tablesListView = (ListView) rootView.findViewById(R.id.tables_listView);
        tablesListView.setAdapter(new TablesAdapter(getActivity(), R.layout.fragment_tables));
        tablesListView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        activity.onTableSelected((Table)parent.getAdapter().getItem(position));
    }
}
