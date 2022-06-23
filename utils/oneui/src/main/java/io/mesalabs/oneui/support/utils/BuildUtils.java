/*
 * Kotahi
 * Copyright © 2022 BlackMesa123
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
