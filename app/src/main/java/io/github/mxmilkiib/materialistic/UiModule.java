/*
 * Copyright (c) 2016 Ha Duy Trung
 * Copyright (c) 2024-2026 mxmilkiib
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

import android.annotation.SuppressLint;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.mxmilkiib.materialistic.widget.PopupMenu;

@Module
class UiModule {
    @Provides
    public PopupMenu providePopupMenu() {
        return new PopupMenu.Impl();
    }

    @Provides @Singleton
    public CustomTabsDelegate provideCustomTabsDelegate() {
        return new CustomTabsDelegate();
    }

    @Provides @Singleton
    public KeyDelegate provideKeyDelegate() {
        return new KeyDelegate();
    }

    @Provides @Singleton
    public ActionViewResolver provideActionViewResolver() {
        return new ActionViewResolver();
    }

    @Provides
    public AlertDialogBuilder provideAlertDialogBuilder() {
        return new AlertDialogBuilder.Impl();
    }

    @SuppressLint("Recycle")
    @Provides @Singleton
    public ResourcesProvider provideResourcesProvider(Context context) {
        return resId -> context.getResources().obtainTypedArray(resId);
    }
}
