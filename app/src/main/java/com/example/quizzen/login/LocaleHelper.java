package com.example.quizzen.login;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

public class LocaleHelper {
    private static final String SHARED_PREF_LANGUAGE = "language_pref";
    private static final String KEY_LANGUAGE = "key_language";

    public static Context updateLocale(Context context, String languageCode) {
        saveLanguage(context, languageCode);

        return setLanguage(context, languageCode);
    }

    // Saves the language preference to shared preferences.
    private static void saveLanguage(Context context, String languageCode) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LANGUAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LANGUAGE, languageCode);
        editor.apply();
    }

    // Retrieves the saved language from shared preferences.
    private static String getSavedLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_LANGUAGE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LANGUAGE, Locale.getDefault().getLanguage());
    }

    // Create a new configuration with the specified language code and updating the resources.
    public static Context setLanguage(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        config.setLocales(new LocaleList(locale));

        return context.createConfigurationContext(config);
    }

    // Wraps the context with the updated language configuration.
    public static ContextWrapper wrap(Context context) {
        String languageCode = getSavedLanguage(context);
        return new ContextWrapper(setLanguage(context, languageCode));
    }
}
