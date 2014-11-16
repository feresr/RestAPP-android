package com.tesis.restapp.restapp.activities.intro;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tesis.restapp.restapp.R;
import com.tesis.restapp.restapp.activities.main.MainActivity;
import com.tesis.restapp.restapp.api.ApiClient;
import com.tesis.restapp.restapp.api.RestAppApiInterface;
import com.tesis.restapp.restapp.api.response.ApiResponse;
import com.tesis.restapp.restapp.database.DatabaseHandler;
import com.tesis.restapp.restapp.models.User;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

public class LogInFragment extends Fragment {

    private IntroHandler activity;
    private EditText usernameTxt;
    private EditText passwordTxt;
    ProgressDialog pDialog;
    private Boolean dialogShowing = false;
    public LogInFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (IntroHandler) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IntroHandler");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        if (savedInstanceState != null){
            dialogShowing =  savedInstanceState.getBoolean("DIALOG");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);


        usernameTxt = (EditText) rootView.findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) rootView.findViewById(R.id.passwordTxt);
        if (dialogShowing) {
            pDialog.setMessage("Logging in...");
            pDialog.show();
        }
        Button logInButton = (Button) rootView.findViewById(R.id.login_btn);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                if (!username.equals("") && !password.equals("")) {
                    onLogInButtonClicked(username, password);
                }
            }
        });

        return rootView;
    }

    public void onLogInButtonClicked(String username, String password) {
        if (isOnline()) {
            RestAppApiInterface apiInterface = ApiClient.getRestAppApiClient(getActivity());
            pDialog.setMessage("Logging in...");
            pDialog.show();
            apiInterface.logIn(username, password, new Callback<ApiResponse>() {
                @Override
                public void success(ApiResponse login, Response response) {
                    pDialog.dismiss();
                    if (login.wasSuccessful()) {
                        final DatabaseHandler db = new DatabaseHandler(getActivity().getApplicationContext());
                        User user = login.getUser();
                        for (Header header : response.getHeaders()) {
                            if (header.getValue().contains("laravel_session")) {
                                user.setToken(header.getValue());
                            }
                        }
                        db.addUser(user);
                        if (User.getUser(getActivity().getApplicationContext()).getToken() != null) {
                            activity.onSuccessfulLogin();
                        } else {
                            Toast.makeText(getActivity(), R.string.missing_token, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.e("sda", retrofitError.toString());
                    if (retrofitError.getKind() == RetrofitError.Kind.HTTP) {
                        Toast.makeText(getActivity(), R.string.http_error, Toast.LENGTH_SHORT).show();
                    }
                    if (retrofitError.getKind() == RetrofitError.Kind.NETWORK) {
                        Toast.makeText(getActivity(), R.string.server_not_found, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Your network connection is off. Turn it on and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("DIALOG", pDialog.isShowing());
        if(pDialog.isShowing()) {
            pDialog.dismiss();
        }
        super.onSaveInstanceState(outState);
    }
}