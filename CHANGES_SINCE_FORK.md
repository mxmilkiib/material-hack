# Material Hack — Changes Since Fork

Forked from [Materialistic](https://github.com/hidroh/materialistic) at commit `442253f7` (Spring cleaning #1471).

## Identity and Build

- Renamed app to **Material Hack**, applicationId changed to `io.github.mxmilkiib.materialistic`
- Full package migration from `io.github.hidroh.materialistic` to `io.github.mxmilkiib.materialistic` across all source files, XML, Gradle, and ProGuard
- Copyright headers updated: `Copyright (c) 2026 mxmilkiib` added as second line in all source and XML files
- Feedback endpoint updated to `mxmilkiib/material-hack` repo
- Bumped version to 3.4-wip, synced LATEST_RELEASE with versionCode
- Debug build installs alongside release (applicationIdSuffix `.debug`)
- Modernized build toolchain: compileSdk 34, targetSdk 34, Java 17, Kotlin support
- GitHub Actions CI workflow for debug/release APK builds and tagged releases
- 3-hour delay on master builds, self-cancel if superseded, release reuses artifacts

## Theming

- **5 new themes** added to the theme picker (17 total):
  - Original: Light, Dark, Black, Sepia, Green, Solarized, Solarized Dark, Violet, Ocean, Rose, Monokai, Nord
  - New: **Crimson** (dark deep red), **Forest** (dark green), **Midnight** (dark deep blue), **Sand** (light warm desert), **Dracula** (dark purple-grey with pink accent)
- Theme icons reordered intuitively: light themes first, then dark themes
- Each theme includes alert dialog and bottom sheet dialog style overrides
- Violet theme: item page header uses `colorPrimaryDark` (deeper purple `#3A0066`) for visual depth
- Violet theme: `colorPrimary` deepened to `purple900` (#4A148C), `colorPrimaryDark` to `purple900b` (#3A0066)
- Violet theme: score column `colorCardHighlight` (`purple800`) shifted bluer to #6A1BB8
- Story list divider: `colorDivider` theme attribute added — `blackT50` (#80000000) for light themes, `grey900` (#212121) for dark themes
- RecyclerView backgrounds use `colorDivider` so 1dp item gap shows as a subtle darker line
- Theme swatch accent dot indicator for selected theme

## App Icon Customization

- 8 selectable app icons via Display settings: Orange (default), Purple, Green, Blue, Red, Teal, Pink, Indigo
- Adaptive icon XML files for each colour (API 26+), sharing the same foreground with different background colours
- 7 `<activity-alias>` entries in AndroidManifest, disabled by default
- `AppIconUtils` handles enabling/disabling aliases via `PackageManager`
- `PreferencesActivity.SettingsFragment` listens for preference changes and applies immediately

## Typography

- Added fonts: Hack Nerd Font, Inter, JetBrains Mono, Fira Code, Terminus, Fixedsys

## Story List Layout

- Compact list mode preference (smaller title/subtitle fonts, symmetric tight padding)
- List item divider set to 1dp with `colorDivider` background showing through as a darker line
- Score/rank column: full-height `colorCardHighlight` background anchored to parent top and `posted` bottom, centred gravity, no truncation
- Score column and background view anchored to `title.top`/`posted.bottom` via `alignTop`/`alignBottom` for reliable full-height coverage
- Title `marginTop` changed to `paddingTop` to eliminate dark gap above story content
- Flame (voted) icon constrained to 16dp x 16dp, centred vertically in ViewSwitcher with 2dp bottom margin for spacing
- Comment button and three-dots button raised 4dp via negative `marginTop`, moved closer together by reducing `button_more` padding to 2dp and `button_comment` `minWidth` to 0dp
- Comment button and three-dots button set to equal width (40dp) and equal height (aligned top/bottom via `alignTop`/`alignBottom`)
- Three-dots icon scale removed; replaced with 3dp top/bottom padding for visual sizing without scaling the view bounds
- Comment button gravity changed to `center` for centred icon+text alignment
- 2dp bottom padding on `posted` text for spacing at bottom of story boxes
- Transparent edge glow (overscroll effect)

## Drawer Menu

- App title in drawer header coloured HN orange (`@color/orange500`) with subtle black text shadow for contrast
- Drawer item text padding increased to 14dp top/bottom
- Lighter separator lines (`blackT6` — half opacity) between all menu items
- More sections flattened: Settings and Feedback repositioned
- Compact drawer items (wrap_content height)

## Item Page

- Item header background uses `colorPrimaryDark` for depth
- Header `paddingTop` increased to 8dp, `paddingBottom` reduced to 0dp
- Title `marginTop` +4dp for extra spacing above
- Meta container `marginTop` -2dp to reduce space below title
- Tighter header/comment padding

## About Page

- Build time and git commit shown in About
- Reworked layout: indented content, tighter spacing, manual bullet chars
- What's New content updated, "I LOVE IT" button removed
- mxmilkiib developer info

## Bug Fixes

- **Red border flash on tap**: root cause was `targetSdk 34` enabling Samsung content transitions using `colorAccent`; fixed by adding `windowContentTransitions=false` to all themes and replacing card `selectableItemBackground` with neutral ripple drawable
- **StrictMode penaltyFlashScreen**: removed red border flashes on thread violations in debug builds, replaced with `penaltyLog()`
- **Score column grey bar/grey line**: fixed by wrapping vote_switcher + score in LinearLayout filling full height with `colorCardHighlight`, aligning to parent top and `posted` bottom
- **Score column not filling full item height**: fixed by anchoring `score_column` and `background` to `alignParentTop`/`alignBottom` (posted) instead of unreliable `match_parent` in wrap_content parent
- **Dark gap above story content**: fixed by changing title `marginTop` to `paddingTop` so background and score column cover from the top edge
- **Thick gap between story items**: fixed by matching RecyclerView and CardView backgrounds, removing compat padding in flat mode via `BaseAppCardView` style
- **Card mode left border**: fixed by dynamically setting `cardUseCompatPadding` and resetting margins in `ListRecyclerViewAdapter` based on `cardViewEnabled`
- **Red ripple flash on DayNight themes**: added `colorControlHighlight` to `BaseAppTheme.DayNight.NoActionBar` and `android:colorControlHighlight` to all base themes

## Deprecated API Modernization

- **`setLayoutFrozen`** replaced with `suppressLayout` in `UserActivity` (API 31+)
- **`setBottomSheetCallback`** replaced with `addBottomSheetCallback` in `UserActivity` (API 28+)
- **`queryIntentActivities`** replaced with `PackageManager.ResolveInfoFlags.of(MATCH_DEFAULT_ONLY)` for API 33+ in `AppUtils` and `CustomTabsDelegate`
- **`resolveActivity`** centralized in `AppUtils.resolveActivity()` helper with API 33 `ResolveInfoFlags` guard; used in `AppUtils.openWebUrlExternal`, `AppUtils.share`, `WebFragment.offerExternalApp`, `CustomTabsDelegate.getDefaultBrowser`
- **`resolveService`** in `CustomTabsDelegate` gets same `ResolveInfoFlags` guard for API 33+
- **`getDefaultDisplay().getSize()` / `getMetrics()`** replaced with `WindowManager.getCurrentWindowMetrics()` for API 30+
- **`Html.fromHtml(String)`** updated to `Html.fromHtml(String, HtmlCompat.FROM_HTML_MODE_COMPACT)` for API 24+
- **`CONNECTIVITY_ACTION` BroadcastReceiver** (`ItemSyncWifiReceiver`) replaced with `ConnectivityManager.NetworkCallback` registered in `Application.onCreate`; manifest receiver declaration removed
- **`addJavascriptInterface`** for PDF viewer replaced with `WebViewAssetLoader` + `WebMessagePort`/`postMessage`; `PdfAndroidJavascriptBridge` class replaced with `PdfBridge` using JSON messages; `script.js` rewritten to use `MessageChannel` instead of synchronous JS bridge calls
- **HTML regex parsing** in `UserServicesClient` (`getInputValue`, `parseLoginError`) replaced with Jsoup CSS selectors and DOM traversal
- **`Vibrator.vibrate(long)`** replaced with `VibrationEffect` for API 26+ in `NavFloatingActionButton`
- **`SystemUiHelper`** updated with `WindowInsetsController` path for API 30+, legacy `SystemUiVisibility` kept for API 24-29
- **`LocalBroadcastManager`** for fullscreen toggling replaced with `FullscreenViewModel` + `LiveData`
- **`shouldInterceptRequest(WebView, String)`** deprecated override removed from `AdBlockWebViewClient` and `CacheableWebView`
- **`NetworkModule`** redundant `SocketFactory` field removed
- **`HttpUrl.parse()`** replaced with `HttpUrl.get()` in `UserServicesClient` (5 call sites)
- **ProGuard** config updated: `proguard-android-optimize.txt`, removed stale LeakCanary rules and outdated comment

## Architecture and Lifecycle

- **`StoryListViewModel`** added with `CompositeDisposable` + `onCleared()` for RxJava subscription lifecycle management
- **`FullscreenViewModel`** added with `LiveData<Boolean>` for fullscreen state observation
- **`ViewModelProvider`** used in `ListFragment` and `WebFragment` for lifecycle-aware state management

## Crash Fixes

- **`AdBlocker.init`**: `Observable.fromCallable(() -> loadFromAssets())` returned `Void` (null), forbidden by RxJava 3.x; `onErrorReturn(throwable -> null)` also returned null. Switched to `Completable.fromAction` since the operation is side-effect-only
- **`ReadabilityClient.fromNetwork`**: `onErrorReturn(throwable -> null)` returned null, forbidden by RxJava 3.x. Replaced with `onErrorResumeNext(throwable -> Observable.empty())`
- **`ReadabilityClient.fromCache`**: `Observable.just(mCache.getReadability(itemId))` where `getReadability` returns nullable `String?`. Added null check with `Observable.empty()` fallback
- **`ReadabilityClient` map operators**: `.map(... ? null : content)` produced null downstream. Changed to emit empty string instead
- **DI injection ordering in `ListFragment` and `FavoriteFragment`**: `BaseListFragment.onActivityCreated` sets `mCustomTabsDelegate` on the adapter, but injection ran after `super.onActivityCreated`, leaving it null. Moved injection before super call
- **DI injection ordering in `WebFragment` and `ItemFragment`**: `LazyLoadFragment.eagerLoad()` calls `load()` which uses injected fields (`mReadabilityClient`, `mItemManager`), but injection ran after `super.onActivityCreated`. Moved injection before super call
- **`ListRecyclerViewAdapter.onBindViewHolder`**: added null guard for `mCustomTabsDelegate` as defensive measure

## Test Coverage

- New `HackerNewsClientTest` with Robolectric for story fetching and item parsing
- New `StoryListViewModelTest` for ViewModel lifecycle and LiveData observation
- `UserServicesClientTest` additions for Jsoup-based `getInputValue` and `parseLoginError`
- All 38 unit tests passing

## Dependencies Added

- `androidx.webkit:webkit:1.11.0` -- `WebViewAssetLoader` for secure asset serving
- `org.jsoup:jsoup:1.18.1` -- HTML parsing in `UserServicesClient`
- `androidx.lifecycle:lifecycle-viewmodel-ktx` and `lifecycle-livedata-ktx` -- ViewModel/LiveData
