package com.example.quizzen.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import com.example.quizzen.R;

/**
 * The type Settings fragment.
 *
 * @author Sunny Zhang
 */
public class SettingFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preference, rootKey);
        SwitchPreferenceCompat switchPreference = findPreference("dynamic_color");
        // show the preference only when the device is running Android 12 or higher
        assert switchPreference != null;
        switchPreference.setVisible(true);
        // restart app when switch is toggled
        switchPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            Toast.makeText(getContext(), "Restart needed!", Toast.LENGTH_SHORT).show();
            return true;
        });

        //list preferences mode selection
        ListPreference listPreference = findPreference("dark_mode");

        assert listPreference != null;
        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            switch (newValue.toString()) {
                case "light":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case "dark":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case "battery":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                    break;
                case "system":
                default:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    break;
            }
            return true;
        });

        //list privacy policy and terms of service
        Preference privacyPolicy = findPreference("privacy_policy");
        assert privacyPolicy != null;
        privacyPolicy.setOnPreferenceClickListener(preference -> {
            // https://www.privacypolicies.com/live/ecbbee66-9f5d-4a51-a7d5-f6ccf4fb83c0
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse("https://www.privacypolicies.com/live/ecbbee66-9f5d-4a51-a7d5-f6ccf4fb83c0"));
            startActivity(intent);
            return true;
        });

        Preference termsOfService = findPreference("terms_of_service");
        assert termsOfService != null;
        termsOfService.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse("https://www.termsfeed.com/live/5635868a-f94c-48bf-a607-d495f80c9889"));
            startActivity(intent);
            return true;
        });


    }


}