package com.example.mybooks_client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddAuthorActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextCountry;
    private Button buttonAddBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);

        editTextName = findViewById(R.id.editTextName);
        editTextCountry = findViewById(R.id.editTextCountry);
        buttonAddBook = findViewById(R.id.buttonAddBook);

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAuthor();
            }
        });
    }

    private void addAuthor() {
        String name = editTextName.getText().toString().trim();
        String country = editTextCountry.getText().toString().trim();

        if (name.isEmpty() || country.isEmpty()) {
            Toast.makeText(this, "Παρακαλώ συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
            return;
        }

        Author author = new Author();
        author.setName(name);
        author.setCountry(country);

        Retrofit retrofit = RetrofitClient.getClient("http://192.168.1.2:8000/");
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.addAuthor(author).enqueue(new Callback<Author>() {
            @Override
            public void onResponse(Call<Author> call, Response<Author> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddAuthorActivity.this, "Ο συγγράφεας προστέθηκε επιτυχώς", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddAuthorActivity.this, "Προέκυψε κάποιο σφάλμα", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Author> call, Throwable t) {
                Toast.makeText(AddAuthorActivity.this, "Προέκυψε κάποιο σφάλμα", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
