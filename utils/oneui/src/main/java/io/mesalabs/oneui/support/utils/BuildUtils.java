package io.mesalabs.oneui.support.utils;

import android.os.SemBuild;

public class BuildUtils {
    public static final int ONEUI_1_0 = 100000;

    public static boolean isSemDevice() {
        try {
            final Integer semVersion = SemBuild.VERSION.SEM_INT;
            return semVersion != null;
        } catch (NoSuchFieldError e) {
            return false;
        }
    }

    public static boolean isOneUI() {
        return getSEPVersion() >= ONEUI_1_0;
    }

    public static int getSEPVersion() {
        try {
            final int sepVersion = SemBuild.VERSION.SEM_PLATFORM_INT;
            return sepVersion;
        } catch (NoSuchFieldError e) {
            return -1;
        }
    }

}
