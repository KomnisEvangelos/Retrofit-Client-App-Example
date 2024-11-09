package com.example.mybooks_client;

import com.example.mybooks_client.Author;
import com.example.mybooks_client.Book;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {
    @GET("getAuthors/")
    Call<List<Author>> getAuthors();

    @POST("createAuthor/")
    Call<Author> addAuthor(@Body Author author);

    @GET("getBooks/")
    Call<List<Book>> getBooks(
            @Query("authorName") String authorName,
            @Query("titleWords") String titleWords,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );
    @POST("createBook/")
    Call<Book> addBook(@Body Book book);
}
