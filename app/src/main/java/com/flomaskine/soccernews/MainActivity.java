package com.flomaskine.soccernews;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.flomaskine.soccernews.data.local.AppDatabase;
import com.flomaskine.soccernews.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
        setupLocalDatabase();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(R.id.navigation_news, R.id.navigation_favorites).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    private void setupLocalDatabase() {
        db = Room.databaseBuilder(
                        this,
                        AppDatabase.class,
                        "soccer-news"
                ).allowMainThreadQueries()
                .build();
    }

}