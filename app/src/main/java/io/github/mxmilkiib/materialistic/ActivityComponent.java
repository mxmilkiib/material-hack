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

import dagger.Subcomponent;
import io.github.mxmilkiib.materialistic.appwidget.WidgetConfigActivity;
import io.github.mxmilkiib.materialistic.appwidget.WidgetService;
import io.github.mxmilkiib.materialistic.data.ItemSyncJobService;
import io.github.mxmilkiib.materialistic.data.ItemSyncService;
import io.github.mxmilkiib.materialistic.widget.FavoriteRecyclerViewAdapter;
import io.github.mxmilkiib.materialistic.widget.MultiPageItemRecyclerViewAdapter;
import io.github.mxmilkiib.materialistic.widget.SinglePageItemRecyclerViewAdapter;
import io.github.mxmilkiib.materialistic.widget.StoryRecyclerViewAdapter;
import io.github.mxmilkiib.materialistic.widget.SubmissionRecyclerViewAdapter;
import io.github.mxmilkiib.materialistic.widget.ThreadPreviewRecyclerViewAdapter;

@Subcomponent
public interface ActivityComponent {

    void inject(AboutActivity activity);
    void inject(AskActivity activity);
    void inject(BestActivity activity);
    void inject(ComposeActivity activity);
    void inject(FavoriteActivity activity);
    void inject(FeedbackActivity activity);
    void inject(ItemActivity activity);
    void inject(JobsActivity activity);
    void inject(ListActivity activity);
    void inject(LoginActivity activity);
    void inject(NewActivity activity);
    void inject(OfflineWebActivity activity);
    void inject(PopularActivity activity);
    void inject(ReleaseNotesActivity activity);
    void inject(SearchActivity activity);
    void inject(SettingsActivity activity);
    void inject(ShowActivity activity);
    void inject(SubmitActivity activity);
    void inject(ThreadPreviewActivity activity);
    void inject(UserActivity activity);
    void inject(WidgetConfigActivity activity);

    void inject(FavoriteFragment fragment);
    void inject(ItemFragment fragment);
    void inject(ListFragment fragment);
    void inject(WebFragment fragment);

    void inject(FavoriteRecyclerViewAdapter adapter);
    void inject(SinglePageItemRecyclerViewAdapter adapter);
    void inject(StoryRecyclerViewAdapter adapter);
    void inject(SubmissionRecyclerViewAdapter adapter);
    void inject(MultiPageItemRecyclerViewAdapter adapter);
    void inject(ThreadPreviewRecyclerViewAdapter adapter);

    void inject(ItemSyncService service);
    void inject(ItemSyncJobService service);
    void inject(WidgetService service);
}
