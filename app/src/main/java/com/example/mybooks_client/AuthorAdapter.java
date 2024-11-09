package com.example.mybooks_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AuthorAdapter extends ArrayAdapter<Author> {

    private Context mContext;
    private int mResource;
    private List<Author> mAuthors;

    public AuthorAdapter(Context context, int resource, List<Author> authors) {
        super(context, resource, authors);
        this.mContext = context;
        this.mResource = resource;
        this.mAuthors = authors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, parent, false);
        }

        Author author = mAuthors.get(position);

        TextView textViewAuthorName = view.findViewById(R.id.textViewAuthorName);
        textViewAuthorName.setText(author.getName());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
