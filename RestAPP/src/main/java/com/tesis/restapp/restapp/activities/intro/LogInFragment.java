package com.tesis.restapp.restapp.activities.intro;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tesis.restapp.restapp.R;

public class LogInFragment extends Fragment {

    private IntroHandler activity;
    private EditText usernameTxt;
    private EditText passwordTxt;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);


        usernameTxt = (EditText) rootView.findViewById(R.id.usernameTxt);
        passwordTxt = (EditText) rootView.findViewById(R.id.passwordTxt);

        Button logInButton = (Button) rootView.findViewById(R.id.login_btn);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                if (!username.equals("") && !password.equals("")) {
                    activity.onLogInButtonClicked(username, password);
                }
            }
        });

        return rootView;
    }

}

