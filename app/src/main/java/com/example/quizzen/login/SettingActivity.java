package com.example.quizzen.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.quizzen.R;

public class SettingActivity extends AppCompatActivity {
    //connect with the SettingFragment, which make the Setting button work in the profile page
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new SettingFragment())
                    .commit();
        }
    }
}