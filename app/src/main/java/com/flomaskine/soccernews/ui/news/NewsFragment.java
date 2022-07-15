package com.flomaskine.soccernews.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flomaskine.soccernews.R;
import com.flomaskine.soccernews.databinding.FragmentNewsBinding;
import com.flomaskine.soccernews.ui.NewsAdapter;


public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private NewsViewModel newsViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        observeNews();
        observeStatus();
        binding.srlNews.setOnRefreshListener(newsViewModel::loadNewsFromGitHub);


        return root;
    }

    private void observeStatus() {
        newsViewModel.getState().observe(getViewLifecycleOwner(), state -> {
            binding.srlNews.setColorSchemeColors(getResources().getColor(R.color.purple_500));
            switch (state) {
                case DOING:

                    binding.srlNews.setRefreshing(true);

                    break;
                case DONE:

                    binding.srlNews.setRefreshing(false);

                    break;
                case ERROR:

                    binding.srlNews.setRefreshing(true);
                    Toast.makeText(getContext(), R.string.error_api, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void observeNews() {
        newsViewModel.getNews().observe(getViewLifecycleOwner(), news ->
                binding.rvNews.setAdapter(new NewsAdapter(news, newsViewModel::saveNews)));
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