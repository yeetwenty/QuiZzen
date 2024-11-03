package com.example.quizzen.navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.res.AssetManager;

import com.example.quizzen.R;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class FavoriteBook extends AppCompatActivity {

    private SearchView searchView;
    private ImageView ivBookCover1;
    private ImageView ivBookCover2;
    private ImageView ivBookCover3;
    private ImageView ivBookCover4;
    private ImageView ivBookCover5;
    private ImageView ivBookCover6;
    private ImageView ivBookCover7;
    private ImageView ivBookCover8;
    private ImageView ivBookCover9;

    private LinearLayout coverContainer;

    private HashMap<String, String> booklinks;


    public void loadBookLinksFromCsv(Context context) {
        // This function loads book links from a CSV file and stores them in a HashMap.
        // The context is passed as a parameter to access the assets.

        booklinks = new HashMap<>();

        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("book_link.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String bookTitle = parts[0].trim();
                    String bookLink = parts[1].trim();
                    booklinks.put(bookTitle, bookLink);
                }
            }

            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Set up the search view
    private void setupSearchView(View rootView) {
        searchView = rootView.findViewById(R.id.searchFlashcard);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when the query is submitted
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search as the query text changes
                performSearch(newText);
                return true;
            }
        });
    }

    // Perform search based on the query
    private void performSearch(String query) {
        // Display a toast message with the search query
        // Tokenize the query string using whitespace as the delimiter
        StringTokenizer tokenizer = new StringTokenizer(query, " ");

        // Hide all book covers and hearts initially
        ivBookCover1.setVisibility(View.GONE);
        // (similarly for ivBookCover2, ivHeart2, ... ivBookCover9, ivHeart9)

        // Process each token from the query
        while (tokenizer.hasMoreTokens()) {
            String keyword = tokenizer.nextToken();

            // (similarly for other book titles)
        }
    }

    private HashMap<String, String> loadBookLinksFromCsv() {
        HashMap<String, String> bookLinks = new HashMap<>();

        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("book_link.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookLinks;
    }


    private void initView(View rootView) {
        // This function initializes the view by setting up image resources for book covers and attaching click listeners to them.
        // It also sets up click listeners for heart icons.

        ivBookCover1 = rootView.findViewById(R.id.iv_book_cover);
        ivBookCover1.setImageResource(R.drawable.first_page);

        ivBookCover2 = rootView.findViewById(R.id.iv_second_image);
        ivBookCover2.setImageResource(R.drawable.souvenirs);

        ivBookCover3 = rootView.findViewById(R.id.iv_third_image);
        ivBookCover3.setImageResource(R.drawable.the_red_and_the_black);

        ivBookCover4 = rootView.findViewById(R.id.iv_forth_image);
        ivBookCover4.setImageResource(R.drawable.jane_eyre);

        ivBookCover5 = rootView.findViewById(R.id.iv_fifth_image);
        ivBookCover5.setImageResource(R.drawable.wuthering);

        ivBookCover6 = rootView.findViewById(R.id.iv_sixth_image);
        ivBookCover6.setImageResource(R.drawable.blank);

        ivBookCover7 = rootView.findViewById(R.id.iv_seventh_image);
        ivBookCover7.setImageResource(R.drawable.blank);

        ivBookCover8 = rootView.findViewById(R.id.iv_eighth_image);
        ivBookCover8.setImageResource(R.drawable.blank);

        ivBookCover9 = rootView.findViewById(R.id.iv_nineth_image);
        ivBookCover9.setImageResource(R.drawable.blank);

        ivBookCover1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookTitle = "book1";
                String bookLink = booklinks.get(bookTitle);
                if (bookLink != null) {
                    openPdfFile(bookLink);
                }
            }
        });


        ivBookCover2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookTitle = "book2";
                String bookLink = booklinks.get(bookTitle);
                if (bookLink != null) {
                    openPdfFile(bookLink);
                }
            }
        });
        ivBookCover3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookTitle = "book3";
                String bookLink = booklinks.get(bookTitle);
                if (bookLink != null) {
                    openPdfFile(bookLink);
                }
            }
        });
        ivBookCover4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookTitle = "book4";
                String bookLink = booklinks.get(bookTitle);
                if (bookLink != null) {
                    openPdfFile(bookLink);
                }
            }
        });
        ivBookCover5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookTitle = "book5";
                String bookLink = booklinks.get(bookTitle);
                if (bookLink != null) {
                    openPdfFile(bookLink);
                }
            }
        });
    }


    private void toggleHeart(ImageView imageView) {
        // This function toggles the heart icon between selected and unselected states.

        boolean isHeartSelected = imageView.getTag() != null && (boolean) imageView.getTag();

        if (isHeartSelected) {
            imageView.setImageResource(R.drawable.blank_heart);
            imageView.setTag(false);
        } else {
            imageView.setImageResource(R.drawable.red_heart);
            imageView.setTag(true);
        }
    }
    private void openPdfFile(String googleDocsUrl) {
        // This function opens a PDF file using the Google Docs viewer by creating an intent with the provided URL.
        // If the activity for viewing the PDF is not found, it displays a toast message.


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(googleDocsUrl));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }


}