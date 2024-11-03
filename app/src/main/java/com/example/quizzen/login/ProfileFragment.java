package com.example.quizzen.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import com.example.quizzen.R;
import com.example.quizzen.navigation.FlashcardsFragment;
import com.example.quizzen.navigation.FavoriteBook;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private boolean userIsLoggedIn;

    /**
     * Instantiates a new Profile fragment.
     */
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userIsLoggedIn the user is logged in
     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance(boolean userIsLoggedIn) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putBoolean("userIsLoggedIn", userIsLoggedIn);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userIsLoggedIn = getArguments().getBoolean("userIsLoggedIn");
        }
        if (savedInstanceState != null) {
            userIsLoggedIn = savedInstanceState.getBoolean("userIsLoggedIn");
        }
        if (!newInstance(userIsLoggedIn).equals(this)) {
            View rootView = getView();
            if (rootView != null) {
                rootView.findViewById(R.id.user_logout).setVisibility(View.GONE);
                rootView.findViewById(R.id.user_ebook).setVisibility(View.GONE);
                rootView.findViewById(R.id.user_flashcard).setVisibility(View.GONE);
                TextView usernameField = rootView.findViewById(R.id.user_mail);
                if (usernameField != null) {
                    usernameField.setText(R.string.user_not_login);
                    CardView userInfo = rootView.findViewById(R.id.user_info);
                    userInfo.setOnClickListener(v -> launchLogin());
                }
            }
        } else {
            View rootView = getView();
            if (rootView != null) {
                TextView usernameField = rootView.findViewById(R.id.user_mail);
                if (usernameField != null) {
                    usernameField.setText(AuthUtil.getEmail());
                }
                rootView.findViewById(R.id.user_logout).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.user_ebook).setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.user_flashcard).setVisibility(View.VISIBLE);
                Button logoutButton = rootView.findViewById(R.id.user_logout);
                if (logoutButton != null) {
                    logoutButton.setOnClickListener(v -> {
                        AuthUtil.logout();
                        userIsLoggedIn = false;
                        getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
                    });
                }
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        // Login/Sign up textview
        TextView loginTextV = view.findViewById(R.id.user_mail);
        loginTextV.setOnClickListener(v -> {
            launchLogin();
        });

        // Your Favourite Book button
        Button Ebutton = view.findViewById(R.id.user_ebook);
        Ebutton.setOnClickListener(v -> {
            if (userIsLoggedIn) {
                Intent intent = new Intent(getActivity(), FavoriteBook.class);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Please login to access your favourite eBook.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Your FlashCards button
        Button flashcardsButton = view.findViewById(R.id.user_flashcard);
        // by Judy Xie
        flashcardsButton.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() != null) {
                DatabaseReference fb = FirebaseDatabase.getInstance().getReference();
                fb.child("flashcards").orderByChild("favorite").equalTo(true).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        StringBuilder favlist = new StringBuilder();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            if (child.child("favorite").getValue().equals(true)) {
                                favlist.append(Objects.requireNonNull(snapshot.child("title").getValue()));
                                Log.d("favlist: ", favlist.toString());
                            }
                        }
                        if (favlist.toString().equals(""))
                            Toast.makeText(getActivity(), "You have no favorite flashcards", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getActivity(), "Your favorite flashcards are: " + favlist, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("error: ", error.toString());
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please login first", Toast.LENGTH_SHORT).show();
            }
        });

        // Settings button
        Button settingsButton = view.findViewById(R.id.user_settings);
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        });

        // Logout button
        Button logoutButton = view.findViewById(R.id.user_logout);
        logoutButton.setOnClickListener(v -> {
            signOut();
        });

        updateUI();

        return view;
    }

    private final ActivityResultLauncher<Intent> loginLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // Successfully signed in
                    userIsLoggedIn = true;
                    updateUsername();
                    // Update this to Flashcards system, please don't remove!
                    FlashcardsFragment.loggedIn = true;

                    // Suppose the newDisplayName is the email, replace with your actual logic
                    String newDisplayName = AuthUtil.getEmail();
                    AuthUtil.updateDisplayName(newDisplayName);

                    getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
                } else {
                    // ...
                    if (result.getIdpResponse() == null) {
                        // user cancelled login
                        Toast.makeText(getContext(), "Login cancelled", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Login failed, please try again", Toast.LENGTH_SHORT).show();
                        Log.e("Login", "Login failed", result.getIdpResponse().getError());
                    }
                }
            }
    );

    private void launchLogin() {
        Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()))
                .build();

        loginLauncher.launch(signInIntent);
    }

    private void updateUsername() {
        View rootView = getView();
        if (rootView != null) {
            TextView usernameTextView = rootView.findViewById(R.id.user_mail);
            if (usernameTextView != null) {
                String username = AuthUtil.getEmail(); // Replace with your logic to get the username
                usernameTextView.setText(username);
            }
        }
    }


    private void updateUI() {
        View rootView = getView();
        if (rootView != null) {
            TextView usernameField = rootView.findViewById(R.id.user_mail);
            if (usernameField != null) {
                if (userIsLoggedIn) {
                    usernameField.setText(AuthUtil.getEmail());
                } else {
                    usernameField.setText(R.string.user_not_login);
                    CardView userInfo = rootView.findViewById(R.id.user_info);
                    userInfo.setOnClickListener(v -> launchLogin());
                }
            }

        }

    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(task -> {
                    Log.d(String.valueOf(RESULT_OK), "User logged out");
                    Toast.makeText(getContext(), "User logged out", Toast.LENGTH_SHORT).show();
                    // Update this to Flashcards system, please don't remove!
                    FlashcardsFragment.loggedIn = false;
                    userIsLoggedIn = false;
                    updateUI();
                });
    }
}

