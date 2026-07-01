/*
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

package io.github.mxmilkiib.materialistic.preference;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import io.github.mxmilkiib.materialistic.R;
import io.github.mxmilkiib.materialistic.widget.preference.IconView;

public class AppIconPreference extends Preference {

    private static final String DEFAULT = "default";
    private static final String PURPLE = "purple";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";
    private static final String RED = "red";
    private static final String TEAL = "teal";
    private static final String PINK = "pink";
    private static final String INDIGO = "indigo";

    private static final ArrayMap<Integer, IconSpec> BUTTONS = new ArrayMap<>();
    static {
        BUTTONS.put(R.id.app_icon_default, new IconSpec(DEFAULT, R.string.app_icon_default, R.color.ic_launcher_background));
        BUTTONS.put(R.id.app_icon_purple, new IconSpec(PURPLE, R.string.app_icon_purple, R.color.ic_launcher_background_purple));
        BUTTONS.put(R.id.app_icon_green, new IconSpec(GREEN, R.string.app_icon_green, R.color.ic_launcher_background_green));
        BUTTONS.put(R.id.app_icon_blue, new IconSpec(BLUE, R.string.app_icon_blue, R.color.ic_launcher_background_blue));
        BUTTONS.put(R.id.app_icon_red, new IconSpec(RED, R.string.app_icon_red, R.color.ic_launcher_background_red));
        BUTTONS.put(R.id.app_icon_teal, new IconSpec(TEAL, R.string.app_icon_teal, R.color.ic_launcher_background_teal));
        BUTTONS.put(R.id.app_icon_pink, new IconSpec(PINK, R.string.app_icon_pink, R.color.ic_launcher_background_pink));
        BUTTONS.put(R.id.app_icon_indigo, new IconSpec(INDIGO, R.string.app_icon_indigo, R.color.ic_launcher_background_indigo));
    }

    private String mSelectedIcon;

    @SuppressWarnings("unused")
    public AppIconPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppIconPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.preference_app_icon);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return DEFAULT;
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        super.onSetInitialValue(restorePersistedValue, defaultValue);
        mSelectedIcon = restorePersistedValue ? getPersistedString(null) : (String) defaultValue;
        if (TextUtils.isEmpty(mSelectedIcon)) {
            mSelectedIcon = DEFAULT;
        }
        updateSummary();
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.itemView.setClickable(false);
        for (int i = 0; i < BUTTONS.size(); i++) {
            final int buttonId = BUTTONS.keyAt(i);
            final IconSpec spec = BUTTONS.valueAt(i);
            IconView iconView = (IconView) holder.findViewById(buttonId);
            iconView.setClickable(true);
            iconView.setForegroundResource(R.mipmap.ic_launcher_foreground);
            iconView.setBackgroundColor(ContextCompat.getColor(getContext(), spec.colorRes));
            iconView.setSelected(spec.value.equals(mSelectedIcon));
            iconView.setOnClickListener(v -> {
                mSelectedIcon = spec.value;
                updateSummary();
                persistString(spec.value);
                notifyChanged();
            });
        }
    }

    private void updateSummary() {
        for (int i = 0; i < BUTTONS.size(); i++) {
            IconSpec spec = BUTTONS.valueAt(i);
            if (spec.value.equals(mSelectedIcon)) {
                setSummary(spec.summary);
                return;
            }
        }
        setSummary(R.string.app_icon_default);
    }

    private static class IconSpec {
        final String value;
        final @StringRes int summary;
        final @ColorRes int colorRes;

        IconSpec(String value, @StringRes int summary, @ColorRes int colorRes) {
            this.value = value;
            this.summary = summary;
            this.colorRes = colorRes;
        }
    }
}
