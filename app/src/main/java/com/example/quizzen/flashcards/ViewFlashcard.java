package com.example.quizzen.flashcards;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizzen.R;

import java.util.ArrayList;
import java.util.List;

/**
 * View flashcard activity class.
 * Displays flashcards in a set and allows user to shuffle and order the cards.
 * Allows user to go to the next or previous flashcard.
 * Allows user to flip the flashcard to see the term or definition.
 * Called from FlashcardsFragment.
 * @author Judy Xie u7397058
 */
public class ViewFlashcard extends AppCompatActivity {
    // flashcard set
    FlashcardSet fcs;
    // list of flashcards
    List<List<String>> cards = new ArrayList<>();
    // ordered flashcards
    List<List<String>> orderedCards;
    // current flashcard
    List<String> current;
    // index of current flashcard
    int index = 0;
    // is flashcard is showing term or definition
    boolean term = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flashcard);

        ImageView shuffle = (ImageView) findViewById(R.id.icon_shuffle);
        ImageView order = (ImageView) findViewById(R.id.icon_order);
        TextView title = (TextView) findViewById(R.id.tv_Flashcard_title);
        TextView text = (TextView) findViewById(R.id.tv_Flashcard_text);
        ImageView prevFlashcard = (ImageView) findViewById(R.id.btn_back);
        ImageView nextFlashcard = (ImageView) findViewById(R.id.btn_next);

        // get the flashcard set intent from FlashcardsFragment
        try {
            fcs = (FlashcardSet) getIntent().getSerializableExtra("FlashcardSet");
            cards = fcs.getCards();
            current = cards.get(index);

            // set the flashcard reader
            title.setText(fcs.getTitle());
            text.setText(current.get(0));

            orderedCards = new ArrayList<>(cards);
        } catch (Exception e) {
            Log.e("ViewFlashcard", "Error getting flashcard set");
        }

        // when previous flashcard button is clicked
        prevFlashcard.setOnClickListener(view -> {
            term = true;
            index--;
            if (index >= 0) {
                current = cards.get(index);
                text.setText(current.get(0));
            }
            // if beginning of list is reached, go to end & show toast
            if (index < 0) {
                index = cards.size() - 1;
                current = cards.get(index);
                text.setText(current.get(0));
                Toast.makeText(this, "You have reached the beginning!", Toast.LENGTH_SHORT).show();
            }
        });

        // when next flashcard button is clicked
        nextFlashcard.setOnClickListener(view -> {
            term = true;
            index++;
            if (index < cards.size()) {
                current = cards.get(index);
                text.setText(current.get(0));
            }
            // if end of list is reached, go to beginning & show toast
            if (index == cards.size()) {
                index = 0;
                current = cards.get(index);
                text.setText(current.get(0));
                Toast.makeText(this, "You have reached the end!", Toast.LENGTH_SHORT).show();
            }
        });

        // when card is clicked, switch between term and definition
        text.setOnClickListener(view -> {
            if (term) { // if current side is term, change to definition
                text.setText(current.get(1));
                term = false;
            } else { // if current side is definition, change to term
                text.setText(current.get(0));
                term = true;
            }
        });

        // shuffle the cards
        shuffle.setOnClickListener(view -> {
            for (int i = 0; i < cards.size(); i++) {
                int random = (int) (Math.random() * cards.size());
                List<String> temp = cards.get(i);
                cards.set(i, cards.get(random));
                cards.set(random, temp);
            }
        });

        // re-order the cards
        order.setOnClickListener(view -> {
            System.out.println(cards);
            System.out.println(orderedCards);
            cards = new ArrayList<>(orderedCards);
        });


    }
}