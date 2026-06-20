package io.github.mxmilkiib.materialistic;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

public class AppIconUtils {

    private static final String NAMESPACE = "io.github.mxmilkiib.materialistic";

    private static final String[][] ALIASES = {
            {"default", NAMESPACE + ".LauncherActivity"},
            {"purple", NAMESPACE + ".LauncherIconPurple"},
            {"green", NAMESPACE + ".LauncherIconGreen"},
            {"blue", NAMESPACE + ".LauncherIconBlue"},
            {"red", NAMESPACE + ".LauncherIconRed"},
            {"teal", NAMESPACE + ".LauncherIconTeal"},
            {"pink", NAMESPACE + ".LauncherIconPink"},
            {"indigo", NAMESPACE + ".LauncherIconIndigo"},
    };

    public static void applyIcon(Context context, String iconValue) {
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();
        String selectedClass = null;
        for (String[] entry : ALIASES) {
            if (entry[0].equals(iconValue)) {
                selectedClass = entry[1];
            }
        }
        if (selectedClass == null) {
            selectedClass = ALIASES[0][1];
        }
        for (String[] entry : ALIASES) {
            String className = entry[1];
            int state;
            if (className.equals(selectedClass)) {
                state = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
            } else {
                state = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
            }
            pm.setComponentEnabledSetting(
                    new ComponentName(packageName, className),
                    state,
                    PackageManager.DONT_KILL_APP);
        }
    }
}
