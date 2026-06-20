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

package io.github.mxmilkiib.materialistic.widget.preference;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import io.github.mxmilkiib.materialistic.AppUtils;
import io.github.mxmilkiib.materialistic.R;

public class ThemeView extends CardView {

    public ThemeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.theme_view, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.theme});
        ContextThemeWrapper wrapper = new ContextThemeWrapper(context, ta.getResourceId(0, R.style.AppTheme));
        ta.recycle();
        int cardBackgroundColor = AppUtils.getThemedResId(wrapper, R.attr.colorCardBackground);
        int textColor = AppUtils.getThemedResId(wrapper, android.R.attr.textColorTertiary);
        int primaryColor = AppUtils.getThemedResId(wrapper, R.attr.colorPrimary);
        int accentColor = AppUtils.getThemedResId(wrapper, R.attr.colorAccent);
        setCardBackgroundColor(ContextCompat.getColor(wrapper, cardBackgroundColor));
        ((TextView) findViewById(R.id.content)).setTextColor(ContextCompat.getColor(wrapper, textColor));
        findViewById(R.id.primary_bar).setBackgroundColor(ContextCompat.getColor(wrapper, primaryColor));
        View accentDot = findViewById(R.id.accent_dot);
        accentDot.setBackgroundTintList(
                ColorStateList.valueOf(ContextCompat.getColor(wrapper, accentColor)));
    }

}
