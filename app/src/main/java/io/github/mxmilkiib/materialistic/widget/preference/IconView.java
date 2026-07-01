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

package io.github.mxmilkiib.materialistic.widget.preference;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import io.github.mxmilkiib.materialistic.R;

public class IconView extends CardView {

    private final ImageView mBackgroundView;
    private final ImageView mForegroundView;
    private final View mSelectedDot;

    public IconView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.icon_view, this, true);
        mBackgroundView = findViewById(R.id.icon_background);
        mForegroundView = findViewById(R.id.icon_foreground);
        mSelectedDot = findViewById(R.id.icon_selected_dot);
    }

    public void setBackgroundColor(int color) {
        mBackgroundView.setBackgroundColor(color);
    }

    public void setForegroundResource(int resId) {
        mForegroundView.setImageResource(resId);
    }

    public void setSelected(boolean selected) {
        mSelectedDot.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
    }
}
