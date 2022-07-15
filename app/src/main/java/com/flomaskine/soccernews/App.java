package com.flomaskine.soccernews;

import android.app.Application;

/**
 *FIXME:
 * Criada como uma gambiarra para fazer ingeção de dependências.
 * Vou aprender mais sobre isso e utilizar o Dagger ou Hilt para fazer isso.
 */

public class App  extends Application {

    public static App getInstance() {
        return instance;
    }

    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}

