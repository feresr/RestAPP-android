package com.tesis.restapp.restapp.activities.search;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.search.adapters.CategoriesAdapter;

/**
 * Created by feresr on 6/12/14.
 */
public class CategoriesFragment extends Fragment {

    private ListView categoriesListView;
    private CategoriesAdapter categoriesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container,
                false);

        //linkViews(rootView);
        categoriesListView = (ListView) rootView.findViewById(R.id.search_listview);


        categoriesAdapter = new CategoriesAdapter(getActivity());
        categoriesListView.setAdapter(categoriesAdapter);

        categoriesListView.setOnItemClickListener(this);
        return rootView;
    }



}
