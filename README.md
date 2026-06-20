## Material Hack

A refreshed [Hacker News] client for Android — a fork of [Materialistic] by Ha Duy Trung.

**Source:** https://github.com/mxmilkiib/material-hack

### Screenshots

![Light](https://raw.githubusercontent.com/mxmilkiib/material-hack/master/screenshots/theme_light_v2.png)

![Dark Orange](https://raw.githubusercontent.com/mxmilkiib/material-hack/master/screenshots/theme_darkorange_v2.png)

![Violet](https://raw.githubusercontent.com/mxmilkiib/material-hack/master/screenshots/theme_violet_v2.png)

![Aquamarine](https://raw.githubusercontent.com/mxmilkiib/material-hack/master/screenshots/theme_aquamarine_v2.png)

### What's different

- **Modernised toolchain** — Gradle 8.7, AGP 8.5.2, Kotlin 1.9.24, compileSdk/targetSdk 34, minSdk 21
- **New themes** — Violet, Ocean, Rose, Monokai, Nord, Crimson, Forest, Midnight, Sand, Dracula, Dark Orange (alongside the originals)
- **Compact list mode** — tighter spacing, smaller fonts, smaller rank/score column
- **Extra fonts** — Hack Nerd Font, Inter, JetBrains Mono, Fira Code, Terminus, Fixedsys
- **Flattened drawer** — all section links in the root, no "More sections" submenu
- **UI polish** — transparent overscroll glow, tighter header/comment spacing, better score column layout
- **Side-by-side debug install** — debug build uses `applicationIdSuffix ".debug"` so it installs alongside any release build

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

### License
    Copyright 2015 Ha Duy Trung
    Copyright 2024-2026 mxmilkiib

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
