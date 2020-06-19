package com.example.newspaper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.room.NewsTable;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter <NewsAdapter.NewsViewHolder> {

    private OnNewsListener onNewsListener;
    private List<NewsTable> data;

    public NewsAdapter(OnNewsListener onNewsListener) {
        this.onNewsListener = onNewsListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.app_layout, parent, false);

        return new NewsViewHolder(view, onNewsListener);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        holder.textView.setText(data.get(position).getTitle());
        Picasso.get().load(data.get(position).getImageUrl()).into(holder.imageView);
    }

    public void setData(List<NewsTable> list) {
        data = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }else{
            return  0;
        }
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageView imageView;

        OnNewsListener onNewsListener;

        public NewsViewHolder(@NonNull View itemView, OnNewsListener onNewsListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            this.onNewsListener = onNewsListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNewsListener.OnNewsClick(getAdapterPosition());
        }
    }

    public interface OnNewsListener {
        void OnNewsClick(int position);
    }
}
