package com.tesis.restapp.restapp.activities.intro;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.MainActivity;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.models.User;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.client.Header;


public class IntroActivity extends Activity implements IntroHandler {

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new IntroFragment())
                    .commit();
        }
        pDialog = new ProgressDialog(this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        if (pDialog != null)
            pDialog.dismiss();
        super.onPause();
    }

    @Override
    public void onLogInButtonClicked() {
        LogInFragment newFragment = new LogInFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    public void onLogInButtonClicked(String username, String password) {
        if (isOnline()) {

            RestAppApiInterface apiInterface = ApiClient.getRestAppApiClient();
            pDialog.setMessage("Loggin in...");
            pDialog.show();
            apiInterface.logIn(username, password, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    if (user != null) {

                        User.setUser(user);

                        for (Header header : response.getHeaders()) {
                            if (header.getValue().contains("laravel_session")) {
                                User.getUser().setToken(header);
                            }
                        }
                        onLoginSuccessful();
                    } else {
                        onInvalidCredentials();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("sda",retrofitError.toString());
                    onServerNotFound();

                }
            });


        } else {
            Toast.makeText(this, "Your network connection is off. Turn it on and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onLoginSuccessful() {

        pDialog.dismiss();
        Intent i = new Intent(this, MainActivity.class);

        startActivity(i);
        finish();

    }

    private void onServerNotFound() {

        pDialog.dismiss();
        Toast.makeText(this, R.string.server_not_found, Toast.LENGTH_SHORT).show();

    }

    private void onInvalidCredentials() {

        pDialog.dismiss();
        Toast.makeText(this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();

    }


}
