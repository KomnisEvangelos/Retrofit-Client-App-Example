package com.example.mybooks_client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDescription, editTextPublicationDate;
    private Spinner spinnerAuthor;
    private Button buttonAddBook;
    private Uri imageUri;

    private static final int REQUEST_IMAGE_FROM_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextPublicationDate = findViewById(R.id.editTextPublicationDate);
        spinnerAuthor = findViewById(R.id.spinnerAuthor);
        buttonAddBook = findViewById(R.id.buttonAddBook);

        fetchAuthors();


        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });
    }

    private void fetchAuthors() {
        Retrofit retrofit = RetrofitClient.getClient("http://192.168.1.2:8000/");
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getAuthors().enqueue(new Callback<List<Author>>() {
            @Override
            public void onResponse(Call<List<Author>> call, Response<List<Author>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Author> authors = response.body();
                    ArrayAdapter<Author> adapter = new ArrayAdapter<>(AddBookActivity.this, android.R.layout.simple_spinner_item, authors);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerAuthor.setAdapter(adapter);
                } else {
                    Toast.makeText(AddBookActivity.this, "Προέκυψε κάποιο σφάλμα", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Author>> call, Throwable t) {
                Toast.makeText(AddBookActivity.this, "Προέκυψε κάποιο σφάλμα", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addBook() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String publicationDate = editTextPublicationDate.getText().toString().trim();
        Author selectedAuthor = (Author) spinnerAuthor.getSelectedItem();

        if (title.isEmpty() || description.isEmpty() || publicationDate.isEmpty() || selectedAuthor == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setPublicationDate(Integer.parseInt(publicationDate));
        book.setAuthor(selectedAuthor);

        Retrofit retrofit = RetrofitClient.getClient("http://192.168.1.2:8000/");
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.addBook(book).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddBookActivity.this, "Το βιβλίο προστέθηκε με επιτυχία", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddBookActivity.this, "Προέκυψε κάποιο σφάλμα", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(AddBookActivity.this, "Προέκυψε κάποιο σφάλμα", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
