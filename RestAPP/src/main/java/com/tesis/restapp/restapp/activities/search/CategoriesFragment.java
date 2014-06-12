package com.tesis.restapp.restapp.activities.search;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.search.adapters.CategoriesAdapter;

public class CategoriesFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView categoriesListView;
    private CategoriesAdapter categoriesAdapter;
    private SearchActivity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (SearchActivity) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement MainHandler");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container,
                false);

        //linkViews(rootView);
        categoriesListView = (ListView) rootView.findViewById(R.id.search_listview);


        categoriesAdapter = new CategoriesAdapter(getActivity(), R.layout.listview_category);
        categoriesListView.setAdapter(categoriesAdapter);
        categoriesListView.setOnItemClickListener(this);
        //categoriesListView.setOnItemClickListener(this);
        return rootView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        activity.onCategorySelected((int)id);
    }
}
