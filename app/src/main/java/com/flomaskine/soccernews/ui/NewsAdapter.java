package com.flomaskine.soccernews.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flomaskine.soccernews.MainActivity;
import com.flomaskine.soccernews.R;
import com.flomaskine.soccernews.databinding.NewsItemBinding;
import com.flomaskine.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<News> newsList;
    private final FavoriteListener favoriteListener;

    public NewsAdapter(List<News> newsList, FavoriteListener favoriteListener) {
        this.newsList = newsList;
        this.favoriteListener = favoriteListener;
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
        //Open the link in browser when user clicks on the button
        holder.binding.openNews.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(news.link));
            holder.binding.getRoot().getContext().startActivity(intent);
        });
        //Share the link when user clicks on the button
        holder.binding.ivShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, news.link);
            intent.putExtra(Intent.EXTRA_TEXT, news.link);
            holder.binding.getRoot().getContext().startActivity(
                    Intent.createChooser(
                            intent,
                            "Share link using"
                    ));
        });
        holder.binding.ivFavoriteIcon.setOnClickListener(v -> {
            news.favorite = !news.favorite;
            favoriteListener.onFavoriteClicked(news);
            notifyItemChanged(position);


        });
        //change the icon of the favorite button depending on the favorite status
        if (news.favorite) {
            holder.binding.ivFavoriteIcon.setImageResource(R.drawable.ic_favorite_icon);
        } else {
            holder.binding.ivFavoriteIcon.setImageResource(R.drawable.ic_favorite_off_icon);
            MainActivity.db.newsDao().delete(news);
        }

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
            binding.newsTitle.setText(news.title);
            binding.newsDescription.setText(news.description);
            Picasso.get().load(news.image)
                    .fit()
                    .into(binding.newsImage);

        }


    }

    public interface FavoriteListener {
        void onFavoriteClicked(News news);

    }
}

