# Material Hack — Changes Since Fork

Forked from [Materialistic](https://github.com/hidroh/materialistic) at commit `442253f7` (Spring cleaning #1471).

## Identity and Build

- Renamed app to **Material Hack**, applicationId changed to `io.github.mxmilkiib.materialistic`
- Full package migration from `io.github.hidroh.materialistic` to `io.github.mxmilkiib.materialistic` across all source files, XML, Gradle, and ProGuard
- Copyright headers updated: `Copyright (c) 2024-2026 mxmilkiib` added as second line in all source and XML files
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

## Files Changed (Uncommitted)

- `AndroidManifest.xml` — 7 activity-alias entries for alternate icons
- `AppIconUtils.java` — new file, icon switching logic
- `Application.java` — removed penaltyFlashScreen
- `BaseListFragment.java` — uses `list_item_divider` (1dp) with `colorDivider` background
- `ListRecyclerViewAdapter.java` — dynamic `cardUseCompatPadding` and margin reset for flat/card mode switching
- `PreferencesActivity.java` — SharedPreferenceChangeListener for icon changes
- `ThemePreference.java` — registered 5 new themes
- `attrs.xml` — `colorDivider` theme attribute
- `button_comment.xml` — `minWidth` reduced to 0dp, zero padding, fixed width 40dp, gravity `center`
- `button_more.xml` — padding reduced to 2dp, 3dp top/bottom padding, fixed width 40dp, scale removed
- `drawer.xml` — orange title with shadow, lighter separators, padding
- `item_header.xml` — colorPrimaryDark background, adjusted padding/margins for title spacing
- `item_story.xml` — uses `BaseAppCardView` style for flat mode
- `fragment_list.xml` / `fragment_favorite.xml` — RecyclerView background changed to `colorDivider`
- `preference_theme.xml` — 5 new ThemeView buttons, reordered
- `story_view.xml` — score column full-height anchoring, title paddingTop, button margins, flame icon constraints, posted paddingBottom
- `arrays.xml` — app icon style arrays
- `colors.xml` — new theme colours, icon background colours, `purple900b`, `grey50`
- `dimens.xml` — `list_item_divider` (1dp)
- `ic_launcher_background.xml` — 7 alternate icon background colours
- `preference_keys.xml` — `pref_app_icon` key
- `strings.xml` — theme names, app icon option strings
- `styles.xml` — `BaseAppCardView` style (cardUseCompatPadding=false, elevation=0, cornerRadius=0), drawer text padding
- `themes.xml` — 5 new theme styles, `colorDivider` for light/dark/night themes, Violet theme deepened
- `values-night/themes.xml` — `colorDivider` for dark mode (`grey900`)
- `preferences_display.xml` — app icon style spinner preference
- `ripple_card.xml` — neutral ripple drawable (removed)
- `FeedbackClient.java` — feedback API endpoint updated to `mxmilkiib/material-hack`
- `proguard-rules.pro` — keep rules updated for new package name
