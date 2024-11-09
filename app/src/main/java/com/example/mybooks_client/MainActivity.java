package com.example.mybooks_client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ImageButton filterButton;
    private ImageView addButton, addAuthorButton;
    private TextView showMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        filterButton = findViewById(R.id.filterButton);
        addButton = findViewById(R.id.addButton);
        addAuthorButton = findViewById(R.id.addAuthorButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setContext(this);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivity(intent);
        });
        addAuthorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddAuthorActivity.class);
            startActivity(intent);
        });

        filterButton.setOnClickListener(v -> {
            View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);

            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
            bottomSheetDialog.setContentView(bottomSheetView);

            EditText authorNameEditText = bottomSheetView.findViewById(R.id.authorNameEditText);
            EditText bookTitleEditText = bottomSheetView.findViewById(R.id.bookTitleEditText);
            EditText fromDateEditText = bottomSheetView.findViewById(R.id.FromDateEditText);
            EditText toDateEditText = bottomSheetView.findViewById(R.id.ToDateEditText);
            Button applyButton = bottomSheetView.findViewById(R.id.applyButton);
            applyButton.setOnClickListener(v1 -> {
                String authorName = authorNameEditText.getText().toString().trim();
                String titleWords = bookTitleEditText.getText().toString().trim();
                String fromDate = fromDateEditText.getText().toString().trim();
                String toDate = toDateEditText.getText().toString().trim();
                fetchBooks(authorName, titleWords, fromDate, toDate);
                bottomSheetDialog.dismiss();
            });

            bottomSheetDialog.show();
        });

        fetchBooks(null, null, null, null);
    }

    private void fetchBooks(String authorName, String titleWords, String startDate, String endDate) {
        Retrofit retrofit = RetrofitClient.getClient("http://192.168.1.2:8000/");
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Book>> call = apiService.getBooks(authorName, titleWords, startDate, endDate);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setBooks(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Πρόβλημα κατά την λήψη των βιβλίων", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Προέκυψε κάποιο σφάλμα", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
