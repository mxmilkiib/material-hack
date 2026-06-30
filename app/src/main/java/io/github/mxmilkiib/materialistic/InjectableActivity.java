/*
 * Copyright (c) 2015 Ha Duy Trung
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

import android.os.Bundle;

public abstract class InjectableActivity extends ThemedActivity implements Injectable {
    private ActivityComponent mActivityComponent;
    private boolean mDestroyed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectSelf(getActivityComponent());
    }

    protected void injectSelf(ActivityComponent component) {
        // override to inject dependencies
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDestroyed = true;
        mActivityComponent = null;
    }

    @Override
    public void onBackPressed() {
        // TODO http://b.android.com/176265
        try {
            super.onBackPressed();
        } catch (IllegalStateException e) {
            supportFinishAfterTransition();
        }
    }

    @Override
    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = ((Injectable) getApplication()).getActivityComponent();
        }
        return mActivityComponent;
    }

    public boolean isActivityDestroyed() {
        return mDestroyed;
    }
}
