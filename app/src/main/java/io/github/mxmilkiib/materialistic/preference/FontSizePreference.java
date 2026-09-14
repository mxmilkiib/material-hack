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

package io.github.mxmilkiib.materialistic.preference;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import io.github.mxmilkiib.materialistic.AppUtils;
import io.github.mxmilkiib.materialistic.Preferences;
import io.github.mxmilkiib.materialistic.R;

public class FontSizePreference extends SpinnerPreference {
    private final LayoutInflater mLayoutInflater;
    private TextView mPreviewView;

    @SuppressWarnings("unused")
    public FontSizePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontSizePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutInflater = LayoutInflater.from(getContext());
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        mPreviewView = (TextView) holder.findViewById(android.R.id.summary);
        if (mPreviewView != null) {
            mPreviewView.setVisibility(View.VISIBLE);
            updatePreview();
        }
    }

    @Override
    protected void onSelectionChanged(int position) {
        updatePreview();
    }

    private void updatePreview() {
        if (mPreviewView == null) {
            return;
        }
        String entryValue = mSelection < mEntryValues.length ? mEntryValues[mSelection] : "";
        int styleResId;
        if (TextUtils.isEmpty(entryValue)) {
            styleResId = Preferences.Theme.resolvePreferredTextSize(getContext());
        } else {
            styleResId = Preferences.Theme.resolveTextSize(entryValue);
        }
        float titleSize = AppUtils.getDimension(getContext(), styleResId, R.attr.titleTextSize);
        mPreviewView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        mPreviewView.setText(R.string.font_size_preview);
    }

    @Override
    protected View createDropDownView(int position, ViewGroup parent) {
        return mLayoutInflater.inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
    }

    @Override
    protected void bindDropDownView(int position, View view) {
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        String entryValue = mEntryValues[position];
        int styleResId;
        if (TextUtils.isEmpty(entryValue)) {
            // "Auto" option: resolve using the global text size preference
            styleResId = Preferences.Theme.resolvePreferredTextSize(getContext());
        } else {
            styleResId = Preferences.Theme.resolveTextSize(entryValue);
        }
        float textSize = AppUtils.getDimension(getContext(), styleResId, R.attr.contentTextSize);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setText(mEntries[position]);
    }
}
