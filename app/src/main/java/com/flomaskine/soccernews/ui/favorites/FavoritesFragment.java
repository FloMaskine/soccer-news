package com.flomaskine.soccernews.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flomaskine.soccernews.databinding.FragmentFavoritesBinding;
import com.flomaskine.soccernews.ui.NewsAdapter;


public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private FavoritesViewModel favoritesViewModel;


    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadFavoriteNews();


        return root;
    }

    private void loadFavoriteNews() {
        favoritesViewModel.loadFavoriteNews().observe(getViewLifecycleOwner(), localNews -> {
            binding.rvFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvFavorite.setAdapter(new NewsAdapter(localNews, savedNews -> {
                favoritesViewModel.saveNews(savedNews);
                loadFavoriteNews();
                Log.e("FavoritesFragment", "Salvou demais");
            }));
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}