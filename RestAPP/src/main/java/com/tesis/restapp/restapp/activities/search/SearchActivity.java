package com.tesis.restapp.restapp.activities.search;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import com.tesis.restapp.restapp.R;

public class SearchActivity extends Activity implements SearchHandler {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new CategoriesFragment())
                    .commit();
        }
    }

    @Override
    public void onCategorySelected(int categoryId) {
        ItemsFragment itemsFragment = new ItemsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        Bundle args = new Bundle();
        args.putInt("category_id", categoryId);
        itemsFragment.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(R.id.container, itemsFragment);
        transaction.commit();
    }

    @Override
    public void onItemSelected(int itemId) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("itemId",itemId);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
