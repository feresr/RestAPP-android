package com.tesis.restapp.restapp.activities.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.MainActivity;

public class IntroActivity extends FragmentActivity implements IntroHandler {

    LogInFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        loginFragment = (LogInFragment) getSupportFragmentManager().findFragmentByTag(LogInFragment.class.getName());
        if (loginFragment == null) {
            loginFragment = new LogInFragment();
        }

        //If it's the first time the activity is started. Add IntroFragment to it.
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new IntroFragment())
                    .commit();
        }
        // STRICT MODE
        /*StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());*/
    }

    @Override
    public void onShowLoginScreenClicked() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
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
