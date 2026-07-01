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

package io.github.mxmilkiib.materialistic;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatDelegate;

import io.github.mxmilkiib.materialistic.data.AlgoliaClient;
import io.github.mxmilkiib.materialistic.data.SyncDelegate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Application extends android.app.Application implements Injectable {

    public static Typeface TYPE_FACE = null;
    private AppComponent mAppComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mAppComponent = DaggerAppComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(Preferences.Theme.getAutoDayNightMode(this));
        AlgoliaClient.sSortByTime = Preferences.isSortByRecent(this);
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
        Preferences.migrate(this);
        TYPE_FACE = FontCache.getInstance().get(this, Preferences.Theme.getTypeface(this));
        AppUtils.registerAccountsUpdatedListener(this);
        AdBlocker.init(this, Schedulers.io());
        registerWifiSyncCallback();
    }

    private void registerWifiSyncCallback() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest request = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
        cm.registerNetworkCallback(request, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onCapabilitiesChanged(Network network, NetworkCapabilities capabilities) {
                if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    SyncDelegate.scheduleSync(Application.this,
                            new SyncDelegate.JobBuilder(Application.this, null).build());
                }
            }
        });
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return mAppComponent.activityComponent();
    }
}
