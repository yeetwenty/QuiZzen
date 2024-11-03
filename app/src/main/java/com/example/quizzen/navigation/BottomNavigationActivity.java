package com.example.quizzen.navigation;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
/**
 * Bottom Navigation page
 * @author Jiazhe Lin
 * @version 1.0
 */
import com.example.quizzen.R;
import com.example.quizzen.login.LocaleHelper;
import com.example.quizzen.login.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Bottom navigation activity class for the navigation bar
 * @author Jiazhe Lin
 * @version 1.0
 */
public class BottomNavigationActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrap(newBase));
    }

    @SuppressLint("MissingPermission")
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

                }
            });
    private BottomNavigationView bottomNavigationView;

    //    private Fragment loginFragment;
    private Fragment bookFragment;
    private Fragment flashcardsFragment;
    private Fragment activeFragment;
    private Fragment uploadFragment;
    private Fragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }else{
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        setContentView(R.layout.activity_bottom_navigation);

        bottomNavigationView = findViewById(R.id.navigation_bar);

//        loginFragment = new LoginFragment();
        bookFragment = new BookFragment();
        flashcardsFragment = new FlashcardsFragment();
        uploadFragment = new UploadFragment();
        profileFragment = new ProfileFragment();
        activeFragment = new FlashcardsFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main, activeFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_book:
                        switchFragment(bookFragment);
                        return true;
                    case R.id.navigation_flashcard:
                        switchFragment(flashcardsFragment);
                        return true;
                    case R.id.navigation_upload:
                        switchFragment(uploadFragment);
                        return true;
                    case R.id.navigation_profile:
                        switchFragment(profileFragment);
                        return true;
                }
                return false;
            }
        });
    }


    /**
* Below are the location based procedure for GPS feature
* @author Jiaying Li
 */
    @Override
    public void onLocationChanged(Location location) {
        // Update location
        setLanguageByLocation(location);
    }

    private void setLanguageByLocation(Location location) {

//        setLanguage("zh");

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            // Get user's current address
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                // Get user's country
                String countryCode = addresses.get(0).getCountryCode();
                if (countryCode != null) {
                    // Determine app language based on country
                    switch (countryCode) {
                        case "CN":
                            setLanguage("zh");
                            break;
//                        case "JP":
//                            setLanguage("ja");
//                            break;
                        // Add more cases for other countries as needed
                        default:
                            setLanguage("en");
                            break;
                    }
                } else {
                    // Default to English if country code is unknown
                    setLanguage("en");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLanguage(String newLanguageCode) {

        if(!Locale.getDefault().getLanguage().equals(newLanguageCode)) {
            LocaleHelper.updateLocale(this, newLanguageCode);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    private void switchFragment(Fragment fragment) {
        if (fragment == activeFragment) {
            return;
        }

        if (getSupportFragmentManager().findFragmentByTag(fragment.getClass().getName()) == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_activity_main, fragment, fragment.getClass().getName()).commit();
        }

        getSupportFragmentManager().beginTransaction().hide(activeFragment).show(fragment).commit();

        activeFragment = fragment;
    }
}
