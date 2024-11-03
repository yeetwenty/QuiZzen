package com.example.quizzen.book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizzen.R;

import org.json.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This class is used to read the book
 * It will display the title, author and content of the book
 * @author Judy Xie u7397058
 */
public class BookRead extends AppCompatActivity {
    boolean fav = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);

        // get file name from intent
        String file = getIntent().getStringExtra("BookTitle");

        TextView tvTitle = findViewById(R.id.book_title);
        TextView author = findViewById(R.id.author_name);
        TextView content = findViewById(R.id.book_content);
        ImageView isFav = findViewById(R.id.img_fav);

        content.setMovementMethod(new ScrollingMovementMethod());

        try {
            // read json file
            InputStream inputStream = getAssets().open(file);
            List<Book> parse = Parser.readJsonStream(inputStream);
            Book no1 = parse.get(0);

            // set favorite icon
            fav = no1.isFavorite();
            if (fav) {
                isFav.setImageResource(R.drawable.red_heart);
            } else {
                isFav.setImageResource(R.drawable.blank_heart);
            }
            // set book contents
            tvTitle.setText(no1.getTitle());
            author.setText(no1.getAuthor());
            content.setText(no1.getText());

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}