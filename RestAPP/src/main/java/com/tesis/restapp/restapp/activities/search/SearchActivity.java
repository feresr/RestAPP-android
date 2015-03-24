package com.tesis.restapp.restapp.activities.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.models.Category;

public class SearchActivity extends ActionBarActivity implements SearchHandler {

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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemsFragment = new ItemsFragment();
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
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

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
