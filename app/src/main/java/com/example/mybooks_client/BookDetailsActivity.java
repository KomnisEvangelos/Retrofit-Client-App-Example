package com.example.mybooks_client;

import static com.example.mybooks_client.BookAdapter.context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            String author = extras.getString("author");
            String publicationDate = extras.getString("publicationDate");
            String description = extras.getString("description");
            String authorsCountry = extras.getString("authorscountry");

            TextView titleTextView = findViewById(R.id.titleTextView);
            TextView authorTextView = findViewById(R.id.authorTextView);
            TextView publicationDateTextView = findViewById(R.id.publicationDateTextView);
            TextView descrptionTextView = findViewById(R.id.descrptionTextView);
            TextView openGoogleMapsBtn = findViewById(R.id.openGoogleMaps);

            openGoogleMapsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String location = "http://maps.google.co.in/maps?q=" + authorsCountry;
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(location));
                    startActivity(i);
                }
            });


            titleTextView.setText(title);
            authorTextView.setText(author);
            publicationDateTextView.setText(publicationDate);
            descrptionTextView.setText(description);
        }
    }
}
