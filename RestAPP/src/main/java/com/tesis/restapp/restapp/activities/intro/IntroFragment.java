package com.tesis.restapp.restapp.activities.intro;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tesis.restapp.restapp.R;

public class IntroFragment extends Fragment {

    private IntroHandler activity;

    public IntroFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button logInButton = (Button) rootView.findViewById(R.id.login_btn);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onShowLoginScreenClicked();
            }
        });
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
                "fonts/LeagueGothicRegular.otf");
        ((TextView) rootView.findViewById(R.id.appname)).setTypeface(font);
        ((TextView) rootView.findViewById(R.id.subtitle)).setTypeface(font);

        return rootView;
    }
}