package com.tesis.restapp.restapp.activities.search;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

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
}
