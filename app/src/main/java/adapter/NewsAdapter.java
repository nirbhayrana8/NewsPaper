package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import model.NewsModel;

public class NewsAdapter extends RecyclerView.Adapter <NewsAdapter.NewsViewHolder> {

    private OnNewsListener onNewsListener;
    private ArrayList<NewsModel> data;


    public NewsAdapter(ArrayList<NewsModel> data, OnNewsListener onNewsListener) {
        this.data = data;
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

        holder.textView.setText(data.get(position).getTitles());
        Picasso.get().load(data.get(position).getImageUrls()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
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
