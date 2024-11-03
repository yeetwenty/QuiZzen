package com.example.quizzen.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.quizzen.R;

/**
 * Login fragment class.
 * @author Jiazhe Lin
 * @version 1.0
 */
public class LoginFragment extends Fragment {

    public LoginFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        // Initialize views and perform any necessary setup
        return rootView;
    }
}
