package com.example.quizzen.navigation;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.quizzen.R;
import com.example.quizzen.book.BookRead;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Book Text Fragment class.
 * Displays a list of JSON books that the user can pick to read from.
 * @author Judy Xie u7397058， Mengyao Xu u7465786
 */
public class BookTextFragment extends Fragment {

    private ArrayList<String> bookText = new ArrayList<>();

    public BookTextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_text, container, false);
        ListView listView = view.findViewById(R.id.textbook_list);

        // Load book titles from JSON
        ArrayList<String> bookTitles = loadBookTitlesFromJson();

        // Display the book titles in a list view
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookTitles);
        listView.setAdapter(itemsAdapter);

        // When the user clicks on a book, open the book
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String bookTitle = bookTitles.get(position);
            Intent intent = new Intent(getContext(), BookRead.class);
            intent.putExtra("BookTitle", bookTitle);
            startActivity(intent);
        });

        return view;
    }

    private ArrayList<String> loadBookTitlesFromJson() {
        ArrayList<String> bookTitles = new ArrayList<>();

        try {
            AssetManager assetManager = requireContext().getAssets();
            InputStream inputStream = assetManager.open("otherbooks.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();

            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                String bookTitle = jsonArray.getString(i);
                bookTitles.add(bookTitle);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return bookTitles;
    }
}