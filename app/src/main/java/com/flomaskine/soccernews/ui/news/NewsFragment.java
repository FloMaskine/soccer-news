package com.flomaskine.soccernews.ui.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flomaskine.soccernews.MainActivity;
import com.flomaskine.soccernews.R;
import com.flomaskine.soccernews.databinding.FragmentNewsBinding;
import com.flomaskine.soccernews.ui.NewsAdapter;


public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NewsViewModel newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);


        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news ->
                binding.rvNews.setAdapter(new NewsAdapter(news, favoriteNews ->
                        MainActivity.db.newsDao().save(favoriteNews)
                )));

        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            switch (state) {
                case DOING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    //TODO Incluir swipe refresh
                    break;
                case DONE:
                    binding.progressBar.setVisibility(View.GONE);
                    //TODO Incluir swipe refresh
                    Toast.makeText(getContext(), R.string.data_loaded_sucessfully, Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    Log.e("NewsViewModel", "Error getting data onResponse");
                    Toast.makeText(getContext(), R.string.error_api, Toast.LENGTH_SHORT).show();
                    break;
            }
        });


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