/*
 * Copyright (c) 2015 Ha Duy Trung
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

package io.github.mxmilkiib.materialistic.data;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import javax.inject.Inject;
import javax.inject.Named;

import io.github.mxmilkiib.materialistic.DataModule;
import net.dankito.readability4j.Article;
import net.dankito.readability4j.Readability4J;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public interface ReadabilityClient {

    interface Callback {
        void onResponse(String content);
    }

    void parse(String itemId, String url, Callback callback);

    @WorkerThread
    void parse(String itemId, String url);

    class Impl implements ReadabilityClient {
        private final HtmlService mHtmlService;
        private final LocalCache mCache;
        @Inject @Named(DataModule.IO_THREAD) Scheduler mIoScheduler;
        @Inject @Named(DataModule.MAIN_THREAD) Scheduler mMainThreadScheduler;

        interface HtmlService {
            // base URL is a placeholder; @Url overrides it entirely per request
            @GET
            Observable<ResponseBody> fetch(@Url String url);
        }

        @Inject
        public Impl(LocalCache cache, RestServiceFactory factory) {
            mHtmlService = factory.rxEnabled(true)
                    .create("https://unused.invalid/", HtmlService.class);
            mCache = cache;
        }

        @Override
        public void parse(String itemId, String url, Callback callback) {
            Observable.defer(() -> fromCache(itemId))
                    .subscribeOn(mIoScheduler)
                    .switchIfEmpty(fromNetwork(itemId, url))
                    .observeOn(mMainThreadScheduler)
                    .subscribe(callback::onResponse);
        }

        @WorkerThread
        @Override
        public void parse(String itemId, String url) {
            Observable.defer(() -> fromCache(itemId))
                    .subscribeOn(Schedulers.trampoline())
                    .switchIfEmpty(fromNetwork(itemId, url))
                    .observeOn(Schedulers.trampoline())
                    .subscribe();
        }

        @NonNull
        private Observable<String> fromNetwork(String itemId, String url) {
            return mHtmlService.fetch(url)
                    .map(body -> {
                        Article article = new Readability4J(url, body.string()).parse();
                        return article != null && article.getContent() != null ?
                                article.getContent() : "";
                    })
                    .onErrorResumeNext(throwable -> Observable.just(""))
                    .doOnNext(content -> {
                        if (!TextUtils.isEmpty(content)) {
                            mCache.putReadability(itemId, content);
                        }
                    });
        }

        private Observable<String> fromCache(String itemId) {
            String cached = mCache.getReadability(itemId);
            return cached != null ? Observable.just(cached) : Observable.empty();
        }
    }
}
