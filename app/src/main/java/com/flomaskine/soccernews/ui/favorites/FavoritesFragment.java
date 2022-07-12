package com.flomaskine.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flomaskine.soccernews.MainActivity;
import com.flomaskine.soccernews.databinding.FragmentFavoritesBinding;
import com.flomaskine.soccernews.domain.News;
import com.flomaskine.soccernews.ui.NewsAdapter;

import java.util.List;


public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;



    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        FavoritesViewModel favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        loadFavoriteNews();
        return root;
    }

    private void loadFavoriteNews() {
        binding.rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        List<News> favoriteNews = MainActivity.db.newsDao().getFavoriteNews();
        binding.rvFavorite.setAdapter(new NewsAdapter(favoriteNews, updatedNews -> {
            MainActivity.db.newsDao().save(updatedNews);
            loadFavoriteNews();
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}