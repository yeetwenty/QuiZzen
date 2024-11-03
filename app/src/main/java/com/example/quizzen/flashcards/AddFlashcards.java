package com.example.quizzen.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzen.R;
import com.example.quizzen.navigation.BottomNavigationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Add flashcard set.
 * Add flashcards to a flashcard set and save it to the database.
 * @author Judy Xie u739058
 * @version 1.0
 */
public class AddFlashcards extends AppCompatActivity {
    // flashcard set
    private final FlashcardSet fcs = new FlashcardSet();
    // list of flashcards
    private List<List<String>> cards = new ArrayList<>();
    // the set's ID
    String setID;
    // the user's ID
    String userID;
    // whether the set is a favorite
    Boolean fav = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcards);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // get current user
        if (user != null) {
            userID = user.getDisplayName(); // get current user's email
        }

        TextInputEditText title = findViewById(R.id.title_text);
        TextInputEditText term = findViewById(R.id.term_text);
        TextInputEditText definition = findViewById(R.id.definition_text);
        Button saveSet = findViewById(R.id.finishbtn);
        FloatingActionButton addCard = findViewById(R.id.addbtn);
        Switch setFav = findViewById(R.id.sw_set_fav);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            // assign random UUID to setID
            setID = UUID.randomUUID().toString();
        } else {
            // if bundle is not null, get the flashcard set and setID
            FlashcardSet fcs = (FlashcardSet) bundle.getSerializable("fcs");
            setID = bundle.getString("setID");
        }
        // set favorite set?
        setFav.setOnClickListener(view -> {
            fav = setFav.isChecked();
        });

        // button to add a card to the set
        addCard.setOnClickListener(view -> {
            if (title.getText().toString().isEmpty()) title.setError("Please enter a title");
            if (term.getText().toString().isEmpty()) term.setError("Please enter a term");
            if (definition.getText().toString().isEmpty()) definition.setError("Please enter a definition");

            if (!title.getText().toString().isEmpty()
                    && !term.getText().toString().isEmpty()
                    && !definition.getText().toString().isEmpty()) {
                // save card to cards arraylist
                cards.add(Arrays.asList(term.getText().toString(), definition.getText().toString()));
                // clear text fields & refocus on term
                term.getText().clear();
                definition.getText().clear();
                term.requestFocus();

            } else { // not all fields are filled
                Toast.makeText(AddFlashcards.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });

        // button to save the set to database
        saveSet.setOnClickListener(view -> {
            if (!term.getText().toString().isEmpty() && !definition.getText().toString().isEmpty()
                    && !title.getText().toString().isEmpty()) {
                // save set to cards arraylist
                Toast.makeText(AddFlashcards.this, "Saving set...", Toast.LENGTH_SHORT).show();
                cards.add(Arrays.asList(term.getText().toString(), definition.getText().toString()));

                String[] userIDSplit = userID.split("@"); // split display name to get username
                userID = userIDSplit[0];
                // set all information for the flashcard set
                fcs.cards = cards;
                fcs.userID = userID;
                fcs.title = title.getText().toString();
                fcs.setID = setID;
                fcs.favorite = fav;

                // save all the cards to firebase database
                FirebaseDatabase.getInstance().getReference().child("sets").child(setID)
                        .setValue(fcs).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddFlashcards.this, "Set saved!", Toast.LENGTH_SHORT).show();

                                FirebaseDatabase.getInstance().getReference("Users").child(fcs.userID)
                                        .child("Cards").child(setID).setValue(setID);
                                startActivity(new Intent(AddFlashcards.this, BottomNavigationActivity.class));
                            } else {
                                Toast.makeText(AddFlashcards.this, "Error saving set!", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else { // if not all fields are filled
                Toast.makeText(AddFlashcards.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });



    }




}