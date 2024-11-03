package com.example.quizzen.login;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * The type Auth util.
 */
public class AuthUtil {

    private static final String RESULT_OK = "AuthUtil";
    private AuthUtil() {}

    /**
     * User logged in boolean.
     *
     * @return the boolean
     */
    public static boolean userLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }



    /**
     * Gets email.
     *
     * @return the email
     */
    public static String getEmail() {
            FirebaseAuth FbAuth = FirebaseAuth.getInstance();
            FirebaseUser FbUser = FbAuth.getCurrentUser();

            if (FbUser != null) {
                return FbUser.getEmail();
            } else {
                return null;
            }
        }

    /**
     * Logout.
     */
    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }





    /**
     * Update display name.
     *
     * @param displayName the display name
     */
    public static void updateDisplayName(String displayName) {
        if (!userLoggedIn()) {
            Log.d(RESULT_OK, "setDisplayName: User not logged in");
            return;
        }
        Log.d(RESULT_OK, "setDisplayName: " + displayName);
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(
                new com.google.firebase.auth.UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build()
        );
    }

}
