package com.example.quizzen.navigation;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quizzen.R;


import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class BookFragment extends Fragment {
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
    private ImageView ivHeart1;
    private ImageView ivHeart2;
    private ImageView ivHeart3;
    private ImageView ivHeart4;
    private ImageView ivHeart5;

    private LinearLayout coverContainer;

    private HashMap<String, String> booklinks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);

        // Set up the search view
        setupSearchView(rootView);

        // Initialize the view components
        initView(rootView);

        // Load book links from a CSV file
        booklinks = loadBookLinksFromCsv();

        return rootView;
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
        Toast.makeText(getContext(), "You want to search: " + query, Toast.LENGTH_SHORT).show();

        // Tokenize the query string using whitespace as the delimiter
        StringTokenizer tokenizer = new StringTokenizer(query, " ");

        // Hide all book covers and hearts initially
        ivBookCover1.setVisibility(View.GONE);
        ivHeart1.setVisibility(View.GONE);
        // (similarly for ivBookCover2, ivHeart2, ... ivBookCover9, ivHeart9)

        // Process each token from the query
        while (tokenizer.hasMoreTokens()) {
            String keyword = tokenizer.nextToken();

            // Check if the keyword matches any book titles and show the corresponding book cover and heart
            if (matchBookTitle(keyword, "the little prince")) {
                ivBookCover1.setVisibility(View.VISIBLE);
                ivHeart1.setVisibility(View.VISIBLE);
            }
            // (similarly for other book titles)
        }
    }

    // Check if a query matches a book title
    private boolean matchBookTitle(String query, String bookTitle) {
        // Get the lengths of the query and book title
        int queryLength = query.length();
        int titleLength = bookTitle.length();

        // Compute the Longest Proper Prefix which is also a Suffix (LPS) array for the query
        int[] lps = computeLPSArray(query);
        int i = 0;
        int j = 0;

        // Compare characters of the query and book title until a match is found or the end of the title is reached
        while (i < titleLength) {
            if (Character.toLowerCase(query.charAt(j)) == Character.toLowerCase(bookTitle.charAt(i))) {
                i++;
                j++;
            }

            if (j == queryLength) {
                // The query matches the book title
                return true;
            } else if (i < titleLength && Character.toLowerCase(query.charAt(j)) != Character.toLowerCase(bookTitle.charAt(i))) {
                if (j != 0) {
                    // Move j to the previous matching position based on the LPS array
                    j = lps[j - 1];
                } else {
                    // Move to the next character in the title
                    i++;
                }
            }
        }

        // The query does not match the book title
        return false;
    }

    // Compute the Longest Proper Prefix which is also a Suffix (LPS) array for a query
    private int[] computeLPSArray(String query) {
        int length = query.length();
        int len = 0;
        int i = 1;
        int[] lps = new int[length];

        while (i < length) {
            if (Character.toLowerCase(query.charAt(i)) == Character.toLowerCase(query.charAt(len))) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    private HashMap<String, String> loadBookLinksFromCsv() {
        // This function loads book links from a CSV file and returns a HashMap containing book titles as keys and corresponding links as values.

        HashMap<String, String> bookLinks = new HashMap<>();

        try {
            InputStream inputStream = requireContext().getAssets().open("book_link.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String bookTitle = parts[0].trim();
                    String bookLink = parts[1].trim();
                    bookLinks.put(bookTitle, bookLink);
                }
            }

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


        ivHeart1 = rootView.findViewById(R.id.iv_heart_1);
        ivHeart2 = rootView.findViewById(R.id.iv_heart_2);
        ivHeart3 = rootView.findViewById(R.id.iv_heart_3);
        ivHeart4 = rootView.findViewById(R.id.iv_heart_4);
        ivHeart5 = rootView.findViewById(R.id.iv_heart_5);
        // "View Text Book" button
        Button btn_viewTextBook = rootView.findViewById(R.id.btn_text_book);
        ivHeart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeart(ivHeart1);
            }
        });
        ivHeart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeart(ivHeart2);
            }
        });

        ivHeart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeart(ivHeart3);
            }
        });
        ivHeart4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeart(ivHeart4);
            }
        });

        ivHeart5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleHeart(ivHeart5);
            }
        });

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

        // listener for "view text book" button, which jumps the fragment to BookTextFragment
        // Judy Xie
        btn_viewTextBook.setOnClickListener(view -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            Fragment booktext = new BookTextFragment();
            fm.beginTransaction().add(R.id.fragment_book, booktext)
                    .addToBackStack("BookFragment").commit();
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
            Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
        }
    }



}