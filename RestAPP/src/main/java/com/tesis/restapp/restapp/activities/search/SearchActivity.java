package com.tesis.restapp.restapp.activities.search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.models.Category;

public class SearchActivity extends Activity implements SearchHandler {

    private ItemsFragment itemsFragment;
    private boolean itemsMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new CategoriesFragment())
                    .commit();
            itemsFragment = new ItemsFragment();
        }
    }


    @Override
    public void onCategorySelected(int categoryId) {
        Bundle b = new Bundle();
        b.putInt(Category.KEY, categoryId);
        itemsFragment.setArguments(b);
        getFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.container, itemsFragment)
                .commit();
        itemsMode = true;
    }

    @Override
    public void onItemSelected(int itemId) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("itemId", itemId);
        setResult(RESULT_OK, returnIntent);
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
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.equals("")) {
               getFragmentManager().popBackStack();
               itemsMode = false;
            } else {
                if (!itemsMode) {
                    Bundle b = new Bundle();
                    b.putString("TEXT", newText);
                    itemsFragment.setArguments(b);
                    getFragmentManager().beginTransaction().addToBackStack(null)
                            .replace(R.id.container, itemsFragment)
                            .commit();
                    itemsMode = true;
                }
                itemsFragment.filterByName(newText);
            }
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            // Do something
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        itemsMode = false;
    }
}
