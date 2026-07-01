/*
 * Copyright (c) 2016 Ha Duy Trung
 * Copyright (c) 2026 mxmilkiib
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mxmilkiib.materialistic;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.mxmilkiib.materialistic.accounts.UserServices;
import io.github.mxmilkiib.materialistic.accounts.UserServicesClient;
import io.github.mxmilkiib.materialistic.data.AlgoliaClient;
import io.github.mxmilkiib.materialistic.data.AlgoliaPopularClient;
import io.github.mxmilkiib.materialistic.data.FeedbackClient;
import io.github.mxmilkiib.materialistic.data.HackerNewsClient;
import io.github.mxmilkiib.materialistic.data.ItemManager;
import io.github.mxmilkiib.materialistic.data.LocalCache;
import io.github.mxmilkiib.materialistic.data.MaterialisticDatabase;
import io.github.mxmilkiib.materialistic.data.ReadabilityClient;
import io.github.mxmilkiib.materialistic.data.SyncScheduler;
import io.github.mxmilkiib.materialistic.data.UserManager;
import io.github.mxmilkiib.materialistic.data.android.Cache;
import okhttp3.Call;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static io.github.mxmilkiib.materialistic.ActivityModule.ALGOLIA;
import static io.github.mxmilkiib.materialistic.ActivityModule.HN;
import static io.github.mxmilkiib.materialistic.ActivityModule.POPULAR;

@Module(includes = NetworkModule.class)
public class DataModule {
    public static final String MAIN_THREAD = "main";
    public static final String IO_THREAD = "io";

    @Provides @Singleton @Named(HN)
    public ItemManager provideHackerNewsClient(HackerNewsClient client) {
        return client;
    }

    @Provides @Singleton @Named(ALGOLIA)
    public ItemManager provideAlgoliaClient(AlgoliaClient client) {
        return client;
    }

    @Provides @Singleton @Named(POPULAR)
    public ItemManager provideAlgoliaPopularClient(AlgoliaPopularClient client) {
        return client;
    }

    @Provides @Singleton
    public UserManager provideUserManager(HackerNewsClient client) {
        return client;
    }

    @Provides @Singleton
    public FeedbackClient provideFeedbackClient(FeedbackClient.Impl client) {
        return client;
    }

    @Provides @Singleton
    public ReadabilityClient provideReadabilityClient(ReadabilityClient.Impl client) {
        return client;
    }

    @Provides @Singleton
    public UserServices provideUserServices(@Named(NetworkModule.NO_REDIRECT) Call.Factory callFactory,
                                            @Named(IO_THREAD) Scheduler ioScheduler) {
        return new UserServicesClient(callFactory, ioScheduler);
    }

    @Provides @Singleton @Named(IO_THREAD)
    public Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Provides @Singleton @Named(MAIN_THREAD)
    public Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides @Singleton
    public SyncScheduler provideSyncScheduler() {
        return new SyncScheduler();
    }

    @Provides @Singleton
    public LocalCache provideLocalCache(Cache cache) {
        return cache;
    }

    @Provides @Singleton
    public MaterialisticDatabase provideDatabase(Context context) {
        return MaterialisticDatabase.getInstance(context);
    }

    @Provides
    public MaterialisticDatabase.SavedStoriesDao provideSavedStoriesDao(MaterialisticDatabase database) {
        return database.getSavedStoriesDao();
    }

    @Provides
    public MaterialisticDatabase.ReadStoriesDao provideReadStoriesDao(MaterialisticDatabase database) {
        return database.getReadStoriesDao();
    }

    @Provides
    public MaterialisticDatabase.ReadableDao provideReadableDao(MaterialisticDatabase database) {
        return database.getReadableDao();
    }

    @Provides
    public SupportSQLiteOpenHelper provideOpenHelper(MaterialisticDatabase database) {
        return database.getOpenHelper();
    }
}
