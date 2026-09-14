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

package io.github.mxmilkiib.materialistic.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ContextThemeWrapper;

import io.github.mxmilkiib.materialistic.Application;

/**
 * ContextThemeWrapper that carries a per-context typeface override.
 * Used by createCommentLayoutInflater to apply a comment-specific font
 * to widget.TextView instances inflated from that context.
 */
public class TypefaceContextWrapper extends ContextThemeWrapper {

    private final Typeface mTypeface;

    public TypefaceContextWrapper(Context base, int themeResId, Typeface typeface) {
        super(base, themeResId);
        mTypeface = typeface;
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    /**
     * Walk the context chain looking for a TypefaceContextWrapper.
     * Returns the per-context typeface if found, otherwise falls back
     * to the global Application.TYPE_FACE.
     */
    public static Typeface resolve(Context context) {
        Context current = context;
        while (current != null) {
            if (current instanceof TypefaceContextWrapper) {
                Typeface tf = ((TypefaceContextWrapper) current).getTypeface();
                if (tf != null) {
                    return tf;
                }
            }
            if (current instanceof ContextThemeWrapper) {
                current = ((ContextThemeWrapper) current).getBaseContext();
            } else {
                Context app = current.getApplicationContext();
                if (app == current) {
                    break;
                }
                current = app;
            }
        }
        return Application.TYPE_FACE;
    }
}
