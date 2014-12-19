package com.tesis.restapp.restapp.activities.intro;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.MainActivity;

public class IntroActivity extends Activity implements IntroHandler {

    LogInFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        loginFragment = (LogInFragment) getFragmentManager().findFragmentByTag(LogInFragment.class.getName());
        if (loginFragment == null) {
            loginFragment = new LogInFragment();
        }

        //If it's the first time the activity is started. Add IntroFragment to it.
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new IntroFragment())
                    .commit();
        }
    }

    @Override
    public void onShowLoginScreenClicked() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, loginFragment, LogInFragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSuccessfulLogin() {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
