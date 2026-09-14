## Material Hack

A refreshed [Hacker News] client for Android — a fork of [Materialistic] by Ha Duy Trung.

Development of Material Hack has been assisted by AI coding agents. The initial modernisation and feature work (June 2026) was done with Claude Sonnet 4 (Anthropic). Later work — translations, UI customisation preferences, code smell fixes, and release signing (August–September 2026) — was done with Devin (Cognition) powered by GLM-5.2 High. All code has been reviewed and tested before committing.

[Materialistic](https://github.com/hidroh/materialistic) was a popular open-source Hacker News reader for Android, developed from January 2015 through April 2023, accumulating over 1,700 commits and 2,300 stars. The original author's last commit was "Spring cleaning (#1471)" on 2023-04-15, after which the project went dormant. It targeted an older Android SDK, used deprecated APIs, and exposed limited customisation — no font selection, no text size options beyond a single preference, no layout controls, and a fixed set of themes.

Material Hack picks up where Materialistic left off. Forked on 2026-06-18 at commit `442253f7`, it has since grown by over 100 commits across three months of active development. The work falls into three areas:

1. **Modernisation** — The build toolchain was updated to Gradle 8.7, AGP 8.5.2, Kotlin 1.9.24, compileSdk/targetSdk 34, and Java 17. Deprecated APIs were replaced throughout: `LocalBroadcastManager` to `LiveData`, `setLayoutFrozen` to `suppressLayout`, `addJavascriptInterface` to `WebViewAssetLoader`, `CONNECTIVITY_ACTION` receivers to `NetworkCallback`, `Html.fromHtml` to `HtmlCompat`, `Vibrator.vibrate` to `VibrationEffect`, `SystemUiVisibility` to `WindowInsetsController`, and HTML regex parsing to Jsoup. Several latent crash bugs from RxJava 3.x null handling and DI ordering were fixed.

2. **Customisation** — The original app's single text size preference was expanded into a full typography system: 11 bundled fonts with independent weight/style selection (Regular, Bold, Italic, Bold Italic), separate text size controls for the story list and comments (7 steps each, Tiny to Huge with a live preview), and compact mode with independently sized titles and subtitles. Layout controls were added for score column width, comment indentation, card elevation, list dividers, and the hot-story threshold. The theme count grew from 12 to 17 (adding Crimson, Forest, Midnight, Sand, and Dracula), and 8 selectable app icons were introduced. Settings were reorganised into two top-level screens — Appearance (all visual controls) and Behaviour (all non-visual controls) — with quick-access popups from the story list and comment toolbars.

3. **Localisation** — Translation support grew from 11 to 29 languages, with full translations added for 18 new languages including Yue Chinese, Wu Chinese, Korean, Amharic, Jamaican, Latin, Gaelic, and Scots.

The goal is a fast, readable, and highly tunable HN reader that respects the original app's architecture while giving the user control over typography, layout, and presentation.

**Source:** https://github.com/mxmilkiib/material-hack

### Screenshots

![Light](https://raw.githubusercontent.com/mxmilkiib/material-hack/master/screenshots/theme_light.png)

![Dark Orange](https://raw.githubusercontent.com/mxmilkiib/material-hack/master/screenshots/theme_darkorange.png)

![Violet](https://raw.githubusercontent.com/mxmilkiib/material-hack/master/screenshots/theme_violet.png)

![Aquamarine](https://raw.githubusercontent.com/mxmilkiib/material-hack/master/screenshots/theme_aquamarine.png)

### What's different

- **Modernised toolchain** — Gradle 8.7, AGP 8.5.2, Kotlin 1.9.24, compileSdk/targetSdk 34, minSdk 24
- **Minimum Android 7.0 (API 24)** — up from the original's API 15; required for Java 8 language features, AndroidX, and modern lifecycle APIs
- **New themes** — Violet, Ocean, Rose, Monokai, Nord, Crimson, Forest, Midnight, Sand, Dracula, Dark Orange (alongside the originals)
- **App icon customization** — 8 selectable app icons via Display settings (Orange, Purple, Green, Blue, Red, Teal, Pink, Indigo)
- **Typography controls** — 11 bundled fonts, font weight/style selection (Regular, Bold, Italic, Bold Italic), independent text sizes for story list and comments (7 steps each, Tiny to Huge with live preview), compact mode title/subtitle sizing
- **Layout controls** — adjustable score column width, comment indentation width, card elevation, list divider toggle, hot story threshold
- **Compact list mode** — tighter spacing, smaller fonts, smaller rank/score column
- **Flattened drawer** — all section links in the root, no "More sections" submenu
- **Reorganised settings** — two top-level screens: Appearance (theme, typography, layout) and Behaviour (display modes, navigation, browser), with quick-access popups from the story list and comment toolbars
- **List display options visible** — gear icon on the story list toolbar opens a quick appearance popup (theme, text size, font, compact toggle)
- **Release signing** — CI signs release APKs with a keystore stored as GitHub secrets; local builds produce unsigned release APKs when secrets are absent
- **Side-by-side debug install** — debug build uses `applicationIdSuffix ".debug"` so it installs alongside any release build
- **Deprecated API modernization** — updated for Android 34, replaced deprecated APIs (LocalBroadcastManager → LiveData, setLayoutFrozen → suppressLayout, etc.)
- **Crash fixes** — fixed RxJava 3.x null crashes, DI ordering issues, ScrollView crash, tab listener leaks, Parcelable duplicates, preference parsing crashes on corrupted values, all-whitespace comment trimming crash
- **Resource leak fixes** — detach old comment adapter before replacement, unregister WiFi sync NetworkCallback on termination, avoid Spannable allocation on every touch event
- **Test coverage** — unit tests for HackerNewsClient, StoryListViewModel, UserServicesClient
- **GitHub Actions CI** — automated debug/release APK builds and tagged releases

### Languages

The app supports 29 languages with full translations:

- English
- Spanish (Español)
- Chinese (Simplified) (简体中文)
- Portuguese (Português)
- French (Français)
- Swedish (Svenska)
- German (Deutsch)
- Hindi (हिन्दी)
- Arabic (العربية)
- Bengali (বাংলা)
- Indonesian (Bahasa Indonesia)
- Urdu (اردو)
- Polish (Polski)
- Russian (Русский)
- Japanese (日本語)
- Turkish (Türkçe)
- Nigerian Pidgin
- Egyptian Arabic (العربية المصرية)
- Western Punjabi (پنجابی)
- Tagalog (Tagalog)
- Tamil (தமிழ்)
- Yue Chinese (粵語)
- Wu Chinese (吳語)
- Korean (한국어)
- Amharic (አማርኛ)
- Jamaican
- Latin (Latina)
- Gaelic (Gàidhlig)
- Scots (Scots)

### Build

**Requirements**
- JDK 17 (`JAVA_HOME=/usr/lib/jvm/java-17-openjdk`)
- Android SDK at `/opt/android-sdk` (or set `sdk.dir` in `local.properties`)
- Android platform 34, build-tools 34

**Debug APK**

```sh
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export ANDROID_HOME=/opt/android-sdk
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

**Dependencies**
- [Official Hacker News API][HackerNews/API]
- [Algolia Hacker News Search API]
- [Mercury Web Parser API]
- [Android Jetpack]: appcompat / recyclerview / material / cardview / preference / browser / room / lifecycle
- Square [Retrofit] / [OkHttp] / [Dagger]
- [RxJava] & [RxAndroid]
- [PDF.js]

### Code Structure

The codebase inherits the original Materialistic's architecture: 137 Java files across 8 packages, ~20,000 lines. The root package (`io.github.mxmilkiib.materialistic`) holds 60 files — activities, fragments, utilities, DI modules, and view models all together.

**Package layout:**

| Package | Contents | Files |
|---------|----------|-------|
| `materialistic` (root) | Activities, fragments, utilities, DI, view models | 60 |
| `materialistic.widget` | RecyclerView adapters, custom views, decorators | 20 |
| `materialistic.data` | Hacker News API client, item model, database, sync | 12 |
| `materialistic.preference` | Custom preference widgets (spinner, font, theme) | 8 |
| `materialistic.accounts` | User services client (login, vote, submit) | 3 |
| `materialistic.appwidget` | Home screen widget | 2 |
| `materialistic.ktx` | Kotlin extensions | 2 |
| `materialistic.annotation` | Synthetic annotation | 1 |

**Largest files (split candidates):**

| File | Lines | Concerns mixed |
|------|-------|----------------|
| `WebFragment.java` | 806 | Web view lifecycle, readability, fullscreen, ad blocking |
| `AppUtils.java` | 725 | HTML parsing, dimensions, intents, clipboard, scrolling, share, layout inflation |
| `StoryRecyclerViewAdapter.java` | 689 | List adapter, vote handling, popup menu, swipe actions |
| `Preferences.java` | 646 | Preference getters, Theme resolution, Observable subscription |
| `HackerNewsItem.java` | 576 | Item model, Parcelable, cache, favourite state |

**Known structural debt:**

- `AppUtils` is a god class handling unrelated concerns (HTML, intents, dimensions, sharing). Could be split into `HtmlUtils`, `IntentUtils`, `DimensionUtils`.
- `Preferences` mixes three concerns: static preference accessors, theme/text-size/font resolution (`Theme` inner class), and subscription management (`Observable` inner class). These could be separate classes.
- The root package's 60 files mix activities, fragments, utilities, and DI. Activities could move to an `activity` package, fragments to a `fragment` package, DI to a `di` package.
- The `widget` package mixes adapters, custom views, and decorators. Adapters could be in a sub-package.

A structural refactor would touch nearly every file (import changes) and should be done as one clean, well-tested commit after the feature set stabilises — not opportunistically alongside feature work.

### License
    Copyright 2015 Ha Duy Trung
    Copyright 2026 mxmilkiib

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[Hacker News]: https://news.ycombinator.com/
[HackerNews/API]: https://github.com/HackerNews/API
[Materialistic]: https://github.com/hidroh/materialistic
[Algolia Hacker News Search API]: https://github.com/algolia/hn-search
[Mercury Web Parser API]: https://mercury.postlight.com/web-parser/
[Retrofit]: https://github.com/square/retrofit
[OkHttp]: https://github.com/square/okhttp
[Dagger]: https://github.com/square/dagger
[RxJava]: https://github.com/ReactiveX/RxJava
[RxAndroid]: https://github.com/ReactiveX/RxAndroid
[Android Jetpack]: https://developer.android.com/jetpack
[PDF.js]: https://mozilla.github.io/pdf.js/
