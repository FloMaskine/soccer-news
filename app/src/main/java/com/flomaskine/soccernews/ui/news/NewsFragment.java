package com.flomaskine.soccernews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.flomaskine.soccernews.data.local.AppDatabase;
import com.flomaskine.soccernews.databinding.FragmentNewsBinding;
import com.flomaskine.soccernews.ui.NewsAdapter;


public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;

    public AppDatabase db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NewsViewModel newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);


        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Room.databaseBuilder(
                root.getContext(),
                AppDatabase.class,
                "soccer-news"
        ).allowMainThreadQueries()
                .build();

        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news ->
                binding.rvNews.setAdapter(new NewsAdapter(news, favoriteNews ->
                        db.newsDao().insert(favoriteNews)
                )));


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;

    }
}