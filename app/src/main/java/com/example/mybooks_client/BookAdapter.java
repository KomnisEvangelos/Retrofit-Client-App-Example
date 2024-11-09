package com.example.mybooks_client;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books = new ArrayList<>();
    public static Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView authorTextView;
        private TextView publicationDateTextView;
        private TextView showMoreBtn;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            publicationDateTextView = itemView.findViewById(R.id.publicationDateTextView);
            showMoreBtn = itemView.findViewById(R.id.showMoreBtn);

        }

        public void bind(Book book) {
            titleTextView.setText(book.getTitle());
            authorTextView.setText(book.getAuthor().getName());
            publicationDateTextView.setText(String.valueOf(book.getPublicationDate()));

            showMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMoreBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, BookDetailsActivity.class);

                            intent.putExtra("title", book.getTitle());
                            intent.putExtra("author", book.getAuthor().getName());
                            intent.putExtra("authorscountry", book.getAuthor().getCountry());
                            intent.putExtra("publicationDate", String.valueOf(book.getPublicationDate()));
                            intent.putExtra("description", book.getDescription());

                            context.startActivity(intent);
                        }
                    });
                }
            });
        }
    }
}
