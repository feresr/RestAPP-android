package com.tesis.restapp.restapp.activities.search;

import android.app.Activity;
import android.os.Bundle;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.intro.IntroFragment;

/**
 * Created by feresr on 6/12/14.
 */
public class SearchActivity extends Activity {
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
}
