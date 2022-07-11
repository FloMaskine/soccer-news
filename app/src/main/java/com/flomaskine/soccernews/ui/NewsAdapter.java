package com.flomaskine.soccernews.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flomaskine.soccernews.databinding.NewsItemBinding;
import com.flomaskine.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<News> newsList;

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(inflater, parent, false);
        return new NewsViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = this.newsList.get(position);
        holder.bind(news);
        holder.binding.openNews.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(news.getLink()));
            holder.binding.getRoot().getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public NewsViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(News news) {
            binding.newsTitle.setText(news.getTitle());
            binding.newsDescription.setText(news.getDescription());
            Picasso.get().load(news.getImage())
                    .fit()
                    .into(binding.newsImage);

        }


    }
}

